package de.fxworld.basel.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.fxworld.basel.api.IEntity;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IUser;

@Entity(name = "basel_user")
public class User extends AbstractEntity<User> implements IUser {

	@JsonIgnore
	private String password;
	private String email;
	private String firstName;
	private String lastName;

	@JsonIgnore
	@ManyToMany() //fetch=FetchType.EAGER
	@JoinTable(name="basel_user_in_group",
		joinColumns=
			    @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns=
        		@JoinColumn(name="group_id", referencedColumnName="id")
        )
	private List<Group> groups = new ArrayList<Group>();
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="basel_user_role",
		joinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id")
        )
	private List<Role> roles = new ArrayList<Role>();
	
	public User() {
	}

	public User(String username) {
		super(username);
	}
	
	@Override
	public boolean isValid() {		
		return super.isValid();
	}
	
	@Override
	public void update(IEntity entity) {
		super.update(entity);
				
		if (entity instanceof IUser) {
			IUser user = (IUser) entity;
			
			setPassword(user.getPassword());
			setFirstName(user.getFirstName());
			setLastName(user.getLastName());

//			List<Role> tempRoles = new ArrayList<Role>();
//			
//			tempRoles.addAll(user.getRoles());
//			roles.clear();
//			roles.addAll(tempRoles);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		if ((password != null) && (!password.isEmpty())) {
			this.password = password;
		}
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IUser#getRoles()
	 */
	@Override
	public List<Role> getRoles() {
		return roles;
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IUser#getGroups()
	 */
	@Override
	public Collection<Group> getGroups() {		
		return groups;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (roles == null) {
			if (other.roles != null) {
				return false;
			}
		} else if (!equals(roles, other.roles)) {			
			return false;
		}
		return true;
	}
}
