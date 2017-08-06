package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import com.rendevous.intention.trick.spring.model.Customers;

public interface ForgotPasswordDAO {
	
	public Customers getCustomers(String emailID);

}
