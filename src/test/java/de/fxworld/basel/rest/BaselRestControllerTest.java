package de.fxworld.basel.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.data.Group;
import de.fxworld.basel.data.IGroupRepository;
import de.fxworld.basel.data.IRoleRepository;
import de.fxworld.basel.data.IUserRepository;
import de.fxworld.basel.data.Role;
import de.fxworld.basel.data.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BaselRestControllerTest {

	private MockMvc mvc;

	@Autowired
    private IUserRepository userRepository;
	
	@Autowired
    private IGroupRepository groupRepository;
	
	@Autowired
    private IRoleRepository roleRepository;
	
	@Autowired
    private IBaselUserService service;
	
	private String pathPrefix;
	
	@Before
	public void setUp() throws Exception {
		groupRepository.deleteAll();
		userRepository.deleteAll();
		roleRepository.deleteAll();
		
		mvc = MockMvcBuilders.standaloneSetup(new BaselRestController(service)).build();
		
		pathPrefix = "/api/v1";
	}

	@After
	public void tearDown() throws Exception {
		groupRepository.deleteAll();
		userRepository.deleteAll();
		roleRepository.deleteAll();
	}

	@Test
	public void testGetUsers() throws Exception {
		JSONArray usersJson = new JSONArray();
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(usersJson.toString()));
		
		userRepository.save(new User("fx"));
		usersJson.put(new JSONObject().put("name", "fx"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(usersJson.toString()));
		
		userRepository.save(new User("mila"));
		usersJson.put(new JSONObject().put("name", "mila"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(usersJson.toString()));
	}

	@Test
	public void testCreateUser() throws Exception {
		JSONObject userJson = new JSONObject();
		userJson.put("name", "mila");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/user")
				.content(userJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		assertNotNull(userRepository.findByName("mila"));
	}

	@Test
	public void testGetUser() throws Exception {
		userRepository.save(new User("fx"));
		JSONObject userJson = new JSONObject().put("name", "fx");
		
		System.out.println(mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/fx").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(userJson.toString()))
			.andReturn().getResponse().getContentAsString());
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/mila").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateUser() throws Exception {
		userRepository.save(new User("fx"));
		JSONObject userJson = new JSONObject().put("name", "mila");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/user/fx")
				.content(userJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(userJson.toString()));
		
		assertNotNull(userRepository.findByName("mila"));
		assertNull(userRepository.findByName("fx"));
	}

	@Test
	@Transactional
	public void testGetGroupsForUser() throws Exception {
		User fx = new User("fx");
		userRepository.save(fx);

		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/fx/groups").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONArray().toString()));
		
		
		
		Group friends = new Group("friends");
		//friends.getMembers().add(fx);
		groupRepository.save(friends);
		
		fx.getGroups().add(friends);
		userRepository.save(fx);
		
		JSONArray  groupsJson  = new JSONArray();
		JSONObject friendsJson = new JSONObject().put("name", "friends");
		groupsJson.put(friendsJson);
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/fx/groups").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupsJson.toString()));
	}
	
	@Test
	public void testGetRolesForUser() throws Exception {
		User fx = new User("fx");
		userRepository.save(fx);

		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/fx/roles").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONArray().toString()));
		
		Role admin = new Role("admin");
		roleRepository.save(admin);
		fx.getRoles().add(admin);
		userRepository.save(fx);
		
		JSONArray  rolesJson = new JSONArray();
		JSONObject adminJson = new JSONObject().put("name", "admin");
		rolesJson.put(adminJson);
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/user/fx/roles").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(rolesJson.toString()));
	}

	@Test
	public void testGetGroups() throws Exception {
		JSONArray groupsJson = new JSONArray();
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/group").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupsJson.toString()));
		
		groupRepository.save(new Group("friends"));
		groupsJson.put(new JSONObject().put("name", "friends"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/group").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupsJson.toString()));
		
		groupRepository.save(new Group("family"));
		groupsJson.put(new JSONObject().put("name", "family"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/group").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupsJson.toString()));
	}

	@Test
	public void testCreateGroup() throws Exception {
		JSONObject groupJson = new JSONObject();
		groupJson.put("name", "friends");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/group")
				.content(groupJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		assertNotNull(groupRepository.findByName("friends"));
	}

	@Test
	public void testGetGroup() throws Exception {
		groupRepository.save(new Group("family"));
		JSONObject groupJson = new JSONObject().put("name", "family");
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/group/family").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupJson.toString()));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/group/friends").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateGroup() throws Exception {
		groupRepository.save(new Group("friends"));
		JSONObject groupJson = new JSONObject().put("name", "family");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/group/friends")
				.content(groupJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(groupJson.toString()));
		
		assertNotNull(groupRepository.findByName("family"));
		assertNull(groupRepository.findByName("friends"));
	}

	@Test
	public void testGetRoles() throws Exception {
		JSONArray rolesJson = new JSONArray();
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/role").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(rolesJson.toString()));
		
		roleRepository.save(new Role("admin"));
		rolesJson.put(new JSONObject().put("name", "admin"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/role").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(rolesJson.toString()));
		
		roleRepository.save(new Role("superuser"));
		rolesJson.put(new JSONObject().put("name", "superuser"));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/role").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(rolesJson.toString()));
	}

	@Test
	public void testCreateRole() throws Exception {
		JSONObject roleJson = new JSONObject();
		roleJson.put("name", "admin");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/role")
				.content(roleJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
		
		assertNotNull(roleRepository.findByName("admin"));
	}

	@Test
	public void testGetRole() throws Exception {
		roleRepository.save(new Role("admin"));
		JSONObject roleJson = new JSONObject().put("name", "admin");
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/role/admin").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(roleJson.toString()));
		
		mvc.perform(MockMvcRequestBuilders.get(pathPrefix + "/role/superuser").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateRole() throws Exception {
		roleRepository.save(new Role("admin"));
		JSONObject roleJson = new JSONObject().put("name", "superuser");
		
		mvc.perform(MockMvcRequestBuilders.post(pathPrefix + "/role/admin")
				.content(roleJson.toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().json(roleJson.toString()));
		
		assertNotNull(roleRepository.findByName("superuser"));
		assertNull(roleRepository.findByName("admin"));
	}

}
