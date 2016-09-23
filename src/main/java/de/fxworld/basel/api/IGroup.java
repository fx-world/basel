package de.fxworld.basel.api;

import java.util.Collection;

public interface IGroup extends IEntity {

	String getDescription();

	void setDescription(String description);

	Collection<? extends IUser> getMembers();

	Collection<? extends IRole> getRoles();

}