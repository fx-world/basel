package de.fxworld.basel.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User, String> {

	public User findByName(String name);

	public List<User> findAllBy(Pageable pageable);
}
