package de.fxworld.basel.ui;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.form.AbstractForm;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IGroup;
import de.fxworld.basel.api.IRole;

@SpringView(name = RoleListView.VIEW_NAME)
public class RoleListView extends EntityView<IRole> {

	private static final long serialVersionUID = 5933878407590697074L;

	public static final String VIEW_NAME = "roles";
	
	@Autowired
	protected IBaselUserService service;
	
	public RoleListView() {
		super(IRole.class, new RoleForm());
		
		setSaveHandler(g -> service.saveRole(g));
	}
	
	@Override
	protected void addColumns(Grid<IRole> grid) {
		grid.addColumn(IRole::getName).setId("name").setCaption("name");
		grid.addColumn(IRole::getDescription).setId("description").setCaption("description");
	}

	@Override
	protected List<IRole> getEntities() {		
		return service.getRoles();
	}

	@Override
	protected IRole createNewEntity() {
		return service.createRole(null);
	}

	@Override
	protected void deleteEntity(IRole entity) {
		service.deleteRole(entity);
	}
}
