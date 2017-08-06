package com.rendevous.intention.trick.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.model.LoginForm;
import com.rendevous.intention.trick.spring.service.LoginCustomerService;


@SessionAttributes("thought")
public class LoginCustomerValidator implements Validator{
	
	 @Autowired
	    private LoginCustomerService loginCustomerService;
	 
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return LoginForm.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginUserID",
			"required.loginUserID", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginPassword",
			"required.loginPassword", "Field name is required.");
		
 
		LoginForm loginForm = (LoginForm)target;
		
		if(errors!=null && !errors.hasErrors())
		{//IF THERE ARE NO FIELD LEVEL ERRORS, THEN ONLY DO THE VALIDATIONS
		
			
		}
 
		/*if(!(cust.getUserPassword().equals(cust.getConfirmUserPassword()))){
			errors.rejectValue("userPassword", "notmatch.userPassword");
		}
		if(cust.getFavFramework().length==0){
			errors.rejectValue("favFramework", "required.favFrameworks");
		}
		if("NONE".equals(cust.getSalutation())){
			errors.rejectValue("salutation", "required.salutation");
		}*/
	}
}