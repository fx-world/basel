package de.fxworld.basel.data;

import javax.persistence.Entity;

import de.fxworld.basel.api.IEntity;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;

@Entity(name = "basel_role")
public class Role extends AbstractEntity<Role> implements IRole {
	
	private String description;
	
	public Role() {		
	}
	
	public Role(String rolename) {
		super(rolename);		
	}
	
	@Override
	public boolean isValid() {		
		return super.isValid();
	}
	
	@Override
	public void update(IEntity entity) {
		super.update(entity);
				
		if (entity instanceof IRole) {
			IRole role = (IRole) entity;
			
			setDescription(role.getDescription());
		}
		// TODO update roles and members
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IRole#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IRole#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
