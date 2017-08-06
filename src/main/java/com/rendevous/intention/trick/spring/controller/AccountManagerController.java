package com.rendevous.intention.trick.spring.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.thymeleaf.context.Context;

import com.rendevous.intention.trick.spring.email.EmailServiceCalls;
import com.rendevous.intention.trick.spring.model.AccountManager;
import com.rendevous.intention.trick.spring.model.CustomerAddress;
import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.service.CustomerAddressService;
import com.rendevous.intention.trick.spring.service.CustomerPaymentService;
import com.rendevous.intention.trick.spring.service.LoginCustomerService;
import com.rendevous.intention.trick.spring.service.RegisterCustomerService;
import com.rendevous.intention.trick.spring.util.CreditCardValidator;
import com.rendevous.intention.trick.spring.validator.CustomValidatorUtil;
 
@Controller
public class AccountManagerController{
	
	 @Autowired 
	    private EmailServiceCalls emailServiceCalls;

	
	@Autowired
	 private UserSessionScopedData userSessionScopedData;
	
	 @Autowired
	    private RegisterCustomerService registerCustomerService;
	 
	 @Autowired
	 private CustomerPaymentService customerPaymentService;
	 
	 @Autowired
	 private CustomerAddressService customerAddressService;
	 
	 
	 @Autowired
	    private LoginCustomerService loginCustomerService;
	 

	 static final Md5PasswordEncoder md5PasswordEncoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
	
	
	
 
	@RequestMapping(value = "/AccountInfo.htm", method = RequestMethod.GET)
	public String processSubmit(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
 		
		return "AccountInfo";
	}
	
	
	@RequestMapping(value = "/AddressDetails.htm", method = RequestMethod.GET)
	public String processAddressDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
		
		AccountManager accountManager = new AccountManager();
		
