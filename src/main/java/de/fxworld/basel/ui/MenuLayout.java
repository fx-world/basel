package de.fxworld.basel.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Update to
 * https://vaadin.com/directory?utm_campaign=New+registration&utm_source=hs_automation&utm_medium=email&utm_content=28895372&_hsenc=p2ANqtz-_I4KCA5mV26mw4qTcBrVjXOwfHAODjsIEiMaATwFbbfG4hJlDNg9YP-7mLiLEMpak-7yWTkEipZ0sqgaPXwiXAiLPPaQPiCmeEhz74rQJq--JFeZ4&_hsmi=28895372#!addon/sidemenu-add-on
 * @author fx
 *
 */
public class MenuLayout extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160672186983651814L;

	CssLayout contentArea = new CssLayout();

	CssLayout menuArea = new CssLayout();
	
	MenuBar menu = new MenuBar();
	
	Navigator navigator = null;

	public MenuLayout(UI ui) {
        setSizeFull();

        navigator = new Navigator(ui, contentArea);
        
        menuArea.setPrimaryStyleName(ValoTheme.MENU_ROOT);
        menuArea.addComponent(menu);

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }

	public ComponentContainer getContentContainer() {
		return contentArea;
	}

	public void addMenu(Component menu) {
		menu.addStyleName(ValoTheme.MENU_PART);
		menuArea.addComponent(menu);
	}

	public void addMenu(String viewName, String name) {
		menu.addItem(name, (MenuItem) -> navigator.navigateTo(viewName));	
	}

	public Navigator getNavigator() {
		return navigator;
	}

}
