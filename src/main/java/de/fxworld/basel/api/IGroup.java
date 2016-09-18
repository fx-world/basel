package de.fxworld.basel.api;

public interface IGroup extends IEntity {

	String getDescription();

	void setDescription(String description);

	Iterable<? extends IUser> getMembers();

}