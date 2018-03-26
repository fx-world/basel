package de.fxworld.basel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

import de.fxworld.basel.api.IRole;
import de.fxworld.basel.data.DataInitializer;
import de.fxworld.basel.service.BaselUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	public static final String ROLE_ADMIN = "ROLE_" + IRole.BASEL_ADMIN;
	public static final String ROLE_USER  = "ROLE_" + IRole.BASEL_USER;
	
	@Autowired
    private BaselUserService userService;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(new BaselAuthenticationProvider(userService));
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
	    http.authorizeRequests().antMatchers("/api/**").fullyAuthenticated();
	    http.httpBasic();
	    http.csrf().disable();
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }

    static {
        // Use a custom SecurityContextHolderStrategy
        SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
    }
}