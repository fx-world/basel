package de.fxworld.basel.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.IUserRepository;

@SpringView(name = UserListView.VIEW_NAME)
public class UserListView extends EntityView<IUser> {

	private static final long serialVersionUID = 1633058377572911134L;

	private static final Logger log = LoggerFactory.getLogger(UserListView.class);
	
	public static final String VIEW_NAME = "users";
	
	@Autowired
	protected IBaselUserService service;
	
	@Autowired
	protected IUserRepository repo;
	
	public UserListView() {
		super(IUser.class, new UserForm(), new String[] {"name", "firstName", "lastName", "email", "members", "roles"});
		
		setSaveHandler(u -> service.saveUser(u));
	}

	@Override
	protected List<IUser> getEntities() {
		List<IUser> result = service.getUsers();

		//result = StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
		
		//log.info(String.format("returning %d entities of type %s", result.size(), User.class));
		
		return result;
	}

	@Override
	protected IUser createNewEntity() {		
		return service.createUser(null);		
	}

}
