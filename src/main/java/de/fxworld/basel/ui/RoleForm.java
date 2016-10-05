package de.fxworld.basel.ui;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import de.fxworld.basel.api.IRole;
import de.fxworld.basel.data.Role;

public class RoleForm extends AbstractForm<IRole> {

	TextField name        = new MTextField("Name");
    TextField description = new MTextField("Description");   
    
    public RoleForm(IRole user) {
    	setSizeUndefined();    	
    	
    	if (user != null) {
    		setEntity(user);
    		setModalWindowTitle("Edit Role");
    	} else {
    		setEntity(new Role());
    		setModalWindowTitle("New Role");
    	}
    }
	
	public RoleForm() {
		this(null);
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(
                new MFormLayout(
                		name,
                		description
                ).withWidth(""),
                getToolbar()
		).withWidth("");
	}

}
