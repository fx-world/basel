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
import de.fxworld.basel.api.IRole;

@Entity(name = "basel_group")
public class Group extends AbstractEntity<Group> implements IGroup {

	private String description;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="basel_user_in_group",
		joinColumns=
            @JoinColumn(name="group_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="user_id", referencedColumnName="id")
        )
	private List<User> members = new ArrayList<User>();
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="basel_group_role",
		joinColumns=
            @JoinColumn(name="group_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="role_id", referencedColumnName="id")
        )
	private List<Role> roles = new ArrayList<Role>();
	
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
	public List<User> getMembers() {
		members.isEmpty();
		return members;
	}
	
	@Override
	public Collection<? extends IRole> getRoles() {
		roles.isEmpty();
		return roles;
	}
}
