package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.CustomerAddress;


@Repository
public class CustomerAddressDAOImpl implements CustomerAddressDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<CustomerAddress> listCustomerAddresses() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from CustomerAddress").list();
	}

	@Override
	public List<CustomerAddress> getCustomerAddresses(Integer customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerAddress getCustomerAddress(Integer addressID) {
		// TODO Auto-generated method stub
		List<CustomerAddress> customerAddressList = null;
		customerAddressList = sessionFactory.getCurrentSession().createQuery("from CustomerAddress where addressId="+addressID.toString()).list();
		
		if(customerAddressList!=null && customerAddressList.size()>0)
		{
			return customerAddressList.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public void removeAddress(Integer addressID) {
		// TODO Auto-generated method stub
		
		CustomerAddress customerAddress = (CustomerAddress) sessionFactory.getCurrentSession().load(CustomerAddress.class, addressID);
        if (null != customerAddress) {
            sessionFactory.getCurrentSession().delete(customerAddress);
        }
	}

	@Override
	public void updateAddress(CustomerAddress customerAddress) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(customerAddress);
	}

	@Override
	public void addAddress(CustomerAddress customerAddress) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(customerAddress);
	}

	

}
