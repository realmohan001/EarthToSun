package com.rendevous.intention.trick.spring.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;

public class CustomValidatorUtil {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	//WITH SPECIAL CHARACTER.
	//((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})
	
	
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	
	private static final String PHONE_PATTERN = "\\d{3}-\\d{3}-\\d{4}";
	
	private static final String NUMERIC_PATTERN = "\\d+";
	
	
	 
	
	
	public static String userIDPatternCheck(String userID)
	{
		String errorString=null;
		
		//USER ID PATTERN
		 Pattern patternuserID = Pattern.compile(USERNAME_PATTERN);
	     Matcher matcherUserID = patternuserID.matcher(userID.toLowerCase());
	     if(!matcherUserID.matches())
			{//INVALID USER ID PATTERN
				//errors.rejectValue("userId", "invalid.userId.constraints");
	    	 	errorString="invalid.userId.constraints";
			}
	     
	     return errorString;
		
	}
	
	public static String passwordPatternCheck(String password)
	{
		String errorString=null;
		
		//PASSWORD PATTERN
		 Pattern patternPassword = Pattern.compile(PASSWORD_PATTERN);
	     Matcher matcherPassword = patternPassword.matcher(password);
	     if(!matcherPassword.matches())
			{//INVALID PASSWORD PATTERN
				//errors.rejectValue("userPassword", "invalid.password.constraints");
	    	 	errorString="invalid.password.constraints";
			}
	     return errorString;
	}

	
	
	public static String emailAddressPatternCheck(String emailAddress)
	{
		String errorString=null;
		
		//CHECK EMAIL ID PATTERN
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher;
		matcher = pattern.matcher(emailAddress);
		if(!matcher.matches())
		{//INVALID EMAIL ADRRESS PATTERN
			//errors.rejectValue("emailAddress", "invalid.emailAddress");
			errorString="invalid.emailAddress";
		}
		return errorString;
	}


	public static String phoneNumberPatternCheck(String phoneNumber)
	{
		String errorString=null;
		
		//PHONE NUMBER PATTERN
		 Pattern patternPhone = Pattern.compile(PHONE_PATTERN);
	     Matcher matcherPhone = patternPhone.matcher(phoneNumber);
	     if(!matcherPhone.matches())
			{//INVALID PHONE NUMBER PATTERN
				//errors.rejectValue("phoneNumber", "invalid.phoneNumber");
	    	 	errorString="invalid.phoneNumber";
			}
	     return errorString;
	}	
	
	
	
	public static String cardNumberPatternCheck(String cardNumber)
	{
		String errorString=null;
		
		//PHONE NUMBER PATTERN
		 Pattern patternCardNumber = Pattern.compile(NUMERIC_PATTERN);
	     Matcher matcherCardNumber = patternCardNumber.matcher(cardNumber);
	     if(!matcherCardNumber.matches())
			{//INVALID PHONE NUMBER PATTERN
				//errors.rejectValue("phoneNumber", "invalid.phoneNumber");
	    	 	errorString="invalid.payment.cardNumber.numeric.pattern";
			}
	     return errorString;
	}	
	
	
	public static String cardCvvPatternCheck(String cvv)
	{
		String errorString=null;
		
		//PHONE NUMBER PATTERN
		 Pattern patternCvv = Pattern.compile(NUMERIC_PATTERN);
	     Matcher matcherCvv = patternCvv.matcher(cvv);
	     if(!matcherCvv.matches())
			{//INVALID PHONE NUMBER PATTERN
				//errors.rejectValue("phoneNumber", "invalid.phoneNumber");
	    	 	errorString="invalid.payment.cvv.numeric.pattern";
			}
	     
	     if( !(cvv.length()==3 || cvv.length()==4) )
	     {
	    	 errorString="invalid.payment.cvv.numeric.pattern";
	     }
	     
	     
	     return errorString;
	}	

}
