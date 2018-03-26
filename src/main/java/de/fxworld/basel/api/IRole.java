package de.fxworld.basel.api;

public interface IRole extends IEntity {

	public static final String BASEL_USER  = "basel-user";

	public static final String BASEL_ADMIN = "basel-admin";
	
	String getDescription();

	void setDescription(String description);

}