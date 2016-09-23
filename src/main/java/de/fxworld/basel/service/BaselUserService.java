package de.fxworld.basel.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.Group;
import de.fxworld.basel.data.IGroupRepository;
import de.fxworld.basel.data.IRoleRepository;
import de.fxworld.basel.data.IUserRepository;
import de.fxworld.basel.data.Role;
import de.fxworld.basel.data.User;

@Service
public class BaselUserService implements IBaselUserService {

	private IUserRepository  userRepository;
	
	private IGroupRepository groupRepository;
		
	private IRoleRepository  roleRepository;

	public BaselUserService() {
	}
	
	@Inject
	public BaselUserService(IUserRepository userRepository, IGroupRepository groupRepository, IRoleRepository roleRepository) {
		this.userRepository  = userRepository;
		this.groupRepository = groupRepository;
		this.roleRepository  = roleRepository;
	}
	
	@Override
	public Iterable<? extends IUser> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public IUser getUser(long id) {		
		return userRepository.findOne(id);
	}
	
	@Override
	public IUser getUserByName(String name) {	
		return userRepository.findByName(name);
	}
	
	@Override
	public IUser saveUser(IUser user) {
		return userRepository.save((User) user);
	}

	@Override
	public void deleteUser(IUser user) {
		userRepository.delete((User) user);
	}

	@Override
	public Iterable<? extends IGroup> getGroups() {
		return groupRepository.findAll();
	}

	@Override
	public IGroup getGroup(long id) {
		return groupRepository.findOne(id);
	}
	
	@Override
	public IGroup getGroupByName(String name) {		
		return groupRepository.findByName(name);
	}

	@Override
	public IGroup saveGroup(IGroup group) {
		return groupRepository.save((Group) group);
	}

	@Override
	public void deleteGroup(IGroup group) {
		groupRepository.delete((Group) group); 
	}

	@Override
	public Iterable<? extends IRole> getRoles() {
		return roleRepository.findAll();
	}

	@Override
	public IRole getRole(long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public IRole getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public IRole saveRole(IRole role) {
		return roleRepository.save((Role) role);
	}

	@Override
	public void deleteRole(IRole role) {
		roleRepository.delete((Role) role);
	}

	@Override
	public IUser createUser(String name) {
		IUser result = new User(name);
		
		return result;
	}

	@Override
	public IGroup createGroup(String name) {
		IGroup result = new Group(name);
		
		return result;
	}

	@Override
	public IRole createRole(String name) {
		IRole result = new Role(name);

		return result;
	}
}
