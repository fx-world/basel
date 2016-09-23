package de.fxworld.basel.api;

public interface IEntity {

	public long getId();
	
	public String getName();

	public void setName(String name);
	
	public boolean isValid();

	public void update(IEntity newEntity);
}
