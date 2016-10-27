package de.fxworld.basel.data;

import org.springframework.data.repository.CrudRepository;

public interface IRoleRepository extends CrudRepository<Role, String> {

	public Role findByName(String name);
}
