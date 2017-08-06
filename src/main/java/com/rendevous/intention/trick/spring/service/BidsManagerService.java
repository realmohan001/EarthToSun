package com.rendevous.intention.trick.spring.service;

import java.util.List;

import com.rendevous.intention.trick.spring.model.CustomerBids;
import com.rendevous.intention.trick.spring.model.Items;

public interface BidsManagerService {
	
	public void addBid(CustomerBids bid);
	public List<CustomerBids> getAllBids();
	public List<CustomerBids> getBidsOfCustomer(Integer id);	
	public CustomerBids getBid(Integer id);
	public CustomerBids getWinningBidOfItem(Integer itemID);
	public void removeBid(Integer id);
	public void updateBid(CustomerBids bid);
	

}
