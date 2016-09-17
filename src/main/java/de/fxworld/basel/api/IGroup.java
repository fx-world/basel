package de.fxworld.basel.api;

public interface IGroup {

	String getGroupname();

	void setGroupname(String groupname);

	String getDescription();

	void setDescription(String description);

	long getId();

	Iterable<? extends IUser> getMembers();

}