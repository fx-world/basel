package de.fxworld.basel.ui;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.data.IUserRepository;

@SpringView(name = UserListView.VIEW_NAME)
public class UserListView extends EntityView<IUser> {

	private static final long serialVersionUID = 1633058377572911134L;

	private static final Logger log = LoggerFactory.getLogger(UserListView.class);
	
	public static final String VIEW_NAME = "users";
	
	protected IBaselUserService service;
	
	protected IUserRepository repo;
	
	public UserListView(@Autowired IBaselUserService service, @Autowired IUserRepository repo) {
		super(IUser.class, new UserForm(service));

		this.service = service;
		this.repo    = repo;
		
		setSaveHandler(u -> service.saveUser(u));
	}

	@Override
	protected void addColumns(Grid<IUser> grid) {
		grid.addColumn(IUser::getName).setId("name").setCaption("name");
		grid.addColumn(IUser::getFirstName).setId("firstName").setCaption("first name");
		grid.addColumn(IUser::getLastName).setId("lastName").setCaption("last name");
		grid.addColumn(IUser::getEmail).setId("email").setCaption("e-mail");		
		grid.addColumn(IUser::getGroups).setId("groups").setCaption("groups");
		grid.addColumn(IUser::getRoles).setId("roles").setCaption("roles");
	}
	
	@Override
	protected List<IUser> getEntities() {
		List<IUser> result = service.getUsers();

		return result;
	}

	@Override
	protected IUser createNewEntity() {		
		return service.createUser(null);		
	}

	@Override
	protected void deleteEntity(IUser entity) {
		service.deleteUser(entity);
	}

}