		if(userSessionScopedData!=null && userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerAddresses()!=null && userSessionScopedData.getCustomer().getCustomerAddresses().size()>0)
		{
			Set<CustomerAddress> addressesSet = userSessionScopedData.getCustomer().getCustomerAddresses();
			
			for(CustomerAddress customerAddress : addressesSet)
			{
				
				System.out.println("customerAddress---ID "+ customerAddress.getAddressId());
				
				//SET THE BILLING ADDRESS TO ACCOUNT FOR DISPLAY AND EDIT
				if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().trim().equals("BILLING"))
				{
					accountManager.setCustomerBillingAddress(customerAddress);
				}
				
				//SET THE SHIPPING ADDRESS TO ACCOUNT FOR DISPLAY AND EDIT
				if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().trim().equals("SHIPPING"))
				{
					accountManager.setCustomerShippingAddress(customerAddress);
				}
			}
		}
		
		if(userSessionScopedData.getCountryList()==null || userSessionScopedData.getCountryList().size()==0)
		{
			//SET THE COUNTRY LIST HEREE
			List<String> countryList= new ArrayList<String>();		
			
			String[] locales = Locale.getISOCountries();
			
			for(String countryCode : locales)
			{
				Locale obj = new Locale("", countryCode);
				countryList.add(obj.getDisplayCountry());
			}
			
			userSessionScopedData.setCountryList(countryList);
		}	
		
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		
		//System.out.println("accountManager-----> shipping address ID "+ accountManager.getCustomerShippingAddress().getAddressId());
		//System.out.println("accountManager-----> billing address ID "+ accountManager.getCustomerBillingAddress().getAddressId());
		
		
		
		
 		
		return "AddressDetails";
	}
	
	
	
	@RequestMapping(value = "/AddressDetailsEditBillingAddress.htm")
	public String processAddressDetailsEditBillingPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors,Locale locale ) {
		
		System.out.println("INSIDE processAddressDetailsEditBillingPost METHOD-----> ");
		
		//ADDRESS
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.companyName", "required.addressBilling.companyName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressContactPhoneNumber", "required.addressBilling.addressContactPhoneNumber", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressLine1", "required.addressBilling.addressLine1", "Field name is required.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressLine2", "required.addressBilling.addressLine2", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.postalZipcode", "required.addressBilling.postalZipcode", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.city", "required.addressBilling.city", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.state", "required.addressBilling.state", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.country", "required.addressBilling.country", "Field name is required.");
		
		
		//country VALIDATION
		if(accountManager!=null && accountManager.getCustomerBillingAddress()!=null && accountManager.getCustomerBillingAddress().getCountry()!=null && accountManager.getCustomerBillingAddress().getCountry().trim().equals("NONE"))
		{
			errors.rejectValue("customerBillingAddress.country", "required.addressBilling.country");
		}
		
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "AddressDetails";
		}
		
		
		
		//UPDATE BILLING ADDRESS IN SESSION 
		if(userSessionScopedData!=null && userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerAddresses()!=null&& userSessionScopedData.getCustomer().getCustomerAddresses().size()>0)
		{
			Set<CustomerAddress> addressesSet = userSessionScopedData.getCustomer().getCustomerAddresses();
			
			for(CustomerAddress customerAddress : addressesSet)
			{
				//SET THE BILLING ADDRESS TO ACCOUNT FOR DISPLAY AND EDIT
				if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().trim().equals("BILLING"))
				{
					accountManager.getCustomerBillingAddress().setAddressId(customerAddress.getAddressId());
					accountManager.getCustomerBillingAddress().setCustomers(userSessionScopedData.getCustomer());
					accountManager.getCustomerBillingAddress().setAddressType("BILLING");
					addressesSet.remove(customerAddress);
					addressesSet.add(accountManager.getCustomerBillingAddress());					
					break;
				}
			}
			
			userSessionScopedData.getCustomer().setCustomerAddresses(addressesSet);
		}
		
		
		//UPDATE BILLING DATABASE
		customerAddressService.updateAddress(accountManager.getCustomerBillingAddress());
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		model.addAttribute("SUCCESS_RESULT","Billing address updated successfully!");
		
		
		//SEND EMAIL NOW
		
				Context ctx = new Context();
		        ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
		        
				
		        try {        	
		        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
		        	emailServiceCalls.cutomerAddressChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }
				
		
		return "AddressDetails";
	}
	
	
	@RequestMapping(value = "/AddressDetailsEditShippingAddress.htm")
	public String processAddressDetailsEditShippingPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors,Locale locale ) {
		
		System.out.println("INSIDE processAddressDetailsEditShippingPost METHOD-----> ");
		
		
		//SHIPPING ADDRESS
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.companyName", "required.addressShipping.companyName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressContactPhoneNumber", "required.addressShipping.addressContactPhoneNumber", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressLine1", "required.addressShipping.addressLine1", "Field name is required.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressLine2", "required.addressShipping.addressLine2", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.postalZipcode", "required.addressShipping.postalZipcode", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.city", "required.addressShipping.city", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.state", "required.addressShipping.state", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.country", "required.addressShipping.country", "Field name is required.");
		
		//country VALIDATION
		if(accountManager!=null && accountManager.getCustomerShippingAddress()!=null && accountManager.getCustomerShippingAddress().getCountry()!=null && accountManager.getCustomerShippingAddress().getCountry().trim().equals("NONE"))
		{
			errors.rejectValue("customerShippingAddress.country", "required.addressShipping.country");
		}
		
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "AddressDetails";
		}
		
		//UPDATE SHIPPING ADDRESS IN SESSION 
		if(userSessionScopedData!=null && userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerAddresses()!=null&& userSessionScopedData.getCustomer().getCustomerAddresses().size()>0)
		{
			Set<CustomerAddress> addressesSet = userSessionScopedData.getCustomer().getCustomerAddresses();
			
			for(CustomerAddress customerAddress : addressesSet)
			{
				//SET THE SHIPPING ADDRESS TO ACCOUNT FOR DISPLAY AND EDIT
				if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().trim().equals("SHIPPING"))
				{
					accountManager.getCustomerShippingAddress().setAddressId(customerAddress.getAddressId());
					accountManager.getCustomerShippingAddress().setCustomers(userSessionScopedData.getCustomer());
					accountManager.getCustomerShippingAddress().setAddressType("SHIPPING");
					
					addressesSet.remove(customerAddress);
					addressesSet.add(accountManager.getCustomerShippingAddress());					
					break;
				}
			}
			
			userSessionScopedData.getCustomer().setCustomerAddresses(addressesSet);
		}
		
		
		//UPDATE SHIPPING DATABASE
		customerAddressService.updateAddress(accountManager.getCustomerShippingAddress());
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		model.addAttribute("SUCCESS_RESULT","Shipping address updated successfully!");
		
		
		//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Welcome "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName());	        
        
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerAddressChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }		
		
		return "AddressDetails";
	}
	
	
	@RequestMapping(value = "/PaymentDetails.htm", method = RequestMethod.GET)
	public String processPaymentDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
		
		AccountManager accountManager = new AccountManager();
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
 		
		
		if(userSessionScopedData!=null && userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerPaymentInformations()!=null&& userSessionScopedData.getCustomer().getCustomerPaymentInformations().size()>0)
		{
			Set<CustomerPaymentInformation> paymentSet = userSessionScopedData.getCustomer().getCustomerPaymentInformations();
			
			for(CustomerPaymentInformation paymentInformation : paymentSet)
			{
				System.out.println("paymentInformation---ID "+ paymentInformation.getPaymentCardId());
				
				//SET THE PAYMENT INFORMATION
				if(paymentInformation!=null)
				{
					accountManager.setCustomerPaymentInformation(paymentInformation);
				}
			}
		}
		
		
		if(userSessionScopedData.getMonthList()==null || userSessionScopedData.getMonthList().size()==0)
		{		
		    //SET THE MONTH LIST HEREE
			List<String> monthList= new ArrayList<String>();
			
			monthList.add("01");
			monthList.add("02");
			monthList.add("03");
			monthList.add("04");
			monthList.add("05");
			monthList.add("06");
			monthList.add("07");
			monthList.add("08");
			monthList.add("09");
			monthList.add("10");
			monthList.add("11");
			monthList.add("12");
		
			userSessionScopedData.setMonthList(monthList);
		}	
			
		
		if(userSessionScopedData.getYearList()==null || userSessionScopedData.getYearList().size()==0)
		{
		
		    //SET THE YEAR LIST HEREE
			List<String> yearList= new ArrayList<String>();
			
			int year = Calendar.getInstance().get(Calendar.YEAR);
			
			for(int i=0; i<30; i++)
			{	
				//yearList.add(String.valueOf(year));
				yearList.add(String.valueOf(year++));
			}
			
			userSessionScopedData.setYearList(yearList);
		}
		
		
		//System.out.println("accountManager-----> payment ID "+ accountManager.getCustomerPaymentInformation().getPaymentCardId());
		
		return "PaymentDetails";
	}
	
	
	
	@RequestMapping(value = "/PaymentDetails.htm")
	public String processPaymentDetailsPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors, Locale locale ) {
		
		System.out.println("INSIDE processPaymentDetailsPost METHOD-----> ");
		System.out.println("accountManager inside post-----> payment ID "+ accountManager.getCustomerPaymentInformation().getPaymentCardId());
		
		//PAYMENT ADDRESS
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.nameOnCard", "required.payment.nameOnCard", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.billingContactPhoneNumber", "required.payment.billingContactPhoneNumber", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardType", "required.payment.cardType", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardNumber", "required.payment.cardNumber", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cvv", "required.payment.cvv", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationMonth", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardExpirationYear", "required.payment.cardExpirationYear", "Field name is required.");
		
		//CARD TYPE VALIDATION
		if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equals("NONE"))
		{
			errors.rejectValue("customerPaymentInformation.cardType", "required.payment.cardType");
		}
		
		//cardExpirationMonth VALIDATION
		if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationMonth()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationMonth().trim().equals("NONE"))
		{
			errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationMonth");
		}
		
		//cardExpirationYear VALIDATION
		if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationYear()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationYear().trim().equals("NONE"))
		{
			errors.rejectValue("customerPaymentInformation.cardExpirationYear", "required.payment.cardExpirationYear");
		}
		
		//payment PHONE NUMBER PATTERN CHECK
		String  paymentPhoneResult = CustomValidatorUtil.phoneNumberPatternCheck(accountManager.getCustomerPaymentInformation().getBillingContactPhoneNumber());
					
		if(paymentPhoneResult!=null)
		{
			errors.rejectValue("customerPaymentInformation.billingContactPhoneNumber", "invalid.payment.contactPhoneNumber.pattern");
		}
		
		
		//payment CardNumber PATTERN CHECK
		String  cardNumberResult = CustomValidatorUtil.cardNumberPatternCheck(accountManager.getCustomerPaymentInformation().getCardNumber());
					
		if(cardNumberResult!=null)
		{
			errors.rejectValue("customerPaymentInformation.cardNumber", cardNumberResult);
		}
		
		
		//payment CVV PATTERN CHECK
		String  cardCvvResult = CustomValidatorUtil.cardCvvPatternCheck(accountManager.getCustomerPaymentInformation().getCvv());
						
		if(cardCvvResult!=null)
		{
			errors.rejectValue("customerPaymentInformation.cvv", cardCvvResult);
		}
		
		//IS IT VALID PAYMENT CARD NUMBER
				CreditCardValidator creditCardValidator = new CreditCardValidator();
				boolean creditCardCheckResult=false;
				
				if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("Visa"))
				{
					creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 1);
				}
				else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("MasterCard"))
				{
					creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 0);
				}
				else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("American Express"))
				{
					creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 2);
				}
				else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("Discover"))
				{
					creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 3);
				}

									
				if(!creditCardCheckResult)
				{//IF NOT VALID
					errors.rejectValue("customerPaymentInformation.cardNumber", creditCardValidator.getMessage());
				}
				
				
				//PAYMENT CARD expiration month and year check
				Calendar cal = Calendar.getInstance();
		        
		        int month = cal.get(Calendar.MONTH) + 1;
		        int year = cal.get(Calendar.YEAR);
				
		   
		        
		        if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationYear()) < year)
		        {//OLD YEAR
		        	errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationDate");
		        }
			
     		      //IF YEAR SELECTED IS SAME AS CURRENT YEAR, CHECK THE MONTH ALSO
		        if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationYear()) == year)
		        {//OLD YEAR
		            if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationMonth()) < month)
			        {//OLD MONTH
			        	errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationDate");
			        }
			        
		        }
			
			
		
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "PaymentDetails";
		}
		
		
		//UPDATE PAYMENT INFORMATION IN SESSION 
		if(userSessionScopedData!=null && userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerPaymentInformations()!=null&& userSessionScopedData.getCustomer().getCustomerPaymentInformations().size()>0)
		{
			Set<CustomerPaymentInformation> paymentSet = userSessionScopedData.getCustomer().getCustomerPaymentInformations();
			
			for(CustomerPaymentInformation paymentInformation : paymentSet)
			{
				//SET THE CUSTOMER PAYMENT TO ACCOUNT FOR DISPLAY AND EDIT
				if(paymentInformation!=null)
				{
					accountManager.getCustomerPaymentInformation().setPaymentCardId(paymentInformation.getPaymentCardId());
					accountManager.getCustomerPaymentInformation().setCustomers(userSessionScopedData.getCustomer());
					paymentSet.remove(paymentInformation);
					paymentSet.add(accountManager.getCustomerPaymentInformation());					
					break;
				}
			}
			
			userSessionScopedData.getCustomer().setCustomerPaymentInformations(paymentSet);
		}
		
		
		//UPDATE PATMENT INFORMATION TO DATABASE
		customerPaymentService.updatePaymentInformation(accountManager.getCustomerPaymentInformation());
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		model.addAttribute("SUCCESS_RESULT","Payment details updated successfully!");
		
		//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
        
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerPaymentChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }	
		
		
		return "PaymentDetails";
	}
	
	
	
	
	
	@RequestMapping(value = "/UserIDDetails.htm", method = RequestMethod.GET)
	public String processUserIDDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
		
		AccountManager accountManager = new AccountManager();
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		return "UserIDDetails";
	}
	
	
	@RequestMapping(value = "/UserIDDetails.htm")
	public String processUserIDDetailsPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ,Errors errors, Locale locale) {
		
		System.out.println("INSIDE processUserIDDetailsPost METHOD-----> ");
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentUserPassword", "required.account.currentUserPassword", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newUserId", "required.account.newUserId", "Field name is required.");
		
		//USER ID PATTERN
		String  userIDResult = CustomValidatorUtil.userIDPatternCheck(accountManager.getNewUserId());
		
		if(userIDResult!=null)
		{
			errors.rejectValue("newUserId", userIDResult);
		}
		
		
		//GET THE OBJECT FROM DATABASE BASED ON THE USERID
		Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID("", accountManager.getNewUserId().trim());
		
		if(customerFromDB!=null && customerFromDB.getUserId().trim().equalsIgnoreCase(accountManager.getNewUserId().trim()))
		{
			errors.rejectValue("newUserId", "taken.account.newUserId");
		}
		
		Customers customerFromSession = userSessionScopedData.getCustomer();
		
		
		String md5EncodedPassword = md5PasswordEncoder.encodePassword(accountManager.getCurrentUserPassword(), null);
		System.out.println("registration encoded password----> " + md5EncodedPassword);		
		
		Customers customer = loginCustomerService.getCustomers(customerFromSession.getUserId(),md5EncodedPassword);
		if(customer==null)
		{
			errors.rejectValue("currentUserPassword", "invalid.account.currentUserPassword");
		}
		
		
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "UserIDDetails";
		}
		
		
		customerFromSession.setUserId(accountManager.getNewUserId().trim());
		
		//UPDATE DATABASE
		registerCustomerService.updateCustomer(customerFromSession);
		
		//UPDATE SESSION WITH NEW CUSTOMER OBJECT WITH MODIFED USER id
		userSessionScopedData.setCustomer(customerFromSession);
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		model.addAttribute("SUCCESS_RESULT","User ID updated Successfully!");
		
		//SEND EMAIL NOW
		
				Context ctx = new Context();
		        ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
		        
				
		        try {        	
		        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
		        	emailServiceCalls.cutomerUserIDChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }	
				
		
		return "UserIDDetails";
	}
	
	
	@RequestMapping(value = "/PasswordDetails.htm", method = RequestMethod.GET)
	public String processPasswordDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
		
		AccountManager accountManager = new AccountManager();
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		return "PasswordDetails";
	}
	
	
	@RequestMapping(value = "/PasswordDetails.htm")
	public String processPasswordDetailsPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors, Locale locale ) {
		
		System.out.println("INSIDE processPasswordDetailsPost METHOD-----> ");
		
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentUserPassword", "required.account.currentUserPassword", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newUserPassword", "required.account.newUserPassword", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmNewUserPassword", "required.account.confirmNewUserPassword", "Field name is required.");
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "PasswordDetails";
		}
		
		
		//VERIFY EXISTING PASSWORD
		Customers customerFromSession = userSessionScopedData.getCustomer();		
		
		String md5EncodedPassword = md5PasswordEncoder.encodePassword(accountManager.getCurrentUserPassword(), null);
		System.out.println("registration encoded password----> " + md5EncodedPassword);		
		
		Customers customer = loginCustomerService.getCustomers(customerFromSession.getUserId(),md5EncodedPassword);
		if(customer==null)
		{
			errors.rejectValue("currentUserPassword", "invalid.account.currentUserPassword");
		}
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "PasswordDetails";
		}
		
		
		//CHECK FOR MATCH OF PASSWORD AND CONFIRMPASSWORD
		if(!accountManager.getNewUserPassword().trim().equals(accountManager.getConfirmNewUserPassword()))
		{
			errors.rejectValue("confirmNewUserPassword", "invalid.account.password.mismatch");
		}
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "PasswordDetails";
		}
		
		
		//PASSWORD PATTERN CHECK
		String  passwordResult = CustomValidatorUtil.passwordPatternCheck(accountManager.getNewUserPassword());
		
		if(passwordResult!=null)
		{
			errors.rejectValue("newUserPassword", "invalid.account.password.pattern");
		}
		

		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "PasswordDetails";
		}
		
		String md5EncodedNewPassword = md5PasswordEncoder.encodePassword(accountManager.getNewUserPassword(), null);
		System.out.println("change password encoded ----> " + md5EncodedNewPassword);		
	
		
		//SET THE ENCODED PASSWORD
		customerFromSession.setUserPassword(md5EncodedNewPassword);
		
		//UPDATE DATABASE
		registerCustomerService.updateCustomer(customerFromSession);
		
		//UPDATE SESSION WITH NEW CUSTOMER OBJECT WITH MODIFED USER id
		userSessionScopedData.setCustomer(customerFromSession);
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		model.addAttribute("SUCCESS_RESULT","Password updated Successfully!");
		
		//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
        
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerPasswordChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }	
		
		
		
		return "PasswordDetails";
	}
	
	
 
	
	@RequestMapping(value = "/EmailAddressDetails.htm", method = RequestMethod.GET)
	public String processEmailAddressDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
 	
		AccountManager accountManager = new AccountManager();
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		return "EmailAddressDetails";
	}
	
	
	@RequestMapping(value = "/EmailAddressDetails.htm")
	public String processEmailAddressDetailsPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ,Errors errors, Locale locale) {
		
		System.out.println("INSIDE processEmailAddressDetailsPost METHOD-----> ");
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newEmailAddress", "required.account.newEmailAddress", "Field name is required.");
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "EmailAddressDetails";
		}
		
		//EMAIL ADDRESS PATTERN CHECK
		String  emailAddressResult = CustomValidatorUtil.emailAddressPatternCheck(accountManager.getNewEmailAddress());
				
		if(emailAddressResult!=null)
		{
			errors.rejectValue("newEmailAddress", "invalid.account.newEmailAddress.pattern");
		}
		
		
		
		//GET THE OBJECT FROM DATABASE BASED ON THE USERID
		Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID(accountManager.getNewEmailAddress().trim(), "");
				
		if(customerFromDB!=null && customerFromDB.getEmailAddress().trim().equalsIgnoreCase(accountManager.getNewEmailAddress().trim()))
		{
			errors.rejectValue("newEmailAddress", "taken.account.newEmailAddress");
		}
		
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "EmailAddressDetails";
		}
		
		
		//GET CURRENT CUSTMER FROM SESSION
		Customers customerFromSession = userSessionScopedData.getCustomer();
		
		customerFromSession.setEmailAddress(accountManager.getNewEmailAddress());
		
		//UPDATE DATABASE
		registerCustomerService.updateCustomer(customerFromSession);
		
		//UPDATE SESSION WITH NEW CUSTOMER OBJECT WITH MODIFED USER id
		userSessionScopedData.setCustomer(customerFromSession);
		
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		model.addAttribute("SUCCESS_RESULT","Email Address updated Successfully!");
		
		
		//SEND EMAIL NOW
		
				Context ctx = new Context();
		        ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
		        
				
		        try {        	
		        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
		        	emailServiceCalls.cutomerEmailChange(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
		        }
		        catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        }	
				
				
		
		
		return "EmailAddressDetails";
	}
	
	
	
	
	
	@RequestMapping(value = "/AccountPreferencesDetails.htm", method = RequestMethod.GET)
	public String processAccountPreferencesDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
 		
		return "AccountPreferencesDetails";
	}
	
	
	


