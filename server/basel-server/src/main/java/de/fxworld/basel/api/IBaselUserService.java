package de.fxworld.basel.api;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

public interface IBaselUserService {
	
	public IUser authenticate(String username, String password);
	
	///////////////////////////////////////////////////////////////////
	// Users

	public IUser createUser(String name);

	@Secured("ULTRA_ADMIN")
	public List<IUser> getUsers();
	
	public IUser getUser(String id);
	
	public IUser getUserByName(String name);
	
	public IUser saveUser(IUser user);
	
	public void deleteUser(IUser user);
	
	///////////////////////////////////////////////////////////////////
	// Groups
	
	public IGroup createGroup(String name);
	
	public List<IGroup> getGroups();
	
	public IGroup getGroup(String id);
	
	public IGroup getGroupByName(String name);
	
	public IGroup saveGroup(IGroup group);
	
	public void deleteGroup(IGroup group);
	
	///////////////////////////////////////////////////////////////////
	// Roles
	
	public IRole createRole(String name);
	
	public List<IRole> getRoles();
	
	public IRole getRole(String id);
	
	public IRole getRoleByName(String name);
	
	public IRole saveRole(IRole role);
	
	public void deleteRole(IRole role);	

}
