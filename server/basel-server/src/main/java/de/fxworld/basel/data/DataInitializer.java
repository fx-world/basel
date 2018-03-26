package de.fxworld.basel.data;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.fxworld.basel.api.IRole;
import de.fxworld.basel.data.IGroupRepository;
import de.fxworld.basel.data.IRoleRepository;
import de.fxworld.basel.data.IUserRepository;
import de.fxworld.basel.data.Role;
import de.fxworld.basel.data.User;

@Component
public class DataInitializer {

	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IGroupRepository groupRepository;
	
	@Autowired
	IRoleRepository roleRepository;
	
	@PostConstruct
	public void init() {
		Role baselUserRole  = roleRepository.findByName(IRole.BASEL_USER) ;
		Role baselAdminRole = roleRepository.findByName(IRole.BASEL_ADMIN);
		
		if (baselUserRole == null) {
			baselUserRole = new Role(IRole.BASEL_USER);
			roleRepository.save(baselUserRole);
		}
		
		if (baselAdminRole == null) {
			baselAdminRole = new Role(IRole.BASEL_ADMIN);
			roleRepository.save(baselAdminRole);
		}
		
		if (userRepository.count() == 0) {
			User admin = new User("admin");
			admin.setPassword("admin");
			admin.getRoles().add(baselAdminRole);
			userRepository.save(admin);
		}
	}
}
