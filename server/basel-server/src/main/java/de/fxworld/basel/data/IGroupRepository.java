package de.fxworld.basel.data;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.fxworld.basel.api.IUser;

public interface IGroupRepository extends CrudRepository<Group, String> {

	public List<Group> findAllByMembers(IUser member);

	public Group findByName(String name);
}
