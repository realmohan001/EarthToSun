package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.Customers;


@Repository
public class RegisterCustomerDAOImpl implements RegisterCustomerDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	
	public void addCustomer(Customers customer) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(customer);
	}

	
	public List<Customers> listCustomers() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from Customers").list();
	}

	
	public void removeCustomer(Integer id) {
		// TODO Auto-generated method stub
		
		Customers customer = (Customers) sessionFactory.getCurrentSession().load(Customers.class, id);
	        if (null != customer) {
	            sessionFactory.getCurrentSession().delete(customer);
	        }
		
	}


	@Override
	public void updateCustomer(Customers customer) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(customer);
	}


	@Override
	public Customers getCustomer(Integer id) {
		// TODO Auto-generated method stub
		
			List<Customers> customersList= null;
			customersList = sessionFactory.getCurrentSession().createQuery("from Customers where customerId="+id.toString()).list();
			
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
	 public Customers checkCustomerWithEmailAndUserID(String emailAddress, String userID)
	{
		List<Customers> customersList= null;
		customersList = sessionFactory.getCurrentSession().createQuery("from Customers where emailAddress='"+emailAddress.toString()+"' or userId='"+userID.toString()+"'" ).list();
		
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
