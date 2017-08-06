package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import com.rendevous.intention.trick.spring.model.Customers;

public interface RegisterCustomerDAO {
	
	public void addCustomer(Customers customer);
    public List<Customers> listCustomers();
    public void removeCustomer(Integer id);
    public void updateCustomer(Customers customer);
    public Customers getCustomer(Integer id);
    public Customers checkCustomerWithEmailAndUserID(String emailAddress, String userID);
    

}
