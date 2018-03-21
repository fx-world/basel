package de.fxworld.basel.ui;

import org.vaadin.jouni.animator.Animator;
import org.vaadin.jouni.dom.client.Css;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class LoginForm extends VerticalLayout {

    public LoginForm(UI ui, LoginCallback callback) {
        setMargin(false);
        setSpacing(false);
                
        //https://picsum.photos/1024/800/?random&blur=true
        //"http://source.unsplash.com/random"
        String url = String.format("https://picsum.photos/%d/%d/?random&blur=true", (int)ui.getPage().getBrowserWindowWidth(), ui.getPage().getBrowserWindowHeight());
        Resource resource = new ExternalResource(url);
        Image backgroundImage = new Image(null, resource);
        backgroundImage.setSizeFull();
        addComponent(backgroundImage);
        setExpandRatio(backgroundImage, 1);

        // Open it in the UI
        ui.addWindow(createLoginWindow(callback));
                
    }
    
	protected Window createLoginWindow(LoginCallback callback) {
    	Window subWindow = new Window("Login");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subWindow.setContent(subContent);
        subWindow.setClosable(false);
        subWindow.setResizable(false);

        TextField username = new TextField("Username");
        subContent.addComponent(username);

        PasswordField password = new PasswordField("Password");
        subContent.addComponent(password);

        Button login = new Button("Login", evt -> {
            String pword = password.getValue();
            password.setValue("");
            if (!callback.login(username.getValue(), pword)) {
                Notification.show("Login failed");
                Animator.animate(subWindow, new Css().rotate(360)).duration(500);
                username.focus();
            } else {
            	subWindow.close();
            }
        });
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        subContent.addComponent(login);

        // Center it in the browser window
        subWindow.center();
        
		return subWindow;       
    }

    @FunctionalInterface
    public interface LoginCallback {

        boolean login(String username, String password);
    }
}