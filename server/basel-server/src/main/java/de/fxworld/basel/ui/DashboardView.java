package de.fxworld.basel.ui;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

import de.fxworld.basel.Application;

@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "";
	
	private static final Logger log = LoggerFactory.getLogger(DashboardView.class);
	
//	@Autowired
//	protected IBaselUserService service;
	
	
	
	public DashboardView() {
	}
	
	@PostConstruct
	protected void init() {
		
		
		this.setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);
		
//		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//		ListSelect list = new ListSelect("roles");
//
//		log.warn("test");
//		for (SimpleGrantedAuthority auth : authorities) {
//			log.warn(auth.toString());
//		}
//		
//		list.setItems(authorities);
//		list.setSizeFull();
//		
//		this.addComponent(list);
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
}
