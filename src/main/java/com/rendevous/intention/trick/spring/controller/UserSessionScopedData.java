package com.rendevous.intention.trick.spring.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.rendevous.intention.trick.spring.model.CustomerBids;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.model.Items;


@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserSessionScopedData  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Customers customer=null;
	String customerID=null;
	String currentBiddingItem=null;
	
	List<Items> cutomerItems = null;
	List<Items> allItems = null;
	List<Items> modifyItems = null;
	List<Items> searchResults = null;
	List<Items> closedItems = null;
	List<CustomerBids> myPlacedBids = null;

	
	private int currentPage=1;
	private int totalPages=0;
	private int recordsPerPage=6;
	private int beginRecord=0;
	private int endRecord=0;
	
	
	private int modifyCurrentPage=1;
	private int modifyTotalPages=0;
	private int modifyRecordsPerPage=6;
	private int modifyBeginRecord=0;
	private int modifyEndRecord=0;
	
	
	private int closedCurrentPage=1;
	private int closedTotalPages=0;
	private int closedRecordsPerPage=6;
	private int closedBeginRecord=0;
	private int closedEndRecord=0;
	
	private int placedBidsCurrentPage=1;
	private int placedBidsTotalPages=0;
	private int placedBidsRecordsPerPage=6;
	private int placedBidsBeginRecord=0;
	private int placedBidsEndRecord=0;
	
	
	
	private int searchCurrentPage=1;
	private int searchTotalPages=0;
	private int searchRecordsPerPage=6;
	private int searchBeginRecord=0;
	private int searchEndRecord=0;
	


	private List<String> monthList= null;
	private List<String> yearList= null;
	private List<String> countryList= null;
	private List<String> salutationList= null;
	private List<String> timeZoneList = null;	
	
	
	public List<String> getSalutationList() {
		return salutationList;
	}

	public void setSalutationList(List<String> salutationList) {
		this.salutationList = salutationList;
	}
	
	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	

	public List<Items> getCutomerItems() {
		return cutomerItems;
	}

	public void setCutomerItems(List<Items> cutomerItems) {
		this.cutomerItems = cutomerItems;
	}

	public List<Items> getAllItems() {
		return allItems;
	}

	public void setAllItems(List<Items> allItems) {
		this.allItems = allItems;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public List<String> getYearList() {
		return yearList;
	}

	public void setYearList(List<String> yearList) {
		this.yearList = yearList;
	}

	public List<String> getCountryList() {
		return countryList;
	}

	public void setCountryList(List<String> countryList) {
		this.countryList = countryList;
	}

	public List<String> getTimeZoneList() {
		return timeZoneList;
	}

	public void setTimeZoneList(List<String> timeZoneList) {
		this.timeZoneList = timeZoneList;
	}
	
	public List<Items> getClosedItems() {
		return closedItems;
	}

	public void setClosedItems(List<Items> closedItems) {
		this.closedItems = closedItems;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getBeginRecord() {
		return beginRecord;
	}

	public void setBeginRecord(int beginRecord) {
		this.beginRecord = beginRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	/**
	 * @return the modifyItems
	 */
	public List<Items> getModifyItems() {
		return modifyItems;
	}

	/**
	 * @param modifyItems the modifyItems to set
	 */
	public void setModifyItems(List<Items> modifyItems) {
		this.modifyItems = modifyItems;
	}

	/**
	 * @return the modifyCurrentPage
	 */
	public int getModifyCurrentPage() {
		return modifyCurrentPage;
	}

	/**
	 * @param modifyCurrentPage the modifyCurrentPage to set
	 */
	public void setModifyCurrentPage(int modifyCurrentPage) {
		this.modifyCurrentPage = modifyCurrentPage;
	}

	/**
	 * @return the modifyTotalPages
	 */
	public int getModifyTotalPages() {
		return modifyTotalPages;
	}

	/**
	 * @param modifyTotalPages the modifyTotalPages to set
	 */
	public void setModifyTotalPages(int modifyTotalPages) {
		this.modifyTotalPages = modifyTotalPages;
	}

	/**
	 * @return the modifyRecordsPerPage
	 */
	public int getModifyRecordsPerPage() {
		return modifyRecordsPerPage;
	}

	/**
	 * @param modifyRecordsPerPage the modifyRecordsPerPage to set
	 */
	public void setModifyRecordsPerPage(int modifyRecordsPerPage) {
		this.modifyRecordsPerPage = modifyRecordsPerPage;
	}

	/**
	 * @return the modifyBeginRecord
	 */
	public int getModifyBeginRecord() {
		return modifyBeginRecord;
	}

	/**
	 * @param modifyBeginRecord the modifyBeginRecord to set
	 */
	public void setModifyBeginRecord(int modifyBeginRecord) {
		this.modifyBeginRecord = modifyBeginRecord;
	}

	/**
	 * @return the modifyEndRecord
	 */
	public int getModifyEndRecord() {
		return modifyEndRecord;
	}

	/**
	 * @param modifyEndRecord the modifyEndRecord to set
	 */
	public void setModifyEndRecord(int modifyEndRecord) {
		this.modifyEndRecord = modifyEndRecord;
	}

	/**
	 * @return the searchResults
	 */
	public List<Items> getSearchResults() {
		return searchResults;
	}

	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<Items> searchResults) {
		this.searchResults = searchResults;
	}

	/**
	 * @return the searchCurrentPage
	 */
	public int getSearchCurrentPage() {
		return searchCurrentPage;
	}

	/**
	 * @param searchCurrentPage the searchCurrentPage to set
	 */
	public void setSearchCurrentPage(int searchCurrentPage) {
		this.searchCurrentPage = searchCurrentPage;
	}

	/**
	 * @return the searchTotalPages
	 */
	public int getSearchTotalPages() {
		return searchTotalPages;
	}

	/**
	 * @param searchTotalPages the searchTotalPages to set
	 */
	public void setSearchTotalPages(int searchTotalPages) {
		this.searchTotalPages = searchTotalPages;
	}

	/**
	 * @return the searchRecordsPerPage
	 */
	public int getSearchRecordsPerPage() {
		return searchRecordsPerPage;
	}

	/**
	 * @param searchRecordsPerPage the searchRecordsPerPage to set
	 */
	public void setSearchRecordsPerPage(int searchRecordsPerPage) {
		this.searchRecordsPerPage = searchRecordsPerPage;
	}

	/**
	 * @return the searchBeginRecord
	 */
	public int getSearchBeginRecord() {
		return searchBeginRecord;
	}

	/**
	 * @param searchBeginRecord the searchBeginRecord to set
	 */
	public void setSearchBeginRecord(int searchBeginRecord) {
		this.searchBeginRecord = searchBeginRecord;
	}

	/**
	 * @return the searchEndRecord
	 */
	public int getSearchEndRecord() {
		return searchEndRecord;
	}

	/**
	 * @param searchEndRecord the searchEndRecord to set
	 */
	public void setSearchEndRecord(int searchEndRecord) {
		this.searchEndRecord = searchEndRecord;
	}

	public int getClosedCurrentPage() {
		return closedCurrentPage;
	}

	public void setClosedCurrentPage(int closedCurrentPage) {
		this.closedCurrentPage = closedCurrentPage;
	}

	public int getClosedTotalPages() {
		return closedTotalPages;
	}

	public void setClosedTotalPages(int closedTotalPages) {
		this.closedTotalPages = closedTotalPages;
	}

	public int getClosedRecordsPerPage() {
		return closedRecordsPerPage;
	}

	public void setClosedRecordsPerPage(int closedRecordsPerPage) {
		this.closedRecordsPerPage = closedRecordsPerPage;
	}

	public int getClosedBeginRecord() {
		return closedBeginRecord;
	}

	public void setClosedBeginRecord(int closedBeginRecord) {
		this.closedBeginRecord = closedBeginRecord;
	}

	public int getClosedEndRecord() {
		return closedEndRecord;
	}

	public void setClosedEndRecord(int closedEndRecord) {
		this.closedEndRecord = closedEndRecord;
	}

	public List<CustomerBids> getMyPlacedBids() {
		return myPlacedBids;
	}

	public void setMyPlacedBids(List<CustomerBids> myPlacedBids) {
		this.myPlacedBids = myPlacedBids;
	}

	public int getPlacedBidsCurrentPage() {
		return placedBidsCurrentPage;
	}

	public void setPlacedBidsCurrentPage(int placedBidsCurrentPage) {
		this.placedBidsCurrentPage = placedBidsCurrentPage;
	}

	public int getPlacedBidsTotalPages() {
		return placedBidsTotalPages;
	}

	public void setPlacedBidsTotalPages(int placedBidsTotalPages) {
		this.placedBidsTotalPages = placedBidsTotalPages;
	}

	public int getPlacedBidsRecordsPerPage() {
		return placedBidsRecordsPerPage;
	}

	public void setPlacedBidsRecordsPerPage(int placedBidsRecordsPerPage) {
		this.placedBidsRecordsPerPage = placedBidsRecordsPerPage;
	}

	public int getPlacedBidsBeginRecord() {
		return placedBidsBeginRecord;
	}

	public void setPlacedBidsBeginRecord(int placedBidsBeginRecord) {
		this.placedBidsBeginRecord = placedBidsBeginRecord;
	}

	public int getPlacedBidsEndRecord() {
		return placedBidsEndRecord;
	}

	public void setPlacedBidsEndRecord(int placedBidsEndRecord) {
		this.placedBidsEndRecord = placedBidsEndRecord;
	}
	
	
	public String getCurrentBiddingItem() {
		return currentBiddingItem;
	}

	public void setCurrentBiddingItem(String currentBiddingItem) {
		this.currentBiddingItem = currentBiddingItem;
	}


	

}