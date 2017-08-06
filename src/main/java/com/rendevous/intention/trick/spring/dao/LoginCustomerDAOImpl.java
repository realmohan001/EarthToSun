package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.Customers;


@Repository
public class LoginCustomerDAOImpl implements LoginCustomerDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	public List<Customers> listCustomers() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Customers").list();
	}


	@Override
	public Customers getCustomers(String userID, String password) {
		// TODO Auto-generated method stub
		List<Customers> customersList = sessionFactory.getCurrentSession().createQuery("from Customers where userId='"+userID+"' and userPassword='"+password+"'").list();
		
		if(customersList!=null && customersList.size()>0)
		{
			return customersList.get(0);
		}
		else
		{
			return null;
		}
		
		
	}
	
	@Override
	public Customers getCustomers(String userID) {
		// TODO Auto-generated method stub
		List<Customers> customersList = sessionFactory.getCurrentSession().createQuery("from Customers where userId='"+userID+"'").list();
		
		if(customersList!=null && customersList.size()>0)
		{
			return customersList.get(0);
		}
		else
		{
			return null;
		}
		
		
	}

}