@RequestMapping(value = "/fillBillingAndCardDetails.htm", method = RequestMethod.GET)
public String fillBillingAndCardDetails(Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ) {
     
	System.out.println("---------------inside fillBillingAndCardDetails call---------> ");
	
	AccountManager accountManager = new AccountManager();
	
	
	if(userSessionScopedData.getMonthList()==null || userSessionScopedData.getMonthList().size()==0)
	{
	
	    //SET THE MONTH LIST HEREE
		List<String> monthList= new ArrayList<String>();
		
		monthList.add("01");
		monthList.add("02");
		monthList.add("03");
		monthList.add("04");
		monthList.add("05");
		monthList.add("06");
		monthList.add("07");
		monthList.add("08");
		monthList.add("09");
		monthList.add("10");
		monthList.add("11");
		monthList.add("12");
	
		userSessionScopedData.setMonthList(monthList);
	}	
		
	
	if(userSessionScopedData.getYearList()==null || userSessionScopedData.getYearList().size()==0)
	{
	
	    //SET THE YEAR LIST HEREE
		List<String> yearList= new ArrayList<String>();
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int i=0; i<30; i++)
		{	
			//yearList.add(String.valueOf(year));
			yearList.add(String.valueOf(year++));
		}
		
		userSessionScopedData.setYearList(yearList);
	}	
		
	
	if(userSessionScopedData.getCountryList()==null || userSessionScopedData.getCountryList().size()==0)
	{
		//SET THE COUNTRY LIST HEREE
		List<String> countryList= new ArrayList<String>();		
		
		String[] locales = Locale.getISOCountries();
		
		for(String countryCode : locales)
		{
			Locale obj = new Locale("", countryCode);
			countryList.add(obj.getDisplayCountry());
		}
		
		userSessionScopedData.setCountryList(countryList);
	}	
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
	
	return "BillingAddressPaymentDetails";
}
	
