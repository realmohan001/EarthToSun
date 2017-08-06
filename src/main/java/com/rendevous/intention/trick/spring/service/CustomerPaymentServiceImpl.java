package com.rendevous.intention.trick.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.CustomerPaymentInformationDAO;
import com.rendevous.intention.trick.spring.model.CustomerAddress;
import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;

@Service
public class CustomerPaymentServiceImpl implements CustomerPaymentService {
	
	@Autowired
    private CustomerPaymentInformationDAO customerPaymentInformationDAO;

	

	@Transactional
	public List<CustomerPaymentInformation> listCustomerPaymentInformations() {
		// TODO Auto-generated method stub
		return customerPaymentInformationDAO.listCustomerPaymentInformations();
	}

	@Transactional
	public List<CustomerPaymentInformation> getCustomerPaymentInformations(
			Integer customerID) {
		// TODO Auto-generated method stub
		return customerPaymentInformationDAO.getCustomerPaymentInformations(customerID);
	}

	@Transactional
	public CustomerPaymentInformation getCustomerPaymentInformation(
			Integer paymentInformationID) {
		// TODO Auto-generated method stub
		return customerPaymentInformationDAO.getCustomerPaymentInformation(paymentInformationID);
	}

	@Transactional
	public void removePaymentInformation(Integer paymentInformationID) {
		// TODO Auto-generated method stub
		customerPaymentInformationDAO.removePaymentInformation(paymentInformationID);
	}

	@Transactional
	public void updatePaymentInformation(
			CustomerPaymentInformation customerPaymentInformation) {
		// TODO Auto-generated method stub
		customerPaymentInformationDAO.updatePaymentInformation(customerPaymentInformation);
	}

	@Transactional
	public void addPaymentInformation(
			CustomerPaymentInformation customerPaymentInformation) {
		// TODO Auto-generated method stub
		customerPaymentInformationDAO.addPaymentInformation(customerPaymentInformation);
	}	
	
}
