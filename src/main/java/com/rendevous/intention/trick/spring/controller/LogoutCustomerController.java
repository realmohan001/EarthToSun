package com.rendevous.intention.trick.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
 
@Controller
public class LogoutCustomerController{	

	
	@RequestMapping(value="/CustomerLogOut.htm", method = RequestMethod.GET)
	public String LogOutSuccessController(Model model, SessionStatus status) {
		
		System.out.println("LogOutSuccessController method-----> ");
		return "LogOutSuccess";
	}
	
}