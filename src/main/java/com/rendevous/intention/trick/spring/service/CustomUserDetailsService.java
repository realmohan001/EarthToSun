package com.rendevous.intention.trick.spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rendevous.intention.trick.spring.controller.UserSessionScopedData;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.util.CustomUserImplementation;

/**
 * A custom service for retrieving users from a custom datasource, such as a database.
 * <p>
 * This custom service must implement Spring's {@link UserDetailsService}
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CustomUserDetailsService implements UserDetailsService {
	
	//protected static Logger logger = Logger.getLogger("service");

	@Autowired
    private LoginCustomerService loginCustomerService;
	
	@Autowired
	 private UserSessionScopedData userSessionScopedData;
	
	/**
	 * Retrieves a user record containing the user's credentials and access. 
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException, LockedException {
		
		
		System.out.println("INSIDE CustomUserDetailsService.loadUserByUsername-------------> ");
		// Declare a null Spring User
		UserDetails user = null;
		
		
			if(username==null || username.trim().length()==0)
			{
				System.out.println("User name is required!");
				//throw new UsernameNotFoundException("Username not found!");
				
				throw new UsernameNotFoundException("Username not found!");
			}
			
			// Search database for a user that matches the specified username
			// You can provide a custom DAO to access your persistence layer
			// Or use JDBC to access your database
			// DbUser is our custom domain user. This is not the same as Spring's User
			Customers customer = loginCustomerService.getCustomers(username);
			
			// Populate the Spring User object with details from the dbUser
			// Here we just pass the username, password, and access level
			// getAuthorities() will translate the access level to the correct role type
			
			
			if(customer==null)
			{
				System.out.println("CUSTOMER IS NULL");
				
				throw new UsernameNotFoundException("Username not found!");
				
				/*user =  new CustomUserImplementation(
						null, 
						null,
						false,
						false,
						false,
						false,
						null,null);*/				
			}
			else if(customer!=null && customer.getUserRoles()==4)
			{//NOT ENABLED---so throw disabledException
				
				System.out.println("CUSTOMER IS NOT ENABLED");
				
				user =  new CustomUserImplementation(
						customer.getUserId(), 
						customer.getUserPassword().toLowerCase(),
						false,
						true,
						true,
						true,
						getAuthorities(customer.getUserRoles()),customer);
			}
			else
			{
				System.out.println("CUSTOMER IS ENABLED");

			
			user =  new CustomUserImplementation(
					customer.getUserId(), 
					customer.getUserPassword().toLowerCase(),
					true,
					true,
					true,
					true,
					getAuthorities(customer.getUserRoles()),customer);
			}

		
		// Return user to Spring for processing.
		// Take note we're not the one evaluating whether this user is authenticated or valid
		// We just merely retrieve a user that matches the specified username
		
		// You will retrieve the user object from database and then you will create the UserDetails object, with the password from database, Spring will then take this UserDetails object and 
		//verifies that passowrd with the passowrd user entered. You also need to provide the password encoder.
		
		return user;
	}
	
	/**
	 * Retrieves the correct ROLE type depending on the access level, where access level is an Integer.
	 * Basically, this interprets the access value whether it's for a regular user or admin.
	 * 
	 * @param access an integer value representing the access of the user
	 * @return collection of granted authorities
	 * 
	 * 1 --- ADMIN
	 * 2 --- BUSINESS
	 * 3 --- USER
	 * 4 --- NONACTIVE
	 */
	 public Collection<GrantedAuthority> getAuthorities(Integer access) {
			// Create a list of grants for this user
			List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(3);
			
			// All users are granted with ROLE_USER access
			// Therefore this user gets a ROLE_USER by default
			//logger.debug("Grant ROLE_USER to this user");
			
			System.out.println("USER ACCESS NOW------> "+access);
			
			if(access.intValue()==1)
			{
				authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
				authList.add(new GrantedAuthorityImpl("ROLE_BUSINESS"));
				authList.add(new GrantedAuthorityImpl("ROLE_USER"));			
			}
			else if(access.intValue()==2)
			{				
				authList.add(new GrantedAuthorityImpl("ROLE_BUSINESS"));
			}
			else if(access.intValue()==3)
			{
				authList.add(new GrantedAuthorityImpl("ROLE_USER"));
			}
			else
			{//4
				authList.add(new GrantedAuthorityImpl("ROLE_NONACTIVE"));
			}
			
		/*	authList.add(new GrantedAuthorityImpl("ROLE_COMMON"));
			//authList.add(new GrantedAuthorityImpl("ROLE_USER"));
			
			
			// Check if this user has admin access 
			// We interpret Integer(1) as an admin user
			if ( access.compareTo(1) == 0) {
				// User has admin access
				//logger.debug("Grant ROLE_ADMIN to this user");
				authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			}
*/
			// Return list of granted authorities
			return authList;
	  }
}
