package com.rendevous.intention.trick.spring.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.CustomerBids;

@Repository
public class BidsManagerDAOImpl implements BidsManagerDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public void addBid(CustomerBids bid) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(bid);
	}

	@Override
	public List<CustomerBids> getAllBids() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<CustomerBids> bids = null;
		try {
			bids = (List<CustomerBids>)session.createQuery("from CustomerBids").list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return bids;
	}

	@Override
	public List<CustomerBids> getBidsOfCustomer(Integer id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<CustomerBids> bids = null;
		try {
			
			bids = (List<CustomerBids>)session.createQuery("from CustomerBids where customers.customerId="+id.toString()).list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return bids;
	}

	@Override
	public CustomerBids getBid(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public CustomerBids getWinningBidOfItem(Integer itemID) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<CustomerBids> bids = null;
			
			bids = session.createQuery("from CustomerBids where items.itemId="+itemID.toString()+" and bidPrice=(select max(bidPrice * 1) from CustomerBids where items.itemId="+itemID.toString()+")").list();
			
			
			if(bids!=null && bids.size()>0)
			{
				return bids.get(0);
			}
			else
			{
				return null;
			}		
	}
	

	@Override
	public void removeBid(Integer id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		CustomerBids bid = (CustomerBids)session.get(CustomerBids.class, id);
		
		if(bid!=null)
		{
		session.delete(bid);
		}
	}

	@Override
	public void updateBid(CustomerBids bid) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(bid);
	}
	

}
