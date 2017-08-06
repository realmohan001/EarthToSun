package com.rendevous.intention.trick.spring.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.support.SessionStatus;

import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.util.DateUtil;

@Controller
public class HomePageController {
	
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;
	 
	 @Autowired
	    private ItemManagerService itemManagerService;
	

	@RequestMapping("/")
	public String defaultMethod(Model model, SessionStatus status) {
		
			System.out.println("INSIDE HomePageController.defaultMethod******************");
			
			
             //GET ALL ITEMS TO DISPLAY AT HOMEPAGE
			
				//ALWAYS PULL THE ITEMS FROM DATABASE WHEN COMING TO HOMEPAGE
				System.out.println("INSIDE **defaultMethod**----->");
				//List<Items> allItemsList = itemManagerService.getAllItems();
				//FOR DATABASE SEARCHES ALWAYS FOLLOW "yyyy/MM/dd HH:mm"
				List<Items> allItemsList = itemManagerService.getAllItemsByFinalBidDate(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"));
				
				userSessionScopedData.setAllItems(allItemsList);
				
				//System.out.println("size----> "+ allItemsList.size());
	
			
			//RESET THE PAGINATION COUNTER
			if(userSessionScopedData.getAllItems()!=null && userSessionScopedData.getAllItems().size()>0)
			{
				
				System.out.println("size----> "+ userSessionScopedData.getAllItems().size());
				
				//RESET IT TO 6
				userSessionScopedData.setRecordsPerPage(6);
				
				userSessionScopedData.setTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getAllItems().size()/userSessionScopedData.getRecordsPerPage()) ) );
				
				System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getTotalPages());
				
				userSessionScopedData.setBeginRecord(0);
				userSessionScopedData.setEndRecord(userSessionScopedData.getRecordsPerPage()-1);
				
				//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
				if(userSessionScopedData.getEndRecord() > userSessionScopedData.getAllItems().size())
				{
					userSessionScopedData.setEndRecord(userSessionScopedData.getAllItems().size()-1);
				}
				
				
				userSessionScopedData.setCurrentPage(1);
				
			}
			
			return "index";
		}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HH:MM");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	

}
