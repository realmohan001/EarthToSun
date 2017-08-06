package com.rendevous.intention.trick.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.ForgotPasswordDAO;
import com.rendevous.intention.trick.spring.model.Customers;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	
	@Autowired
    private ForgotPasswordDAO forgotPasswordDAO;


	@Transactional
	public Customers getCustomers(String emailID) {
		// TODO Auto-generated method stub
		return forgotPasswordDAO.getCustomers(emailID);
	}

	
}
