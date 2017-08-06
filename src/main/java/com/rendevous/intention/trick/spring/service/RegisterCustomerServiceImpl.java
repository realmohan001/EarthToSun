package com.rendevous.intention.trick.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.RegisterCustomerDAO;
import com.rendevous.intention.trick.spring.model.Customers;

@Service
public class RegisterCustomerServiceImpl implements RegisterCustomerService {
	
	@Autowired
    private RegisterCustomerDAO registerCustomerDAO;

	
	@Transactional	
	public void addCustomer(Customers customer) {
		// TODO Auto-generated method stub
		registerCustomerDAO.addCustomer(customer);		
	}

	@Transactional
	public List<Customers> listCustomers() {
		return registerCustomerDAO.listCustomers();
	}

	@Transactional
	public void removeCustomer(Integer id) {
		// TODO Auto-generated method stub
		registerCustomerDAO.removeCustomer(id);
	}



	@Transactional
	public Customers getCustomer(Integer id) {
		// TODO Auto-generated method stub
		return registerCustomerDAO.getCustomer(id);
	}

	@Transactional
	public void updateCustomer(Customers customer) {
		// TODO Auto-generated method stub
		registerCustomerDAO.updateCustomer(customer);
		
	}
	
	@Transactional
	 public Customers checkCustomerWithEmailAndUserID(String emailAddress, String userID)
	{
		return registerCustomerDAO.checkCustomerWithEmailAndUserID(emailAddress, userID);
	}
	

}
