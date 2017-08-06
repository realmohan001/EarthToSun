package com.rendevous.intention.trick.spring.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.rendevous.intention.trick.spring.model.ForgotPasswordForm;
import com.rendevous.intention.trick.spring.service.ForgotPasswordService;
import com.rendevous.intention.trick.spring.validator.ForgotPasswordValidator;
 
@Controller
@RequestMapping("/ForgotPassword.htm")
public class ForgotPasswordController{
	
	 @Autowired
	    private ForgotPasswordService forgotPasswordService;
 
	 ForgotPasswordValidator forgotPasswordValidator;
 
	@Autowired
	public ForgotPasswordController(ForgotPasswordValidator forgotPasswordValidator){
		this.forgotPasswordValidator = forgotPasswordValidator;
	}
 
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("FORGOT_PASSWORD") ForgotPasswordForm forgotPasswordForm, BindingResult result, SessionStatus status, Model model) {
 
		forgotPasswordValidator.validate(forgotPasswordForm, result);
 
		if (result.hasErrors()) {
			//if validator failed
			return "ForgotPassword";
		} else {
			status.setComplete();
			
			//WRITE THE LOGIC TO SEND THE EMAIL TO THE CUSTOMER
			
			//form success
			return "index";
		}
	}
 
	@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
 
		ForgotPasswordForm forgotPasswordForm = new ForgotPasswordForm();
				//initilize a hidden value
		//cust.setSecretValue("I'm hidden value");
 
		//command object
		model.addAttribute("FORGOT_PASSWORD", forgotPasswordForm);
 
		//return form view
		return "ForgotPassword";
	}
 
 
	 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
 
	}
 
	
}