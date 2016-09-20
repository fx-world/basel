package de.fxworld.basel.data;

import javax.persistence.Entity;

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
	public void update(Role role) {
		super.update(role);
		
		setDescription(role.getDescription());
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Role other = (Role) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		return true;
	}
}
