package de.fxworld.basel.api;

public interface IEntity {

	public String getId();
	
	public String getName();

	public void setName(String name);
	
	public boolean isValid();

	public void update(IEntity newEntity);
}
