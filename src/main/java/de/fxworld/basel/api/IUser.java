package de.fxworld.basel.api;

import java.util.Collection;

public interface IUser extends IEntity {

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	Collection<? extends IRole> getRoles();

	Collection<? extends IGroup> getGroups();

}