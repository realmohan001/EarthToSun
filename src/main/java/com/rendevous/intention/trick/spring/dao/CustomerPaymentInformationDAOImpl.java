package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;


@Repository
public class CustomerPaymentInformationDAOImpl implements CustomerPaymentInformationDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public List<CustomerPaymentInformation> listCustomerPaymentInformations() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from CustomerPaymentInformation").list();
	}

	@Override
	public List<CustomerPaymentInformation> getCustomerPaymentInformations(Integer customerID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerPaymentInformation getCustomerPaymentInformation(Integer paymentInformationID) {
		// TODO Auto-generated method stub
		List<CustomerPaymentInformation> customerPaymentInformationList = null;
		customerPaymentInformationList = sessionFactory.getCurrentSession().createQuery("from CustomerPaymentInformation where paymentCardId="+paymentInformationID.toString()).list();
		
		if(customerPaymentInformationList!=null && customerPaymentInformationList.size()>0)
		{
			return customerPaymentInformationList.get(0);
		}
		else
		{
			return null;
		}
	}

	@Override
	public void removePaymentInformation(Integer paymentInformationID) {
		// TODO Auto-generated method stub
		
		CustomerPaymentInformation customerPaymentInformation = (CustomerPaymentInformation) sessionFactory.getCurrentSession().load(CustomerPaymentInformation.class, paymentInformationID);
        if (null != customerPaymentInformation) {
            sessionFactory.getCurrentSession().delete(customerPaymentInformation);
        }
	}

	@Override
	public void updatePaymentInformation(CustomerPaymentInformation customerPaymentInformation) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(customerPaymentInformation);
	}

	@Override
	public void addPaymentInformation(CustomerPaymentInformation customerPaymentInformation) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().save(customerPaymentInformation);
	}

	

}
