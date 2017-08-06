package com.rendevous.intention.trick.spring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.model.ForgotPasswordForm;
import com.rendevous.intention.trick.spring.model.LoginForm;
import com.rendevous.intention.trick.spring.service.ForgotPasswordService;



public class ForgotPasswordValidator implements Validator{
	
	 @Autowired
	    private ForgotPasswordService forgotPasswordService;
	 
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return LoginForm.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerEmailID",
			"required.customerEmailID", "Field name is required.");
		
		ForgotPasswordForm forgotPasswordForm = (ForgotPasswordForm)target;
		
		if(errors!=null && !errors.hasErrors())
		{//IF THERE ARE NO FIELD LEVEL ERRORS, THEN ONLY DO THE VALIDATIONS
			Customers customer = forgotPasswordService.getCustomers(forgotPasswordForm.getCustomerEmailID());
			if(customer==null)
			{
				errors.rejectValue("customerEmailID", "invalid.customerEmailID");
			}			
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