package de.fxworld.basel.api;

public interface IUser {

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	String getEmail();

	void setEmail(String email);

	String getFirstName();

	void setFirstName(String firstName);

	String getLastName();

	void setLastName(String lastName);

	long getId();

	Iterable<? extends IRole> getRoles();

}