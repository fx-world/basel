package de.fxworld.basel.api;

public interface IBaselUserService {

	public Iterable<? extends IUser> getUsers();
	
	public IUser getUser(long id);
	
	public void saveUser(IUser user);
	
	public void deleteUser(IUser user);
	
	public Iterable<? extends IGroup> getGroups();
	
	public IGroup getGroup(long id);
	
	public void saveGroup(IGroup group);
	
	public void deleteGroup(IGroup group);
	
	public Iterable<? extends IRole> getRoles();
	
	public IRole getRole(long id);
	
	public void saveRole(IRole role);
	
	public void deleteRole(IRole role);
}
