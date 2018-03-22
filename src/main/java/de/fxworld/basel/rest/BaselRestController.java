package de.fxworld.basel.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IEntity;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.AbstractEntity;
import de.fxworld.basel.data.Group;
import de.fxworld.basel.data.Role;
import de.fxworld.basel.data.User;

@RestController
@RequestMapping("/api/v1")
public class BaselRestController {

	// error handling https://gist.github.com/jonikarppinen/662c38fb57a23de61c8b
	
	protected IBaselUserService service;
		
	@Inject
	public BaselRestController(IBaselUserService service) {
		this.service = service;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// Users
	
	@RequestMapping(path="/user", method = RequestMethod.GET)
	//@Secured("ROLE_ADMIN")
    public List<IUser> getUsers() {	
        return createList(service.getUsers());
    }
	
	@RequestMapping(path="/user", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user) {
		return createCreateResponse(user, "/user/{name}", (u) -> service.saveUser(u));
    }
	
	@RequestMapping(path="/user/{username}", method=RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String username) {
		return createGetResponse(service.getUserByName(username));	
	}
    
	@RequestMapping(path="/user/{name}", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<?> updateUser(@PathVariable String name, @RequestBody User newUser) {    	
    	return createUpdateResponse(newUser, service.getUserByName(name), (user) -> service.saveUser(user));
    }
	
	@RequestMapping(path="/user/{username}/groups", method=RequestMethod.GET)
	@Transactional
    public ResponseEntity<?> getGroupsForUser(@PathVariable String username) {  
		return createGetResponse(service.getUserByName(username), (IUser user) -> user.getGroups());		
	}
	
	@RequestMapping(path="/user/{username}/roles", method=RequestMethod.GET)
	@Transactional
    public ResponseEntity<?> getRolesForUser(@PathVariable String username) {
		return createGetResponse(service.getUserByName(username), (IUser user) -> user.getRoles());
	}  
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// Groups
		
	@RequestMapping(path = "/group", method = RequestMethod.GET)
    public List<IGroup> getGroups() {
		return createList(service.getGroups());
    }
	
	@RequestMapping(path="/group", method = RequestMethod.POST)
    public ResponseEntity<?> createGroup(@RequestBody Group group) {
    	return createCreateResponse(group, "/group/{name}", (g) -> service.saveGroup(g));
    }
	
	@RequestMapping(path="/group/{name}", method=RequestMethod.GET)
    public ResponseEntity<?> getGroup(@PathVariable String name) {
		return createGetResponse(service.getGroupByName(name));	
	}
	
	@RequestMapping(path="/group/{name}", method = RequestMethod.POST)
	public ResponseEntity<?> updateGroup(@PathVariable String name, @RequestBody Group newGroup) {
		return createUpdateResponse(newGroup, service.getGroupByName(name), (group) -> service.saveGroup(group));
    }
	

	@RequestMapping(path="/group/{name}/roles", method=RequestMethod.GET)
	@Transactional
    public ResponseEntity<?> getRolesForGroup(@PathVariable String name) {
		return createGetResponse(service.getGroupByName(name), (IGroup group) -> group.getRoles());
	}  
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// Roles
	
	@RequestMapping(path = "/role", method = RequestMethod.GET)
    public List<IRole> getRoles() {
		return createList(service.getRoles());
    }
	
	@RequestMapping(path="/role", method = RequestMethod.POST)
    public ResponseEntity<?> createRole(@RequestBody Role role) {
		return createCreateResponse(role, "/role/{name}", (r) -> service.saveRole(r));
    }
	
	@RequestMapping(path="/role/{name}", method=RequestMethod.GET)
    public ResponseEntity<?> getRole(@PathVariable String name) {
		return createGetResponse(service.getRoleByName(name));	
	}
	
	@RequestMapping(path="/role/{name}", method = RequestMethod.POST)
	public ResponseEntity<?> updateRole(@PathVariable String name, @RequestBody Role newRole) {
		return createUpdateResponse(newRole, service.getRoleByName(name), (role) -> service.saveRole(role));
    }
	
	////////////////////////////////////////////////////////////////////////
	// Dirty little helpers
	
	protected <T> List<T> createList(Iterable<? extends T> iterable) {
		List<T> result = new ArrayList<T>();
		
		iterable.forEach(result::add);
		
		return result;
	}
	
	protected HttpHeaders createHeader(IEntity entity, String path) {
		HttpHeaders result = new HttpHeaders();
		
		result.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path(path)
				.buildAndExpand(entity.getName()).toUri());
		
		return result;
	}
	
	protected <T, U> ResponseEntity<U> createGetResponse(T entity, Function<T, U> converter) {
		ResponseEntity<U> result = null;
				
		if (entity != null) {
			result = new ResponseEntity<>(converter.apply(entity), HttpStatus.OK);
		} else {
			result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return result;
	}
	
	protected <T> ResponseEntity<T> createGetResponse(T entity) {
		ResponseEntity<T> result;
		
		if (entity != null) {
			result = new ResponseEntity<>(entity, HttpStatus.OK);
		} else {
			result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return result;
	} 
	
	protected <T extends IEntity> ResponseEntity<?> createCreateResponse(T entity, String path, Consumer<T> consumer) {
		ResponseEntity<?> result = null;
				
		if (entity.isValid()) {
			consumer.accept(entity);
	    	
	    	HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(ServletUriComponentsBuilder
					.fromCurrentRequest().path(path)
					.buildAndExpand(entity.getName()).toUri());    	
	    		
	    	result = new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    	} else {
    		result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    		// TODO add better errors
    	}
		
		return result;
	}
	
	protected <T extends IEntity> ResponseEntity<T> createUpdateResponse(AbstractEntity<?> newUser, T entity, Consumer<T> consumer) {
		ResponseEntity<T> result = null;
		
		if (entity != null) {
	    	if (entity.isValid()) {
	    		entity.update(newUser);
	    		consumer.accept(entity);
	    	
	    		result = new ResponseEntity<>(entity, HttpStatus.OK);
	    	} else {
	    		result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    		// TODO add better errors
	    	}
    	} else {
    		result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
		
		return result;
	}
}