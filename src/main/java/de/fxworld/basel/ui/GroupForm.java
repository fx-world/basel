package de.fxworld.basel.ui;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.data.Group;

public class GroupForm extends AbstractForm<IGroup> {

	TextField name        = new MTextField("Name");
    TextField description = new MTextField("Description");   
    
    public GroupForm(IGroup user) {
    	setSizeUndefined();    	
    	
    	if (user != null) {
    		setEntity(user);
    		setModalWindowTitle("Edit Group");
    	} else {
    		setEntity(new Group());
    		setModalWindowTitle("New Group");
    	}
    }
	
	public GroupForm() {
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
