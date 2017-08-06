package com.rendevous.intention.trick.spring.service;

import java.util.List;

import com.rendevous.intention.trick.spring.model.Customers;

public interface ForgotPasswordService {
	
	public Customers getCustomers(String emailID);
	
}
