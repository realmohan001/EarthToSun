package com.rendevous.intention.trick.spring.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.util.DateUtil;

@Controller
public class SearchResultsController {
	
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;
	 
	 @Autowired
	    private ItemManagerService itemManagerService;
	


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:MM");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	
	

@RequestMapping(value = "/searchController", method = RequestMethod.POST)
public String searchController(@RequestParam(value="searchWord", required=true) String searchWord, RedirectAttributes redirectAttributes, Model model ) {
	

	if(userSessionScopedData.getSearchResults()!=null && userSessionScopedData.getSearchResults().size()>0)
	{//CLEAR THE LIST EVERYTIME
		userSessionScopedData.getSearchResults().clear();
	}

	if(searchWord==null || searchWord.trim().length()==0)
	{
		return "searchResultsPage";
	}
	
	//GET THE ITEMS FROM DATABASE
	List<Items> allItemsList = itemManagerService.getAllItemsByFinalBidDate(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"));
	
	if(allItemsList!=null && allItemsList.size()>0)
	{
		String[] searchWordArray = searchWord.trim().split("\\s");
		List<Items> searchResultsList = new ArrayList<Items>();
		String addedToSearchResultsListIDs = "";
		
		for(String searchString : searchWordArray)
		{
			
			for(Items itemObj : allItemsList)
			{
				boolean isSearchResult=false;
				
				//CHECK BUSINESS NAME
				if(itemObj!=null && itemObj.getBusinessName()!=null)
				{
					if(itemObj.getBusinessName().indexOf(searchString.trim())!=-1)
					{//it is there
						isSearchResult=true;						
					}
				}
				
				//CHECK ITEM NAME
				if(itemObj!=null && itemObj.getItemName()!=null)
				{
					if(itemObj.getItemName().indexOf(searchString.trim())!=-1)
					{
						isSearchResult=true;						
					}
				}	
				
				//CHECK ITEM DESCRIPTION
				if(itemObj!=null && itemObj.getItemDescription()!=null)
				{
					if(itemObj.getItemDescription().indexOf(searchString.trim())!=-1)
					{
						isSearchResult=true;						
					}
				}	
				
				if(isSearchResult)
				{//WE NEED IT IN THE SEARCH RESULTS
					
					if(addedToSearchResultsListIDs.indexOf(itemObj.getItemId().toString()) == -1)
					{//IT IS NOT ADDED BEFORE ALREADY, SO ADD NOW
						searchResultsList.add(itemObj);
						addedToSearchResultsListIDs = addedToSearchResultsListIDs+" "+itemObj.getItemId().toString()+" ";
					}					
				}
			}

		}
		
		userSessionScopedData.setSearchResults(searchResultsList);
	}
	
		
	
	

	//RESET THE PAGINATION COUNTER
	if(userSessionScopedData.getSearchResults()!=null && userSessionScopedData.getSearchResults().size()>0)
	{
		
		System.out.println("size----> "+ userSessionScopedData.getSearchResults().size());
		
		//RESET IT TO 6
		userSessionScopedData.setSearchRecordsPerPage(6);
		
		userSessionScopedData.setSearchTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getSearchResults().size()/userSessionScopedData.getSearchRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getSearchTotalPages());
		
		userSessionScopedData.setSearchBeginRecord(0);
		userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchRecordsPerPage()-1);
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getSearchEndRecord() > userSessionScopedData.getSearchResults().size())
		{
			userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchResults().size()-1);
		}
		
		
		userSessionScopedData.setSearchCurrentPage(1);
		
	}
	
	return "searchResultsPage";
}



@RequestMapping(value = "/searchItemPagination/", method = RequestMethod.GET)
public String searchItemPaginationList(@RequestParam(value="pageNo", required=true) String pageNo, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getSearchResults()!=null && userSessionScopedData.getSearchResults().size()>0)
	{	
		//SET BEGIN INDEX
		if((Integer.parseInt(pageNo)-1) != 0)
		{
			userSessionScopedData.setSearchBeginRecord((Integer.parseInt(pageNo)-1) * userSessionScopedData.getSearchRecordsPerPage());
		}
		else
		{
			userSessionScopedData.setSearchBeginRecord(0);
		}
		
		//SET END RECORD
		userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchBeginRecord()+(userSessionScopedData.getSearchRecordsPerPage()-1));
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getSearchEndRecord() > userSessionScopedData.getSearchResults().size())
		{
			userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchResults().size()-1);
		}
		
		//SET THE CURRENT PAGE NUMBER
		userSessionScopedData.setSearchCurrentPage(Integer.parseInt(pageNo));

	}
	
	//return "ItemDetail2";
	return "searchResultsPage";
}


@RequestMapping(value = "/searchItemPaginationItemsPerPage", method = RequestMethod.POST)
public String searchItemPaginationitemsPerPage(@RequestParam(value="searchItemsPerPage", required=true) String itemsPerPage, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getSearchResults()!=null && userSessionScopedData.getSearchResults().size()>0)
	{		
		//CHANGE THE RECORDS PER PAGE
		userSessionScopedData.setSearchRecordsPerPage(Integer.parseInt(itemsPerPage));
		
		userSessionScopedData.setSearchTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getSearchResults().size()/userSessionScopedData.getSearchRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getSearchTotalPages());
		
		userSessionScopedData.setSearchBeginRecord(0);
		userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchRecordsPerPage()-1);
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getSearchEndRecord() > userSessionScopedData.getSearchResults().size())
		{
			userSessionScopedData.setSearchEndRecord(userSessionScopedData.getSearchResults().size()-1);
		}
		
		userSessionScopedData.setSearchCurrentPage(1);
	}
	
	//return "ItemDetail2";
	return "searchResultsPage";
}
	
	

}
