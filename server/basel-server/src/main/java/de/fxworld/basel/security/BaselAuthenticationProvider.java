package de.fxworld.basel.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.fxworld.basel.api.IBaselUserService;
import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;

public class BaselAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private IBaselUserService userService;
	
	public BaselAuthenticationProvider(IBaselUserService userService) {
		this.userService = userService;
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");

			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		try {
			String      password   = null;
			UserDetails loadedUser = null;
			
			if (authentication.getCredentials() != null) {
				password = authentication.getCredentials().toString();
			}
			
			IUser user = userService.authenticate(username, password);
			
			if (user != null) {
				loadedUser = new BaselUserDetails(user);			
			} else {
				throw new BadCredentialsException(messages.getMessage(
						"AbstractUserDetailsAuthenticationProvider.badCredentials",
						"Bad credentials"));
			}
			
			return loadedUser;
			
		} catch (InternalAuthenticationServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}
		
	class BaselUserDetails implements UserDetails {

		private static final long serialVersionUID = 2893035082893639405L;
		
		private IUser user;
		
		public BaselUserDetails(IUser user) {
			this.user = user;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        
			for (final IRole role : user.getAllRoles()) {
	            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
	        }
			
	        return authorities;
		}

		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getName();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}
		
	}


}
