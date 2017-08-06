package com.rendevous.intention.trick.spring.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsModel {
	
	List<Items> searchResultsItems = null;
	
	private int searchCurrentPage=1;
	private int searchTotalPages=0;
	private int searchRecordsPerPage=6;
	private int searchBeginRecord=0;
	private int searchEndRecord=0;
	
	
	
	
	/**
	 * @param searchResultsItems
	 * @param searchCurrentPage
	 * @param searchTotalPages
	 * @param searchRecordsPerPage
	 * @param searchBeginRecord
	 * @param searchEndRecord
	 */
	public SearchResultsModel(List<Items> searchResultsItems,
			int searchCurrentPage, int searchTotalPages,
			int searchRecordsPerPage, int searchBeginRecord, int searchEndRecord) {
		super();
		this.searchResultsItems = searchResultsItems;
		this.searchCurrentPage = searchCurrentPage;
		this.searchTotalPages = searchTotalPages;
		this.searchRecordsPerPage = searchRecordsPerPage;
		this.searchBeginRecord = searchBeginRecord;
		this.searchEndRecord = searchEndRecord;
	}
	/**
	 * @return the searchResultsItems
	 */
	public List<Items> getSearchResultsItems() {
		return searchResultsItems;
	}
	/**
	 * @param searchResultsItems the searchResultsItems to set
	 */
	public void setSearchResultsItems(List<Items> searchResultsItems) {
		this.searchResultsItems = searchResultsItems;
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
	
	
}
