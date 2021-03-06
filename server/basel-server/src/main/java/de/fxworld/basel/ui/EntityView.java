package de.fxworld.basel.ui;

import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.form.AbstractForm;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.access.ViewInstanceAccessControl;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.fxworld.basel.api.IEntity;

public abstract class EntityView<T extends IEntity> extends VerticalLayout implements View, ViewInstanceAccessControl  {

	Class<T> clazz;
	AbstractForm<T> form;
	Consumer<T> saveHandler;
	
	Grid<T> grid;
	
	Button addButton;
	Button editButton;
	Button deleteButton;
	
	Window editWindow;
	
	public EntityView(Class<T> clazz, AbstractForm<T> form) {
		this.clazz       = clazz;
		this.form        = form;		
	}
	
	@PostConstruct
	protected void init() {
		
		grid         = new Grid(); 
		addButton    = new Button("New", VaadinIcons.PLUS_SQUARE_LEFT_O);
		editButton   = new Button("Edit", VaadinIcons.EDIT);
		deleteButton = new Button("Delete", VaadinIcons.TRASH);
		
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
		addColumns(grid);

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getAllSelectedItems().isEmpty()) {
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
			T entity = (T) grid.getSelectedItems().iterator().next();
			editEntity(entity);
		});
		deleteButton.addClickListener(e -> {
			T entity = (T) grid.getSelectedItems().iterator().next();
			deleteEntityQuestion(entity);
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
	
	protected void deleteEntityQuestion(T entity) {
		ConfirmDialog.show(getUI(), String.format("Should %s be deleted?", entity.getName()), new ConfirmDialog.Listener() {

            public void onClose(ConfirmDialog dialog) {
                if (dialog.isConfirmed()) {
                	deleteEntity(entity);
                }
            }
        });
	}
	
	protected abstract void addColumns(Grid<T> grid);
	
	protected abstract void deleteEntity(T entity);
	
	protected abstract T createNewEntity();

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
	protected void listCustomers(String s) {
		List<T> entites = getEntities();		
		grid.setItems(entites);
	}
	
	protected abstract List<T> getEntities();

//	@Override
//	public boolean isAccessGranted(UI ui, String beanName) {
//		return true;
//	}
	@Override
	public boolean isAccessGranted(UI ui, String beanName, View view) {
		// TODO Auto-generated method stub
		return true;
	}
}
