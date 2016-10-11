package de.fxworld.basel.ui;

import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import de.fxworld.basel.api.IUser;

public class UserForm extends AbstractForm<IUser> {

	TextField     username = new MTextField("Name");
	PasswordField password = new MPasswordField("Password");
    TextField     email    = new MTextField("E-Mail");   
    	
	public UserForm() {
		setSizeUndefined();
		setModalWindowTitle("Edit User");
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(
                new MFormLayout(
                		username,
                		password,
                        email
                ).withWidth(""),
                getToolbar()
		).withWidth("");
	}

}
