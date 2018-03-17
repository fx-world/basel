package de.fxworld.basel.ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.UI;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;

@SpringView(name = GroupListView.VIEW_NAME)
public class GroupListView extends EntityView<IGroup> {

	private static final long serialVersionUID = 5933878407590697074L;

	public static final String VIEW_NAME = "groups";
	
	protected IBaselUserService service;
	
	public GroupListView(@Autowired IBaselUserService service) {
		super(IGroup.class, new GroupForm(service, null), new String[] {"name", "description", "members", "roles"});
		
		this.service = service;
		
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

	@Override
	protected void deleteEntity(IGroup entity) {
		service.deleteGroup(entity);
	}
}
