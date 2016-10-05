package de.fxworld.basel.ui;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;

import de.fxworld.basel.Application;
import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.IUserRepository;
import de.fxworld.basel.data.User;

@SpringView(name = UserListView.VIEW_NAME)
public class UserListView extends EntityView<User> {

	private static final long serialVersionUID = 1633058377572911134L;

	private static final Logger log = LoggerFactory.getLogger(UserListView.class);
	
	public static final String VIEW_NAME = "users";
	
	@Autowired
	protected IBaselUserService service;
	
	@Autowired
	protected IUserRepository repo;
	
	public UserListView() {
		super(User.class, new UserForm(), new String[] {"name", "firstName", "lastName", "email"});
		
		setSaveHandler(u -> service.saveUser(u));
	}

	@Override
	protected List<User> getEntities() {
		List<User> result = null;
		//return service.getUsers();
		result = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
		
		log.info(String.format("returning %d entities of type %s", result.size(), User.class));
		
		return result;
	}

	@Override
	protected User createNewEntity() {		
		//return service.createUser(null);
		return new User();
	}

}
