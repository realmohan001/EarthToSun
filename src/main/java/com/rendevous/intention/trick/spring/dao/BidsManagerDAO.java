package com.rendevous.intention.trick.spring.dao;

import java.util.List;


import com.rendevous.intention.trick.spring.model.CustomerBids;
import com.rendevous.intention.trick.spring.model.Items;

public interface BidsManagerDAO {
	
	public void addBid(CustomerBids bid);
	public List<CustomerBids> getAllBids();
	public List<CustomerBids> getBidsOfCustomer(Integer id);
	public CustomerBids getWinningBidOfItem(Integer itemID);
	public CustomerBids getBid(Integer id);
	public void removeBid(Integer id);
	public void updateBid(CustomerBids bid);
	
	

}
