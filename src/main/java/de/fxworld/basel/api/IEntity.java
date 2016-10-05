package de.fxworld.basel.api;

import java.io.Serializable;

public interface IEntity extends Serializable {

	public long getId();
	
	public String getName();

	public void setName(String name);
	
	public boolean isValid();

	public void update(IEntity newEntity);
}
