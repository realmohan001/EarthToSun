package com.rendevous.intention.trick.spring.model;

import java.io.Serializable;

public class LoginForm implements Serializable{
	
	private String loginUserID;
	private String loginPassword;
	

	public void Loginform()
	{
		
	}


	public String getLoginUserID() {
		return loginUserID;
	}


	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}


	public String getLoginPassword() {
		return loginPassword;
	}


	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	
	
	


}
