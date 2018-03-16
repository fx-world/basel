package de.fxworld.basel.ui;

import java.util.Iterator;
import java.util.List;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;

public class UIHelper {

	public static Container createRolesContainer(List<IRole> roles) {
		final Container result = new IndexedContainer();
		
        if (roles != null) {
            for (final Iterator<?> i = roles.iterator(); i.hasNext();) {
                result.addItem(i.next());
            }
        }
        
		return result;
	}

	public static Container createUsersContainer(List<IUser> users) {
		final Container result = new IndexedContainer();
		
        if (users != null) {
            for (final Iterator<?> i = users.iterator(); i.hasNext();) {
                result.addItem(i.next());
            }
        }
        
		return result;
	}
}