@RequestMapping(value = "/fillBillingAndCardDetails.htm")
public String fillBillingAndCardDetailsPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response ,Errors errors, Locale locale) {
	
	
	model.addAttribute("ACCOUNT_MANAGER", accountManager);
	
	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.nameOnCard", "required.payment.nameOnCard", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.billingContactPhoneNumber", "required.payment.billingContactPhoneNumber", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardType", "required.payment.cardType", "Field name is required.");
	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardNumber", "required.payment.cardNumber", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cvv", "required.payment.cvv", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationMonth", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerPaymentInformation.cardExpirationYear", "required.payment.cardExpirationYear", "Field name is required.");
	
	
	
	
	//ADDRESS
	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.companyName", "required.addressBilling.companyName", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressContactPhoneNumber", "required.addressBilling.addressContactPhoneNumber", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressLine1", "required.addressBilling.addressLine1", "Field name is required.");
	//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.addressLine2", "required.addressBilling.addressLine2", "Field name is required.");
	
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.postalZipcode", "required.addressBilling.postalZipcode", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.city", "required.addressBilling.city", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.state", "required.addressBilling.state", "Field name is required.");
	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerBillingAddress.country", "required.addressBilling.country", "Field name is required.");
	
	//CARD TYPE VALIDATION
	if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equals("NONE"))
	{
		errors.rejectValue("customerPaymentInformation.cardType", "required.payment.cardType");
	}
	
	//cardExpirationMonth VALIDATION
	if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationMonth()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationMonth().trim().equals("NONE"))
	{
		errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationMonth");
	}
	
	//cardExpirationYear VALIDATION
	if(accountManager!=null && accountManager.getCustomerPaymentInformation()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationYear()!=null && accountManager.getCustomerPaymentInformation().getCardExpirationYear().trim().equals("NONE"))
	{
		errors.rejectValue("customerPaymentInformation.cardExpirationYear", "required.payment.cardExpirationYear");
	}
	
	//country VALIDATION
	if(accountManager!=null && accountManager.getCustomerBillingAddress()!=null && accountManager.getCustomerBillingAddress().getCountry()!=null && accountManager.getCustomerBillingAddress().getCountry().trim().equals("NONE"))
	{
		errors.rejectValue("customerBillingAddress.country", "required.addressBilling.country");
	}
	
	//payment PHONE NUMBER PATTERN CHECK
	String  paymentPhoneResult = CustomValidatorUtil.phoneNumberPatternCheck(accountManager.getCustomerPaymentInformation().getBillingContactPhoneNumber());
				
	if(paymentPhoneResult!=null)
	{
		errors.rejectValue("customerPaymentInformation.billingContactPhoneNumber", "invalid.payment.contactPhoneNumber.pattern");
	}
	
	
	
	//SHIPPING ADDRESS VALIDATION
	
	// VALIDATION
		if(accountManager!=null && !accountManager.isShippingSameAsBillingAddress())
		{//IT MEANS SHIPPING ADDRESS IS NOT SAME AS BILLING ADDRESS
			
			//SHIPPING ADDRESS
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.companyName", "required.addressShipping.companyName", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressContactPhoneNumber", "required.addressShipping.addressContactPhoneNumber", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressLine1", "required.addressShipping.addressLine1", "Field name is required.");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.addressLine2", "required.addressShipping.addressLine2", "Field name is required.");
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.postalZipcode", "required.addressShipping.postalZipcode", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.city", "required.addressShipping.city", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.state", "required.addressShipping.state", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerShippingAddress.country", "required.addressShipping.country", "Field name is required.");
			
			//country VALIDATION
			if(accountManager!=null && accountManager.getCustomerShippingAddress()!=null && accountManager.getCustomerShippingAddress().getCountry()!=null && accountManager.getCustomerShippingAddress().getCountry().trim().equals("NONE"))
			{
				errors.rejectValue("customerShippingAddress.country", "required.addressShipping.country");
			}
			
		}
	
	if(errors!=null && errors.hasErrors())
	{//IF THER ARE ERRORS
		return "BillingAddressPaymentDetails";
	}
	
	
	
	//ADDITIONAL CHECKING
	

	//payment CardNumber PATTERN CHECK
	String  cardNumberResult = CustomValidatorUtil.cardNumberPatternCheck(accountManager.getCustomerPaymentInformation().getCardNumber());
				
	if(cardNumberResult!=null)
	{
		errors.rejectValue("customerPaymentInformation.cardNumber", cardNumberResult);
	}
	
	//IS IT VALID PAYMENT CARD NUMBER
		CreditCardValidator creditCardValidator = new CreditCardValidator();
		boolean creditCardCheckResult=false;
		
		if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("Visa"))
		{
			creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 1);
		}
		else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("MasterCard"))
		{
			creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 0);
		}
		else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("American Express"))
		{
			creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 2);
		}
		else if(accountManager.getCustomerPaymentInformation().getCardType()!=null && accountManager.getCustomerPaymentInformation().getCardType().trim().equalsIgnoreCase("Discover"))
		{
			creditCardCheckResult = creditCardValidator.isCreditCardValid(accountManager.getCustomerPaymentInformation().getCardNumber(), 3);
		}

							
		if(!creditCardCheckResult)
		{//IF NOT VALID
			errors.rejectValue("customerPaymentInformation.cardNumber", creditCardValidator.getMessage());
		}
		
		
		//PAYMENT CARD expiration month and year check
		Calendar cal = Calendar.getInstance();
        
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        
        
   	

        
        if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationYear()) < year)
        {//OLD YEAR
        	errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationDate");
        }
        
        
        //IF YEAR SELECTED IS SAME AS CURRENT YEAR, CHECK THE MONTH ALSO
        if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationYear()) == year)
        {//SAME YEAR
        	
            if(Integer.parseInt(accountManager.getCustomerPaymentInformation().getCardExpirationMonth()) < month)
            {//OLD MONTH
            	errors.rejectValue("customerPaymentInformation.cardExpirationMonth", "required.payment.cardExpirationDate");
            }
        }
	
	
	
	//payment CVV PATTERN CHECK
	String  cardCvvResult = CustomValidatorUtil.cardCvvPatternCheck(accountManager.getCustomerPaymentInformation().getCvv());
					
	if(cardCvvResult!=null)
	{
		errors.rejectValue("customerPaymentInformation.cvv", cardCvvResult);
	}
	
	
	
	if(errors!=null && errors.hasErrors())
	{//IF THER ARE ERRORS
		return "BillingAddressPaymentDetails";
	}
	
	
	
	
	
	
	CustomerPaymentInformation customerPaymentInformation = accountManager.getCustomerPaymentInformation();
	customerPaymentInformation.setCustomers(userSessionScopedData.getCustomer());
		
	
	//ADD PAYMENT IN DATABASE
	customerPaymentService.addPaymentInformation(customerPaymentInformation);	
	//UPDATE SESSION WITH NEW PAYMENT DETAILS
	userSessionScopedData.getCustomer().getCustomerPaymentInformations().add(customerPaymentInformation);
	
	
	CustomerAddress customerBillingAddress = accountManager.getCustomerBillingAddress();
	customerBillingAddress.setCustomers(userSessionScopedData.getCustomer());
	customerBillingAddress.setAddressType("BILLING");
	
	
	//ADD BILLING ADDRESS IN DATABASE
	customerAddressService.addAddress(customerBillingAddress);	
	//UPDATE SESSION WITH BILLING ADDRESS DETAILS	
	userSessionScopedData.getCustomer().getCustomerAddresses().add(customerBillingAddress);
	
	
	//FOR SHIPPING ADDRESS
	if(accountManager!=null && accountManager.isShippingSameAsBillingAddress())
	{
		
		CustomerAddress customerShippingAddress = new CustomerAddress();
		
		customerShippingAddress.setAddressContactPhoneNumber(customerBillingAddress.getAddressContactPhoneNumber());
		customerShippingAddress.setAddressFirstName(customerBillingAddress.getAddressFirstName());
		customerShippingAddress.setAddressLastName(customerBillingAddress.getAddressLastName());
		customerShippingAddress.setAddressLine1(customerBillingAddress.getAddressLine1());
		customerShippingAddress.setAddressLine2(customerBillingAddress.getAddressLine2());
		customerShippingAddress.setAddressType("SHIPPING");
		customerShippingAddress.setCity(customerBillingAddress.getCity());
		customerShippingAddress.setCountry(customerBillingAddress.getCountry());
		customerShippingAddress.setCompanyName(customerBillingAddress.getCompanyName());
		customerShippingAddress.setPostalZipcode(customerBillingAddress.getPostalZipcode());
		customerShippingAddress.setState(customerBillingAddress.getState());		
		
		customerShippingAddress.setCustomers(userSessionScopedData.getCustomer());
		
		//ADD SHIPPING ADDRESS IN DATABASE
		customerAddressService.addAddress(customerShippingAddress);
		
		//UPDATE SESSION WITH SHIPPING ADDRESS DETAILS	
		userSessionScopedData.getCustomer().getCustomerAddresses().add(customerShippingAddress);
	}
	else
	{
		//ADD SHIPPING ADDRESS IN DATABASE
		CustomerAddress customerShippingAddress = accountManager.getCustomerShippingAddress();
		customerShippingAddress.setAddressType("SHIPPING");
		customerShippingAddress.setCustomers(userSessionScopedData.getCustomer());
		
		customerAddressService.addAddress(customerShippingAddress);
		
		//UPDATE SESSION WITH SHIPPING ADDRESS DETAILS	
		userSessionScopedData.getCustomer().getCustomerAddresses().add(customerShippingAddress);		
	}
	
	
	//SEND EMAIL NOW
	
	Context ctx = new Context();
    ctx.setVariable("name", "Hello, "+userSessionScopedData.getCustomer().getFirstName()+" "+userSessionScopedData.getCustomer().getLastName()+"!");	        
    
	
    try {        	
    	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
    	emailServiceCalls.cutomerPaymentAndAddressAdded(userSessionScopedData.getCustomer().getEmailAddress(), ctx, locale);
    }
    catch(Exception ex)
    {
    	ex.printStackTrace();
    }		
	
		 
	
	return "BillingAddressPaymentSuccess";
}	

	 
	

	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String restartFlow() {
	    return "redirect:/LoginCustomer";
	}
	
}