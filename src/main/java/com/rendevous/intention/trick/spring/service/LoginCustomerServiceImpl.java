package com.rendevous.intention.trick.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.LoginCustomerDAO;
import com.rendevous.intention.trick.spring.model.Customers;

@Service
public class LoginCustomerServiceImpl implements LoginCustomerService {
	
	@Autowired
    private LoginCustomerDAO loginCustomerDAO;

	
	
	@Transactional
	public List<Customers> listCustomers() {
		return loginCustomerDAO.listCustomers();
	}


	@Transactional
	public Customers getCustomers(String userID, String password) {
		// TODO Auto-generated method stub
		return loginCustomerDAO.getCustomers(userID, password);
	}

	@Transactional
	public Customers getCustomers(String userID) {
		// TODO Auto-generated method stub
		return loginCustomerDAO.getCustomers(userID);
	}

	
}
