package de.fxworld.basel.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringView;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;

@SpringView(name = GroupListView.VIEW_NAME)
public class GroupListView extends EntityView<IGroup> {

	private static final long serialVersionUID = 5933878407590697074L;

	public static final String VIEW_NAME = "groups";
	
	@Autowired
	protected IBaselUserService service;
	
	public GroupListView() {
		super(IGroup.class, new GroupForm(), new String[] {"name", "description", "members", "roles"});
		
		setSaveHandler(g -> service.saveGroup(g));
	}

	@Override
	protected List<IGroup> getEntities() {		
		return service.getGroups();
	}

	@Override
	protected IGroup createNewEntity() {
		return service.createGroup(null);
	}

}