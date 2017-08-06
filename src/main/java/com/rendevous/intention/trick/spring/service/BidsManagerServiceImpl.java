package com.rendevous.intention.trick.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rendevous.intention.trick.spring.dao.BidsManagerDAO;
import com.rendevous.intention.trick.spring.model.CustomerBids;

@Service
public class BidsManagerServiceImpl implements BidsManagerService {

	@Autowired
    private BidsManagerDAO bidsManagerDAO;
	

	@Transactional
	public void addBid(CustomerBids bid) {
		// TODO Auto-generated method stub
		bidsManagerDAO.addBid(bid);
	}

	@Transactional
	public List<CustomerBids> getAllBids() {
		// TODO Auto-generated method stub
		return bidsManagerDAO.getAllBids();
	}

	@Transactional
	public List<CustomerBids> getBidsOfCustomer(Integer id) {
		// TODO Auto-generated method stub
		return bidsManagerDAO.getBidsOfCustomer(id);
	}

	@Transactional
	public CustomerBids getBid(Integer id) {
		// TODO Auto-generated method stub
		return bidsManagerDAO.getBid(id);
	}
	
	@Transactional
	public CustomerBids getWinningBidOfItem(Integer itemID) {
		// TODO Auto-generated method stub
		return bidsManagerDAO.getWinningBidOfItem(itemID);
	}

	@Transactional
	public void removeBid(Integer id) {
		// TODO Auto-generated method stub
		bidsManagerDAO.removeBid(id);
	}

	@Transactional
	public void updateBid(CustomerBids bid) {
		// TODO Auto-generated method stub
		bidsManagerDAO.updateBid(bid);
	}

}
