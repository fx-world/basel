package de.fxworld.basel.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.IGroupRepository;
import de.fxworld.basel.data.IRoleRepository;
import de.fxworld.basel.data.IUserRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BaselUserServiceTest {

	@Autowired
    private IUserRepository userRepository;
	
	@Autowired
    private IGroupRepository groupRepository;
	
	@Autowired
    private IRoleRepository roleRepository;
	
	IBaselUserService service;
	
	@Before
	public void setUp() throws Exception {
		service = new BaselUserService(userRepository, groupRepository, roleRepository);
		
		userRepository.deleteAll();
		groupRepository.deleteAll();
		roleRepository.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		userRepository.deleteAll();
		groupRepository.deleteAll();
		roleRepository.deleteAll();
	}

	@Test
	public void testGetUsers() {
		assertNotNull(service.getUsers());
		
		IUser user = service.createUser("fx");
		assertEquals("fx", user.getName());
		user = service.saveUser(user);
		
		assertNotNull(service.getUsers());
		
		List<IUser> users = new ArrayList<IUser>();
		
		service.getUsers().forEach(u -> users.add(u));
		assertEquals(1, users.size());
		assertEquals(user, users.get(0));
	}

	@Test
	public void testGetUser() {
		assertNull(service.getUser(0));
		
		IUser user = service.createUser("fx");
		assertEquals("fx", user.getName());
		service.saveUser(user);
		
		assertNotNull(service.getUser(user.getId()));
	}

	@Test
	public void testSaveUser() {
		try {
			service.saveUser(null);
			fail();
		} catch (Exception e) {			
		}
		
		IUser user = service.createUser("fx");
		service.saveUser(user);
		
		assertNotNull(service.getUser(user.getId()));
	}

	@Test
	public void testDeleteUser() {
		try {
			service.deleteUser(null);
			fail();
		} catch (Exception e) {			
		}
		
		IUser user = service.createUser("fx");
		service.saveUser(user);
		
		assertNotNull(service.getUser(user.getId()));
		service.deleteUser(user);
		assertNull(service.getUser(user.getId()));
	}

	@Test
	public void testGetGroups() {
		assertNotNull(service.getGroups());
		
		IGroup group = service.createGroup("friends");
		assertEquals("friends", group.getName());
		group = service.saveGroup(group);
		
		assertNotNull(service.getGroups());
		
		List<IGroup> groups = new ArrayList<IGroup>();
		
		service.getGroups().forEach(u -> groups.add(u));
		assertEquals(1, groups.size());
		assertEquals(group, groups.get(0));
	}

	@Test
	public void testGetGroup() {
		assertNull(service.getGroup(0));
		
		IGroup group = service.createGroup("friends");
		assertEquals("friends", group.getName());
		service.saveGroup(group);
		
		assertNotNull(service.getGroup(group.getId()));
	}

	@Test
	public void testSaveGroup() {
		try {
			service.saveGroup(null);
			fail();
		} catch (Exception e) {			
		}
		
		IGroup group = service.createGroup("friends");
		service.saveGroup(group);
		
		assertNotNull(service.getGroup(group.getId()));
	}

	@Test
	public void testDeleteGroup() {
		try {
			service.deleteGroup(null);
			fail();
		} catch (Exception e) {			
		}
		
		IGroup group = service.createGroup("friends");
		service.saveGroup(group);
		
		assertNotNull(service.getGroup(group.getId()));
		service.deleteGroup(group);
		assertNull(service.getGroup(group.getId()));
	}

	@Test
	public void testGetRoles() {
		assertNotNull(service.getRoles());
		
		IRole role = service.createRole("admin");
		assertEquals("admin", role.getName());
		role = service.saveRole(role);
		
		assertNotNull(service.getRoles());
		
		List<IRole> roles = new ArrayList<IRole>();
		
		service.getRoles().forEach(u -> roles.add(u));
		assertEquals(1, roles.size());
		assertEquals(role, roles.get(0));
	}

	@Test
	public void testGetRole() {
		assertNull(service.getRole(0));
		
		IRole role = service.createRole("admin");
		assertEquals("admin", role.getName());
		service.saveRole(role);
		
		assertNotNull(service.getRole(role.getId()));
	}

	@Test
	public void testSaveRole() {
		try {
			service.saveRole(null);
			fail();
		} catch (Exception e) {			
		}
		
		IRole role = service.createRole("admin");
		service.saveRole(role);
		
		assertNotNull(service.getRole(role.getId()));
	}

	@Test
	public void testDeleteRole() {
		try {
			service.deleteRole(null);
			fail();
		} catch (Exception e) {			
		}
		
		IRole role = service.createRole("admin");
		service.saveRole(role);
		
		assertNotNull(service.getRole(role.getId()));
		service.deleteRole(role);
		assertNull(service.getRole(role.getId()));
	}

}
