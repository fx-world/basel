package de.fxworld.basel.api;

public interface IBaselUserService {
	
	///////////////////////////////////////////////////////////////////
	// Users

	public IUser createUser(String name);
	
	public Iterable<? extends IUser> getUsers();
	
	public IUser getUser(long id);
	
	public IUser getUserByName(String name);
	
	public IUser saveUser(IUser user);
	
	public void deleteUser(IUser user);
	
	///////////////////////////////////////////////////////////////////
	// Groups
	
	public IGroup createGroup(String name);
	
	public Iterable<? extends IGroup> getGroups();
	
	public IGroup getGroup(long id);
	
	public IGroup getGroupByName(String name);
	
	public IGroup saveGroup(IGroup group);
	
	public void deleteGroup(IGroup group);
	
	///////////////////////////////////////////////////////////////////
	// Roles
	
	public IRole createRole(String name);
	
	public Iterable<? extends IRole> getRoles();
	
	public IRole getRole(long id);
	
	public IRole getRoleByName(String name);
	
	public IRole saveRole(IRole role);
	
	public void deleteRole(IRole role);

	

}
