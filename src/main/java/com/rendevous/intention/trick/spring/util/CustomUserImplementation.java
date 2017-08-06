package com.rendevous.intention.trick.spring.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rendevous.intention.trick.spring.model.Customers;

public class CustomUserImplementation implements UserDetails{
	
	private final Customers customer;
	// Spring Security related methods and instance Vars
    /*private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;*/
    
    private final String password;
    private final String userName;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    
   public CustomUserImplementation(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, Customers customer)
    {
    	this.userName=username;
    	this.password=password;
    	this.authorities=authorities;
    	this.enabled=enabled;
    	this.credentialsNonExpired=credentialsNonExpired;
    	this.accountNonExpired=accountNonExpired;
    	this.accountNonLocked=accountNonLocked;
    	this.customer=customer;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}

	public Customers getCustomer() {
		return this.customer;
	}	

}
