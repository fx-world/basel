package de.fxworld.basel.api;

import java.util.Set;

public interface IGroup extends IEntity {

	String getDescription();

	void setDescription(String description);

	Set<IUser> getMembers();
	
	void setMembers(Set<IUser> members);

	Set<IRole> getRoles();
	
	void setRoles(Set<IRole> roles);
}