package de.fxworld.basel.ui;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.vaadin.viritin.ListContainer;
import org.vaadin.viritin.form.AbstractForm;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

//@SpringView(name = EntityView.VIEW_NAME)
public abstract class EntityView<T> extends VerticalLayout implements View {
	
	//public static final String VIEW_NAME = "";
	
//	@Autowired
//	protected IBaselUserService service;

	Class<T> clazz;
	AbstractForm<T> form;
	Consumer<T> saveHandler;
	String[] columnNames;
	
	Grid grid;

	//TextField filter;
	
	Button addButton;
	Button editButton;
	Button deleteButton;
	
	Window editWindow;
	
	
	
	public EntityView(Class<T> clazz, AbstractForm<T> form, String[] columnNames) {
		this.clazz       = clazz;
		this.form        = form;		
		this.columnNames = columnNames;
	}
	
	@PostConstruct
	protected void init() {
		
		grid         = new Grid();
		//filter       = new TextField();
		addButton    = new Button("New", FontAwesome.PLUS_SQUARE_O);
		editButton   = new Button("Edit", FontAwesome.EDIT);
		deleteButton = new Button("Delete", FontAwesome.TRASH);
		
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		// build layout
		VerticalLayout   actions   = new VerticalLayout(addButton, editButton, deleteButton);
		HorizontalLayout container = new HorizontalLayout(grid, actions);

		addComponents(container);
		
		//getUI().getWindows().add(editWindow);
		//getUI().getMainWindow().addWindow(editWindow);
		
		//setTheme(Reindeer.LAYOUT_WHITE);

		// Configure layouts and components
		
		addButton.setWidth(100, Unit.PERCENTAGE);
		editButton.setWidth(100, Unit.PERCENTAGE);
		deleteButton.setWidth(100, Unit.PERCENTAGE);
		
		setSizeFull();
		container.setSizeFull();
		container.setExpandRatio(grid, 1.0f);
		container.setSpacing(true);
		actions.setWidth(200, Unit.PIXELS);
		actions.setSpacing(true);
		this.setMargin(true);
		this.setSpacing(true);

		//grid.setHeight(300, Unit.PIXELS);
		grid.setSizeFull();
		grid.setColumns((Object[]) columnNames);//"username", "firstName", "lastName", "email", "roles");
		//grid.setColumns("name");
		//grid.getColumn("email").setHeaderCaption("E-Mail");

		//filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		//filter.addTextChangeListener(e -> listCustomers(e.getText()));

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				editButton.setEnabled(false);
				deleteButton.setEnabled(false);
			}
			else {
				editButton.setEnabled(true);
				deleteButton.setEnabled(true);
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addButton.addClickListener(e -> {
			newUser();
		});
		editButton.addClickListener(e -> {
//			editor.edit((User) grid.getSelectedRow());
//			if (!getUI().getWindows().contains(editWindow)) {
//				getUI().addWindow(editWindow);
//			}
//			editWindow.setVisible(true);
			T user = (T) grid.getSelectedRow();
			editUser(user);
			
		});

		// Listen changes made by the editor, refresh data from backend
//		editor.setChangeHandler(() -> {
//			editor.setVisible(false);
//			listCustomers(filter.getValue());
//		});

		// Initialize listing
		listCustomers(null);
	}
	
	public void setSaveHandler(Consumer<T> saveHandler) {
		this.saveHandler = saveHandler;
	}
	
	protected void newUser() {
		editUser(null);
	}
	
	protected void editUser(T user) {
        //UserForm userForm = new UserForm(user);
		if (user == null) {
			user = createNewEntity();
		}
        form.openInModalPopup();
        form.setData(user);       
        form.setSavedHandler((T u) -> {
        	saveHandler.accept(u);
        	//service.saveUser(u);
        	listCustomers(null);
        	form.closePopup();        	
        });
        form.setResetHandler((T u) -> {
        	listCustomers(null);
        	form.closePopup();
        });
}
	
	protected abstract T createNewEntity();

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	protected void listCustomers(String s) {
		List<T> entites = getEntities();
		BeanItemContainer container = new BeanItemContainer(clazz, entites);
		grid.setContainerDataSource(container);
//		List ids = container.getItemIds();
		//entites.forEach(grid.getContainerDataSource()::addItem);
		//grid.getContainerDataSource().addItem(itemId)
		//grid.setContainerDataSource(new ListContainer<>(clazz, entites));
//		grid.setEnabled(true);
//		boolean hasfilter = container.hasContainerFilters();
//		grid.setRowStyleGenerator(null);
//		markAsDirtyRecursive();
	}
	
	protected abstract List<T> getEntities();

}
