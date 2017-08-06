package com.rendevous.intention.trick.spring.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.ItemManagerDAO;
import com.rendevous.intention.trick.spring.model.Items;

@Service
public class ItemManagerServiceImpl implements ItemManagerService{

	@Autowired
    private ItemManagerDAO itemManagerDAO;
	

	@Transactional
	public void addItem(Items item) {
		// TODO Auto-generated method stub
		itemManagerDAO.addItem(item);
	}

	@Transactional
	public List<Items> getAllItems() {
		// TODO Auto-generated method stub
		return itemManagerDAO.getAllItems();
	}
	
	@Transactional
	public List<Items> getAllItemsByFinalBidDate(String date) {
		// TODO Auto-generated method stub
		return itemManagerDAO.getAllItemsByFinalBidDate(date);
	}
	
	@Transactional
	public List<Items> getItemsByBidClosed(String date, String statusCode) {
		// TODO Auto-generated method stub
		return itemManagerDAO.getItemsByBidClosed(date,statusCode);
	}
	
	@Transactional
	public List<Items> getItemsByBidClosed(String date) {
		// TODO Auto-generated method stub
		return itemManagerDAO.getItemsByBidClosed(date);
	}

	@Transactional
	public List<Items> getItemsOfCustomer(Integer id) {
		// TODO Auto-generated method stub
		return itemManagerDAO.getItemsOfCustomer(id);
	}

	@Transactional
	public Items getItem(Integer id) {
		// TODO Auto-generated method stub
		return itemManagerDAO.getItem(id);
	}

	@Transactional
	public void removeItem(Integer id) {
		// TODO Auto-generated method stub
		itemManagerDAO.removeItem(id);
	}

	@Transactional
	public void updateItem(Items item) {
		// TODO Auto-generated method stub
		itemManagerDAO.updateItem(item);
	}

	

}
