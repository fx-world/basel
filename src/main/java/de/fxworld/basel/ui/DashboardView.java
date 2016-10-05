package de.fxworld.basel.ui;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View {
	
	public static final String VIEW_NAME = "";
	
//	@Autowired
//	protected IBaselUserService service;
	
	
	
	public DashboardView() {
	}
	
	@PostConstruct
	protected void init() {
		
		
		setSizeFull();
		this.setMargin(true);
		this.setSpacing(true);
	}

	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}
	
}
