package com.rendevous.intention.trick.spring.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.util.DateUtil;

@Repository
public class ItemManagerDAOImpl implements ItemManagerDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	

	@Override
	public void addItem(Items item) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		session.save(item);
	}

	@Override
	public List<Items> getAllItems() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Items> items = null;
		try {
			items = (List<Items>)session.createQuery("from Items").list();

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
	@Override
	public List<Items> getAllItemsByFinalBidDate(String date) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Items> items = null;
		try {			
			items = (List<Items>)session.createQuery("from Items where itemFinalTimeToBid>'"+date+"' order by itemFinalTimeToBid asc").list();			

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public List<Items> getItemsByBidClosed(String date, String statusCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Items> items = null;
		try {			
			items = (List<Items>)session.createQuery("from Items where itemFinalTimeToBid < '"+date+"' and itemStatusCode="+statusCode+" order by itemFinalTimeToBid asc").list();			

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public List<Items> getItemsByBidClosed(String date) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Items> items = null;
		try {			
			items = (List<Items>)session.createQuery("from Items where itemFinalTimeToBid < '"+date+"' order by itemFinalTimeToBid asc").list();			

		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return items;
	}
	

	@Override
	public List<Items> getItemsOfCustomer(Integer id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		List<Items> items = null;
		try {
			items = (List<Items>)session.createQuery("from Items where customers.customerId="+id.toString()).list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public Items getItem(Integer id) {
		// TODO Auto-generated method stub
		
		System.out.println("Item ID ----> "+id.toString());
			
		List<Items> itemsList = sessionFactory.getCurrentSession().createQuery("from Items where itemId="+id.toString()).list();
		
		if(itemsList!=null && itemsList.size()>0)
		{
			return itemsList.get(0);
		}
		else
		{
			return null;
		}		
	}

	@Override
	public void removeItem(Integer id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Items item = (Items)session.get(Items.class, id);
		
		if(item!=null)
		{
		session.delete(item);
		}
	}

	@Override
	public void updateItem(Items item) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().update(item);
	}
	

}
