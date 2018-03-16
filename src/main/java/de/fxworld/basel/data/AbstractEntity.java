package de.fxworld.basel.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.fxworld.basel.api.IEntity;

@SuppressWarnings("rawtypes")
@MappedSuperclass 
public abstract class AbstractEntity<T extends AbstractEntity> implements IEntity {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@JsonIgnore
	private String id;
	
	@NotNull
	private String name;
	
	public AbstractEntity() {		
	}
	
	public AbstractEntity(String name) {
		setName(name);
	}

	@Override
	public boolean isValid() {
		boolean result = true;
		
		result &= (name != null);
		result &= !name.isEmpty();
		
		return result;
	}

	@Override
	public void update(IEntity entity) {
		setName(entity.getName());
	}
	

	/* (non-Javadoc)
	 * @see de.fxworld.basel.data.IGroup#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	protected <U,V> boolean equals(List<U> a, List<V> b) {
		boolean result = true;
		
		if ((a != null) && (b != null)) {
			result = a.size() == b.size();
			
			if (result) {
				for (int i = 0; (i < a.size()) && result; i++) {
					result &= a.equals(b);					
				}
			}			
			
		} else if ((a != null) && (b == null)) {
			result = false;
			
		} else if ((a == null) && (b != null)) {
			result = false;
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}		
}
