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

@Entity(name = "basel_group")
public class Group extends AbstractEntity<Group> implements IGroup {

	private static final long serialVersionUID = 7743634964801718612L;

	private String description;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinTable(name="basel_user_in_group",
		joinColumns=
            @JoinColumn(name="group_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id")
        )
	private Set<IUser> members = new HashSet<IUser>();
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER, targetEntity=Role.class)
	@JoinTable(name="basel_group_role",
		joinColumns=
            @JoinColumn(name="group_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id")
        )
	private Set<IRole> roles = new HashSet<IRole>();
	
	public Group() {		
	}
	
	public Group(String groupname, String description) {
		super(groupname);
		
		setDescription(description);
	}
	
	public Group(String groupname) {
		this(groupname, null);
	}
	
	@Override
	public boolean isValid() {
		return super.isValid();
	}
	
	@Override
	public void update(IEntity entity) {
		super.update(entity);
				
		if (entity instanceof IGroup) {
			IGroup group = (IGroup) entity;
			
			setDescription(group.getDescription());
		}
		// TODO update roles and members
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IGroup#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IGroup#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IGroup#getMembers()
	 */
	@Override
	public Set<IUser> getMembers() {
		members.isEmpty();
		return members;
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IGroup#setMembers(java.util.Set)
	 */
	@Override
	public void setMembers(Set<IUser> members) {		
		this.members = members;
	}
	
	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IGroup#getRoles()
	 */
	@Override
	public Set<IRole> getRoles() {
		roles.isEmpty();
		return roles;
	}

	/* (non-Javadoc)
	 * @see de.fxworld.basel.api.IGroup#setRoles(java.util.Set)
	 */
	@Override
	public void setRoles(Set<IRole> roles) {
		this.roles = roles;
	}
}
