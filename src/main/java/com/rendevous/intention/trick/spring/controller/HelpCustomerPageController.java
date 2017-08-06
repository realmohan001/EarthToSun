package com.rendevous.intention.trick.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.rendevous.intention.trick.spring.service.ItemManagerService;

@Controller
public class HelpCustomerPageController {
	
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;
	 
	 @Autowired
	    private ItemManagerService itemManagerService;
	

	@RequestMapping("/HelpCustomer.htm")
	public String defaultMethod(Model model, SessionStatus status) {
		
			
			return "HelpCustomer";
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
