package de.fxworld.basel.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.vaadin.viritin.fields.MPasswordField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;

public class UserForm extends AbstractForm<IUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7605482083148044506L;
	
	TextField     name      = new MTextField("Username");
	TextField     firstname = new MTextField("First Name");
	TextField     lastname  = new MTextField("Last Name");
	PasswordField password  = new MPasswordField("Password");
    TextField     email     = new MTextField("E-Mail");    
    TwinColSelect roles     = new TwinColSelect("Roles");
    	
	public UserForm(IBaselUserService service) {
		List<IRole> availableRoles = service.getRoles();
		
		setSizeUndefined();
		setModalWindowTitle("Edit User");
//		roles.setConverter(new SetConverter());		
		roles.setContainerDataSource(UIHelper.createRolesContainer(availableRoles));
		roles.setLeftColumnCaption("Available");
		roles.setRightColumnCaption("Applied");
	}

	@Override
	protected Component createContent() {
		return new MVerticalLayout(
                new MFormLayout(
                		name,
                		firstname,
                		lastname,
                		password,
                        email,
                        roles
                ).withWidth(""),
                getToolbar()
		).withWidth("");
	}

}
