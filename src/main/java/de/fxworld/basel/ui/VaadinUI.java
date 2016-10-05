package de.fxworld.basel.ui;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

//@EnableVaadin
@SpringUI
@Theme("valo")
public class VaadinUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 824882934600464374L;

	@Autowired
	private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest request) {
		MenuLayout menuLayout = new MenuLayout(this);        
        setContent(menuLayout);
        
        menuLayout.addMenu(DashboardView.VIEW_NAME, "Home");
        menuLayout.addMenu(UserListView.VIEW_NAME, "Users");
        menuLayout.addMenu(GroupListView.VIEW_NAME, "Groups");
        menuLayout.addMenu(RoleListView.VIEW_NAME, "Roles");
        menuLayout.getNavigator().addProvider(viewProvider);        
	}
}