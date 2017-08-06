package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;

public interface CustomerPaymentInformationDAO {
	
	public List<CustomerPaymentInformation> listCustomerPaymentInformations();
	public List<CustomerPaymentInformation> getCustomerPaymentInformations(Integer customerID);
	public CustomerPaymentInformation getCustomerPaymentInformation(Integer paymentInformationID);
	
	public void removePaymentInformation(Integer paymentInformationID);
    public void updatePaymentInformation(CustomerPaymentInformation customerPaymentInformation);
	public void addPaymentInformation(CustomerPaymentInformation customerPaymentInformation);
    

}
