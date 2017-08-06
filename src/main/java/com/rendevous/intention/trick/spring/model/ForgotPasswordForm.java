package com.rendevous.intention.trick.spring.model;

import java.io.Serializable;

public class ForgotPasswordForm implements Serializable{
	
	private String customerEmailID;
	

	public void ForgotPasswordForm()
	{
		
	}


	public String getCustomerEmailID() {
		return customerEmailID;
	}



	public void setCustomerEmailID(String customerEmailID) {
		this.customerEmailID = customerEmailID;
	}
	


}
