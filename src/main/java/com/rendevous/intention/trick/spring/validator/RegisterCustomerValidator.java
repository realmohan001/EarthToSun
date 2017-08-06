package com.rendevous.intention.trick.spring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rendevous.intention.trick.spring.model.Customers;



public class RegisterCustomerValidator implements Validator{
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	//WITH SPECIAL CHARACTER.
	//((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})
	
	
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	 
	@Override
	public boolean supports(Class clazz) {
		//just validate the Customer instances
		return Customers.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salutation", "required.salutation", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
			"required.firstName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
			"required.lastName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress",
			"required.emailAddress", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", 
			"required.userId", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", 
			"required.userPassword", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmUserPassword", 
				"required.confirmUserPassword", "Field name is required.");
		
 
		Customers cust = (Customers)target;
 
		if(!(cust.getUserPassword().equals(cust.getConfirmUserPassword()))){
			errors.rejectValue("userPassword", "notmatch.userPassword");
		}
		
	/*	if("NONE".equals(cust.getSalutation())){
			errors.rejectValue("salutation", "required.salutation");
		}*/
		
		
	/*	if(cust.getUserId().length()<8 || cust.getUserId().length()>20 )
		{
			errors.rejectValue("userId", "userId.lenght");
		}		
		*/
		
		if(errors!=null && !errors.hasErrors())
		{//IF THERE ARE NO FIELD LEVEL ERRORS, THEN ONLY DO THE VALIDATIONS
			
			
			//USER ID PATTERN
			String  userIDResult = CustomValidatorUtil.userIDPatternCheck(cust.getUserId());
			
			if(userIDResult!=null)
			{
				errors.rejectValue("userId", userIDResult);
			}
			
			
			//PASSWORD PATTERN
			String  passwordResult = CustomValidatorUtil.passwordPatternCheck(cust.getUserPassword());
			
			if(passwordResult!=null)
			{
				errors.rejectValue("userPassword", passwordResult);
			}
			
		
			//CHECK EMAIL ID PATTERN
			String  emailResult = CustomValidatorUtil.emailAddressPatternCheck(cust.getEmailAddress());
			
			if(emailResult!=null)
			{
				errors.rejectValue("emailAddress", emailResult);
			}
		
			if(cust.getPhoneNumber()!=null && cust.getPhoneNumber().trim().length()>0)
			{
				//PHONE NUMBER PATTERN
				String  phoneResult = CustomValidatorUtil.phoneNumberPatternCheck(cust.getPhoneNumber());
				
				if(phoneResult!=null)
				{
					errors.rejectValue("phoneNumber", phoneResult);
				}
			}
			
			
			if(!cust.getAcceptedTermsAndConditions())
			{
				errors.rejectValue("acceptedTermsAndConditions", "required.acceptTermsAndConditions");
			}
			
		
		}
		
	}
}