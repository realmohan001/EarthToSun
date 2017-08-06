package com.rendevous.intention.trick.spring.dao;

import java.util.Date;
import java.util.List;


import com.rendevous.intention.trick.spring.model.Items;

public interface ItemManagerDAO {
	
	public void addItem(Items item);
	public List<Items> getAllItems();
	public List<Items> getAllItemsByFinalBidDate(String date);
	public List<Items> getItemsByBidClosed(String date, String statusCode);
	public List<Items> getItemsByBidClosed(String date);
	public List<Items> getItemsOfCustomer(Integer id);	
	public Items getItem(Integer id);
	public void removeItem(Integer id);
	public void updateItem(Items item);
	

}
