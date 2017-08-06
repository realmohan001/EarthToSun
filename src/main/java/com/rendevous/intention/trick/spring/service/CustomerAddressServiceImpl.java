package com.rendevous.intention.trick.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.CustomerAddressDAO;
import com.rendevous.intention.trick.spring.model.CustomerAddress;

@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {
	
	@Autowired
    private CustomerAddressDAO customerAddressDAO;

	@Transactional
	public List<CustomerAddress> listCustomerAddresses() {
		// TODO Auto-generated method stub
		return customerAddressDAO.listCustomerAddresses();
	}

	@Transactional
	public List<CustomerAddress> getCustomerAddresses(Integer customerID) {
		// TODO Auto-generated method stub
		return customerAddressDAO.getCustomerAddresses(customerID);
	}

	@Transactional
	public CustomerAddress getCustomerAddress(Integer addressID) {
		// TODO Auto-generated method stub
		return customerAddressDAO.getCustomerAddress(addressID);
	}

	@Transactional
	public void removeAddress(Integer addressID) {
		// TODO Auto-generated method stub
		customerAddressDAO.removeAddress(addressID);
	}

	@Transactional
	public void updateAddress(CustomerAddress customerAddress) {
		// TODO Auto-generated method stub
		customerAddressDAO.updateAddress(customerAddress);
	}

	@Transactional
	public void addAddress(CustomerAddress customerAddress) {
		// TODO Auto-generated method stub
		customerAddressDAO.addAddress(customerAddress);
	}
	
}
