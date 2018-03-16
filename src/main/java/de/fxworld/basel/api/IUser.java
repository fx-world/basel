package de.fxworld.basel.api;

import java.util.Set;

public interface IUser extends IEntity {

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	Set<IRole> getRoles();

	void setRoles(Set<IRole> roles);
	
	Set<IGroup> getGroups();

}