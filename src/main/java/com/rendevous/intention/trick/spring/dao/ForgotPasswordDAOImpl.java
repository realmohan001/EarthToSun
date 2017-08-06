package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.Customers;


@Repository
public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public Customers getCustomers(String emailID) {
		// TODO Auto-generated method stub
		List<Customers> customersList = sessionFactory.getCurrentSession().createQuery("from Customers where emailAddress='"+emailID+"'").list();

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
