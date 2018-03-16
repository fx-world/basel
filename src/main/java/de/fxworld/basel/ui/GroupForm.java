package de.fxworld.basel.ui;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.Group;

public class GroupForm extends AbstractForm<IGroup> {

	TextField     name        = new MTextField("Name");
    TextField     description = new MTextField("Description");
    TwinColSelect members     = new TwinColSelect("Members");
    TwinColSelect roles       = new TwinColSelect("Roles");
    
    public GroupForm(IBaselUserService service, IGroup user) {
    	List<IRole> availableRoles = service.getRoles();
    	List<IUser> availableUsers = service.getUsers();
    	
    	setSizeUndefined();    	
    	
    	if (user != null) {
    		setEntity(user);
    		setModalWindowTitle("Edit Group");
    	} else {
    		//setEntity(new Group());
    		setModalWindowTitle("New Group");
    	}
    	
//    	members.setConverter(new SetConverter());		
    	members.setContainerDataSource(UIHelper.createUsersContainer(availableUsers));
    	members.setLeftColumnCaption("Available");
    	members.setRightColumnCaption("Selected");
    	
//    	roles.setConverter(new SetConverter());		
		roles.setContainerDataSource(UIHelper.createRolesContainer(availableRoles));
		roles.setLeftColumnCaption("Available");
		roles.setRightColumnCaption("Applied");
    }

	@Override
	protected Component createContent() {
		return new MVerticalLayout(
                new MFormLayout(
                		name,
                		description,
                		members,
                		roles                		
                ).withWidth(""),
                getToolbar()
		).withWidth("");
	}

}
