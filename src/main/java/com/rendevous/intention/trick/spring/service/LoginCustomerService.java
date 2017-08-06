package com.rendevous.intention.trick.spring.service;

import java.util.List;

import com.rendevous.intention.trick.spring.model.Customers;

public interface LoginCustomerService {
	
	public List<Customers> listCustomers();
	public Customers getCustomers(String userID, String password);
	public Customers getCustomers(String userID);
	
    

}
