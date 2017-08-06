package com.rendevous.intention.trick.spring.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.scheduledtasks.ScheduledTasksService;
import com.rendevous.intention.trick.spring.service.BidsManagerService;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.util.DateUtil;


@Controller
public class BidsManagerController {
	
	@Autowired
	ServletContext context; 
	
	@Autowired
	private ScheduledTasksService scheduledTasksService;
	
	@Autowired
	private ItemManagerService itemManagerService;
	
	@Autowired
	private BidsManagerService bidsManagerService;
	
	
	@Autowired
	 private UserSessionScopedData userSessionScopedData;
	
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	@RequestMapping(value = "/ClosedItemsBids", method = RequestMethod.GET)
	public String ClosedItemsBids(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult result, SessionStatus status,Model model,Errors errors){
 		
		System.out.println("TEST ClosedItemsBids INIT FORM METHOD -------> ");
		List<Items> closedItemsList = itemManagerService.getItemsByBidClosed(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"));
		
		userSessionScopedData.setClosedItems(closedItemsList);
		//return form view
		return "ClosedBidsPage";
	}
	
	
	

}
