package de.fxworld.basel.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IUser;

@SpringView(name = UsersView.VIEW_NAME)
public class UsersView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "";
	
	@Autowired
	protected IBaselUserService service;

	Grid grid;

	TextField filter;
	
	Button addButton;
	Button editButton;
	Button deleteButton;
	
	Window editWindow;
	
	@PostConstruct
	protected void init() {
		
		grid         = new Grid();
		filter       = new TextField();
		addButton    = new Button("New", FontAwesome.PLUS_SQUARE_O);
		editButton   = new Button("Edit", FontAwesome.EDIT);
		deleteButton = new Button("Delete", FontAwesome.TRASH);
		
		// build layout
		VerticalLayout   actions   = new VerticalLayout(filter, addButton, editButton, deleteButton);
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
		grid.setColumns("username", "firstName", "lastName", "email", "roles");
		grid.getColumn("email").setHeaderCaption("E-Mail");

		filter.setInputPrompt("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addTextChangeListener(e -> listCustomers(e.getText()));

		// Connect selected Customer to editor or hide if none is selected
//		grid.addSelectionListener(e -> {
//			if (e.getSelected().isEmpty()) {
//				editor.setVisible(false);
//			}
//			else {
//				editor.edit((User) grid.getSelectedRow());
//			}
//		});

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
			IUser user = (IUser) grid.getSelectedRow();
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
	
	protected void newUser() {
		editUser(null);
	}
	
	protected void editUser(IUser user) {
        UserForm userForm = new UserForm(user);
        userForm.openInModalPopup();
        userForm.setSavedHandler((IUser u) -> {
        	service.saveUser(u);
        	listCustomers(null);
        	userForm.closePopup();        	
        });
        userForm.setResetHandler((IUser u) -> {
        	listCustomers(null);
        	userForm.closePopup();
        });
}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
//		void listCustomers(String text) {
//			List<User> list = new ArrayList<>();
//			if (StringUtils.isEmpty(text)) {
//				repo.findAll().forEach(list::add);			
//			} else {
//				//repo. // TODO add search function
//			}
//			
//			grid.setContainerDataSource(new BeanItemContainer(User.class, list));
//		}
	
	protected void listCustomers(String s) {
		listEntities();
	}
	
	static final int PAGESIZE = 45;
		protected void listEntities() {
	        // A dead simple in memory listing would be:
	        // list.setBeans(repo.findAll());

	        // Lazy binding with SortableLazyList: memory and query efficient 
	        // connection from Vaadin Table to Spring Repository
	        // Note that fetching strategies can be given to MTable constructor as well.
	        // Use this approach if you expect you'll have lots of data in your 
	        // table.
			
//			List<IUser> users = new ArrayList<>();
//			service.getUsers().forEach(users::add);
			
			List<IUser> users = StreamSupport.stream(service.getUsers().spliterator(), false)
					.collect(Collectors.toList());
			
			grid.setContainerDataSource(new BeanItemContainer<IUser>(IUser.class, users));
//			grid.setContainerDataSource(new BeanItemContainer(IUser.class, 
//	        new SortableLazyList<IUser>(
//	                // entity fetching strategy
//	                (firstRow, asc, sortProperty) -> repo.findAllBy(
//	                        new PageRequest(
//	                                firstRow / PAGESIZE,
//	                                PAGESIZE,
//	                                asc ? Sort.Direction.ASC : Sort.Direction.DESC,
//	                                // fall back to id as "natural order"
//	                                sortProperty == null ? "id" : sortProperty
//	                        )
//	                ),
//	                // count fetching strategy
//	                () -> (int) repo.count(),
//	                PAGESIZE
//	        )));
	        //adjustActionButtonState();

	    }


}
