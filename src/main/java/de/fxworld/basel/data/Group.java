package de.fxworld.basel.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.utils.BagComparator;

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
	private List<User> roles = new ArrayList<User>();
	
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
	public void update(Group group) {
		super.update(group);
				
		setDescription(group.getDescription());
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
		return members;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((members == null) ? 0 : members.hashCode());
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
		Group other = (Group) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (members == null) {
			if (other.members != null) {
				return false;
			}
		} else if (!BagComparator.equals(members, other.members)) {
			return false;
		}
		if (roles == null) {
			if (other.roles != null) {
				return false;
			}
		} else if (!BagComparator.equals(roles, other.roles)) {
			return false;
		}
		return true;
	}
}
