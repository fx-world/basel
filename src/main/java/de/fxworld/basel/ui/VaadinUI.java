package de.fxworld.basel.ui;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

@SpringUI(path="ui")
@Theme("valo")
public class VaadinUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 824882934600464374L;

	@Autowired
	protected SpringViewProvider viewProvider;
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	protected ErrorView errorView;
	
	@Override
	protected void init(VaadinRequest request) {
		
		if (UIHelper.isLoggedIn()) {
			showMain();
		} else {
			setContent(new LoginForm(this, this::login));
		}
	}

	protected void showMain() {
		MenuLayout menuLayout = new MenuLayout(this);        
		setContent(menuLayout);
		
		menuLayout.addMenu(DashboardView.VIEW_NAME, "Home");
		menuLayout.addMenu(UserListView.VIEW_NAME, "Users");
		menuLayout.addMenu(GroupListView.VIEW_NAME, "Groups");
		menuLayout.addMenu(RoleListView.VIEW_NAME, "Roles");
		menuLayout.getNavigator().addProvider(viewProvider);
	}
	
	protected boolean login(String username, String password) {
		try {
			Authentication token = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			// Reinitialize the session to protect against session fixation attacks. This
			// does not work
			// with websocket communication.
			VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
			SecurityContextHolder.getContext().setAuthentication(token);

			// Now when the session is reinitialized, we can enable websocket communication.
			// Or we could have just
			// used WEBSOCKET_XHR and skipped this step completely.
			getPushConfiguration().setTransport(Transport.WEBSOCKET);
			// getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

			// Show the main UI
			showMain();

			return true;
		} catch (AuthenticationException ex) {
			return false;
		}
	}

	protected void logout() {
		getPage().reload();
		getSession().close();
	}
}