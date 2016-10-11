package de.fxworld.basel.ui;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.vaadin.viritin.form.AbstractForm;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class EntityView<T> extends VerticalLayout implements View {

	Class<T> clazz;
	AbstractForm<T> form;
	Consumer<T> saveHandler;
	String[] columnNames;
	
	Grid grid;
	
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
		addButton    = new Button("New", FontAwesome.PLUS_SQUARE_O);
		editButton   = new Button("Edit", FontAwesome.EDIT);
		deleteButton = new Button("Delete", FontAwesome.TRASH);
		
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		// build layout
		VerticalLayout   actions   = new VerticalLayout(addButton, editButton, deleteButton);
		HorizontalLayout container = new HorizontalLayout(grid, actions);

		addComponents(container);
		
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

		grid.setSizeFull();
		grid.setColumns((Object[]) columnNames);

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
			newEntity();
		});
		editButton.addClickListener(e -> {
			@SuppressWarnings("unchecked")
			T entity = (T) grid.getSelectedRow();
			editEntity(entity);
			
		});

		// Initialize listing
		listCustomers(null);
	}
	
	public void setSaveHandler(Consumer<T> saveHandler) {
		this.saveHandler = saveHandler;
	}
	
	protected void newEntity() {
		editEntity(createNewEntity());
	}
	
	protected void editEntity(T entity) {
        form.openInModalPopup();
        form.setEntity(entity);
        form.setSavedHandler((T u) -> {
        	saveHandler.accept(u);
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
		List<T>              entites   = getEntities();
		BeanItemContainer<T> container = new BeanItemContainer<T>(clazz, entites);
		
		grid.setContainerDataSource(container);
	}
	
	protected abstract List<T> getEntities();

}
