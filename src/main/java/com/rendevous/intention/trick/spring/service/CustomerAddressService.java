package com.rendevous.intention.trick.spring.service;

import java.util.List;

import com.rendevous.intention.trick.spring.model.CustomerAddress;
import com.rendevous.intention.trick.spring.model.Customers;

public interface CustomerAddressService {
	
	public List<CustomerAddress> listCustomerAddresses();
	public List<CustomerAddress> getCustomerAddresses(Integer customerID);
	public CustomerAddress getCustomerAddress(Integer addressID);
	
	public void removeAddress(Integer addressID);
    public void updateAddress(CustomerAddress customerAddress);
	public void addAddress(CustomerAddress customerAddress);

}
