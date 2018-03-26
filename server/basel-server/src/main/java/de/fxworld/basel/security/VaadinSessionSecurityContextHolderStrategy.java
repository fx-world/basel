package de.fxworld.basel.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;

import com.vaadin.server.VaadinSession;

/**
 * A custom {@link SecurityContextHolderStrategy} that stores the {@link SecurityContext} in the Vaadin Session.
 */
public class VaadinSessionSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();
	
    @Override
    public void clearContext() {
    	VaadinSession session = VaadinSession.getCurrent();
    	
    	if (session != null) {
    		session.setAttribute(SecurityContext.class, null);
    	} else {
    		contextHolder.remove();
    	}
    }

    @Override
    public SecurityContext getContext() {
        VaadinSession session = VaadinSession.getCurrent();
        SecurityContext context = null;
        
        if (session != null) {
	        context = session.getAttribute(SecurityContext.class);
	        if (context == null) {
	            context = createEmptyContext();
	            session.setAttribute(SecurityContext.class, context);
	        }
        } else {
        	context = contextHolder.get();

    		if (context == null) {
    			context = createEmptyContext();
    			contextHolder.set(context);
    		}
        }
        
        return context;
    }

    @Override
    public void setContext(SecurityContext context) {
    	VaadinSession session = VaadinSession.getCurrent();
    	
    	if (session != null) {
    		session.setAttribute(SecurityContext.class, context);
    	} else {
    		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    		contextHolder.set(context);
    	}
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }
}
