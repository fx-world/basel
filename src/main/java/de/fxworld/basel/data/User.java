package de.fxworld.basel.data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.fxworld.basel.api.IEntity;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;

@Entity(name = "basel_user")
public class User extends AbstractEntity<User> implements IUser {

	@JsonIgnore
	private String password;
	private String email;
	private String firstName;
	private String lastName;

	@JsonIgnore
	@ManyToMany(targetEntity=Group.class) //fetch=FetchType.EAGER
	@JoinTable(name="basel_user_in_group",
		joinColumns=
			    @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns=
        		@JoinColumn(name="group_id", referencedColumnName="id")
        )
	private Set<IGroup> groups = new HashSet<IGroup>();
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=Role.class)
	@JoinTable(name="basel_user_role",
		joinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id")
        )
	private Set<IRole> roles = new HashSet<IRole>();
	
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
	public Set<IRole> getRoles() {
		roles.isEmpty(); // to force EclipseLink to resolve it
		return roles;
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IUser#setRoles(java.util.Set)
	 */
	@Override
	public void setRoles(Set<IRole> roles) {
		this.roles = roles;
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IUser#getGroups()
	 */
	@Override
	public Set<IGroup> getGroups() {
		groups.isEmpty(); // to force EclipseLink to resolve it
		return groups;
	}
}
