package de.fxworld.basel.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.fxworld.basel.api.IRole;
import de.fxworld.basel.api.IUser;
import de.fxworld.basel.service.BaselUserService;

@Service
public class BaselUserDetailsService implements UserDetailsService {

	@Autowired
    private BaselUserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		IUser user = userService.getUserByName(username);
        
		if (user == null) {
            throw new UsernameNotFoundException(username);
        }
		
        return new BaselUserDetails(user);
	}
	
	class BaselUserDetails implements UserDetails {

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
