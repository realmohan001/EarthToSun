package com.rendevous.intention.trick.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.context.Context;

import com.rendevous.intention.trick.spring.email.EmailServiceCalls;
import com.rendevous.intention.trick.spring.model.AccountManager;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.service.RegisterCustomerService;
import com.rendevous.intention.trick.spring.util.DateUtil;
import com.rendevous.intention.trick.spring.util.EarthToSunConstants;
import com.rendevous.intention.trick.spring.validator.RegisterCustomerValidator;
 
@Controller
public class RegisterCustomerController{
	
	 static final Md5PasswordEncoder md5PasswordEncoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
	 
	 @Autowired 
	    private EmailServiceCalls emailServiceCalls;
	 
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;

	 @Autowired
	    private RegisterCustomerService registerCustomerService;
 
	 RegisterCustomerValidator registerCustomerValidator;
 
	@Autowired
	public RegisterCustomerController(RegisterCustomerValidator registerCustomerValidator){
		this.registerCustomerValidator = registerCustomerValidator;
	}
	
	
	
	@RequestMapping(value="/RegisterCustomer.htm", method = RequestMethod.GET)
	public String registerCustomerGet(Model model, SessionStatus status) {
		
		AccountManager accountManager = new AccountManager();
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		
		
		return "RegisterCustomer";
	}
	
	
	
	@RequestMapping(value="/RegisterCustomerSubmit.htm")
	public Object registerCustomerPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors ) {
		
		if(accountManager.getAccountApprovalCode()==null || accountManager.getAccountApprovalCode().trim().length()==0)
		{	
			errors.reject("required.approvalCode");			
			return "RegisterCustomer";
		}
		
		
		if(!accountManager.getAccountApprovalCode().trim().equalsIgnoreCase("ASDFGHJKL"))
		{
			errors.reject("invalid.approvalCode");
			return "RegisterCustomer";
		}
		
		System.out.println("request.getContextPath()----> "+request.getContextPath());
		
		
		//form success
		return new RedirectView("/RegistrationBusinessApprovalRedirect.htm", true);
		//return "forward:/RegistrationBusinessApprovalRedirect.htm";		
	}
	
	
	@RequestMapping(value="/RegistrationBusinessApprovalRedirect.htm", method = RequestMethod.GET)
	public String RegistrationBusinessApprovalRedirect(Model model) {
		
		Customers cust = new Customers();		
		model.addAttribute("REGISTER_CUSTOMER", cust);
		
		return "RegisterBusinessCustomer";	
	}

	
	
	
	
	
	@RequestMapping(value="/RegisterUserCustomer.htm", method = RequestMethod.GET)
	public String registerUserCustomerGet(@ModelAttribute("REGISTER_CUSTOMER") Customers customer, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		Customers cust = new Customers();
		//command object
		model.addAttribute("REGISTER_CUSTOMER", cust);
		
		return "RegisterUserCustomer";
	}
	
	
 
	@RequestMapping(value="/RegisterUserCustomerSubmit.htm", method = RequestMethod.POST)
	public Object processUserRegistrationSubmit(@ModelAttribute("REGISTER_CUSTOMER") Customers customer, BindingResult result, HttpServletRequest request, SessionStatus status, Model model,Errors errors,Locale locale) {
 
		registerCustomerValidator.validate(customer, result);
 
		if (result.hasErrors()) {
			//if validator failed
			return "RegisterUserCustomer";
		} else {
			
			
			Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID(customer.getEmailAddress().trim(), customer.getUserId().trim());
			
			if(customerFromDB!=null && customerFromDB.getUserId().trim().equalsIgnoreCase(customer.getUserId().trim()))
			{
				errors.rejectValue("userId", "taken.userID");
				return "RegisterUserCustomer";
			}
			
			if(customerFromDB!=null && customerFromDB.getEmailAddress().trim().equalsIgnoreCase(customer.getEmailAddress().trim()))
			{
				errors.rejectValue("emailAddress", "taken.emailAddress");
				return "RegisterUserCustomer";
			}
			
			
			customer.setRegistrationDate(DateUtil.getCurrentDateTime());
			
			//USER
			customer.setCustomerType("USER");
			customer.setUserRoles(4);//non-active user, after validating the account we will set it to 3
				
			
			//ENCODE PASSWORD HERE
			String md5EncodedPassword = md5PasswordEncoder.encodePassword(customer.getUserPassword(), null);
			System.out.println("registration encoded password----> " + md5EncodedPassword);
			customer.setUserPassword(md5EncodedPassword);
			
			//BSE 64 ENCODE PASSWORD HERE
			String base64EncodedActivationCode = new String(Base64.encodeBase64((customer.getUserId()+"##"+customer.getEmailAddress()+"##"+new java.util.Date().getTime()).getBytes()));
			System.out.println("registration base 64 encoded activation code----> " + base64EncodedActivationCode);
			customer.setActivationCode(base64EncodedActivationCode);
			
			
			registerCustomerService.addCustomer(customer);
			
			//SEND EMAIL NOW
			
			Context ctx = new Context();
	        ctx.setVariable("name", "Welcome, "+customer.getFirstName()+" "+customer.getLastName()+"!");
	        ctx.setVariable("userID", customer.getUserId());
	        ctx.setVariable("verificationLink", EarthToSunConstants.SITE_DOMAIN_ADDRESS+request.getContextPath()+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);
	        //ctx.setVariable("verificationLink", "http://cherrysportsbids.com"+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);

	        try {
	        	emailServiceCalls.cutomerRegistration(customer.getEmailAddress(), ctx, locale);
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
			
			
			//form success
			//return "redirect:RegistrationSuccessRedirect.htm";
			return new RedirectView("/RegistrationSuccessRedirect.htm", true);
		}
	}
	
	
	
	
	@RequestMapping(value="/RegistrationSuccessRedirect.htm", method = RequestMethod.GET)
	public String processSubmitRedirect(@ModelAttribute("REGISTER_CUSTOMER") Customers customer, BindingResult result, SessionStatus status,Model model,Errors errors) {
		
		return "RegistrationSuccess";	
	}
	
	
	

	
	
	@RequestMapping(value="/RegisterBusinessCustomer.htm", method = RequestMethod.GET)
	public String registerBusinessCustomerGet(@ModelAttribute("REGISTER_CUSTOMER") Customers customer, BindingResult result, SessionStatus status, HttpServletRequest request, Model model,Errors errors) {
		
		Customers cust = new Customers();
		//command object
		model.addAttribute("REGISTER_CUSTOMER", cust);
		
		return "RegisterBusinessCustomer";
	}
	
	
 
	@RequestMapping(value="/RegisterBusinessCustomerSubmit.htm", method = RequestMethod.POST)
	public Object processBusinessRegistrationSubmit(@ModelAttribute("REGISTER_CUSTOMER") Customers customer, BindingResult result, SessionStatus status, HttpServletRequest request, Model model,Errors errors,Locale locale) {
 
		registerCustomerValidator.validate(customer, result);
 
		if (result.hasErrors()) {
			//if validator failed
			return "RegisterBusinessCustomer";
		} else {
			
			
			Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID(customer.getEmailAddress().trim(), customer.getUserId().trim());
			
			if(customerFromDB!=null && customerFromDB.getUserId().trim().equalsIgnoreCase(customer.getUserId().trim()))
			{
				errors.rejectValue("userId", "taken.userID");
				return "RegisterBusinessCustomer";
			}
			
			if(customerFromDB!=null && customerFromDB.getEmailAddress().trim().equalsIgnoreCase(customer.getEmailAddress().trim()))
			{
				errors.rejectValue("emailAddress", "taken.emailAddress");
				return "RegisterBusinessCustomer";
			}
			
			
			if(customer!=null && (customer.getBusinessName()==null || customer.getBusinessName().trim().length()==0) )
			{
				errors.rejectValue("businessName", "required.businessName");
				return "RegisterBusinessCustomer";
			}
			
			
			
			customer.setRegistrationDate(DateUtil.getCurrentDateTime());
			
			
			customer.setCustomerType("BUSINESS");
			customer.setUserRoles(4);//non-active user, after validating the account we will set it to 2
			
			
			//ENCODE PASSWORD HERE
			String md5EncodedPassword = md5PasswordEncoder.encodePassword(customer.getUserPassword(), null);
			System.out.println("registration encoded password----> " + md5EncodedPassword);
			customer.setUserPassword(md5EncodedPassword);
			
			//BSE 64 ENCODE PASSWORD HERE
			String base64EncodedActivationCode = new String(Base64.encodeBase64((customer.getUserId()+"##"+customer.getEmailAddress()+"##"+new java.util.Date().getTime()).getBytes()));
			System.out.println("registration base 64 encoded activation code----> " + base64EncodedActivationCode);
			customer.setActivationCode(base64EncodedActivationCode);
			
			
			registerCustomerService.addCustomer(customer);
			
			//SEND EMAIL NOW
			
			Context ctx = new Context();
	        ctx.setVariable("name", "Welcome, "+customer.getFirstName()+" "+customer.getLastName()+"!");
	        ctx.setVariable("userID", customer.getUserId());
	        ctx.setVariable("verificationLink", EarthToSunConstants.SITE_DOMAIN_ADDRESS+request.getContextPath()+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);
	        //ctx.setVariable("verificationLink", "http://cherrysportsbids.com"+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);
			
	        try {
	        	
	        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
	        	
	        	
	        	emailServiceCalls.cutomerRegistration(customer.getEmailAddress(), ctx, locale);
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
			
			//form success
			//return "redirect:RegistrationSuccessRedirect.htm";
	        return new RedirectView("/RegistrationSuccessRedirect.htm", true);
		}
	}
	
	
	
	
	@RequestMapping(value="/RegisterVerificationLink.htm/{accountActivationCode}", method = RequestMethod.GET)
	public String registrationAccountVerificationGet(@PathVariable String accountActivationCode, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Locale locale ) {
		
		if(accountActivationCode==null || accountActivationCode.trim().length()==0)
		{
			return "index";
		}
		
		
		String base64DecodedActivationCode = new String(Base64.decodeBase64(accountActivationCode.getBytes()));
		
		System.out.println("--------------------> base64DecodedActivationCode -------> "+base64DecodedActivationCode);
		
		if(base64DecodedActivationCode!=null && base64DecodedActivationCode.trim().length()>0)
		{
			String[] userIDAndEmailIDArray = base64DecodedActivationCode.split("##");
			
			//IF NOT VALID ACCOUNT ACTIVATION CODE
			if(userIDAndEmailIDArray==null || userIDAndEmailIDArray.length<2)
			{
				
				return "RegistrationActivationFailed";
			}
			
			
			//System.out.println("userIDAndEmailIDArray[1].trim() "+userIDAndEmailIDArray[1].trim());
			//System.out.println("userIDAndEmailIDArray[0].trim() "+userIDAndEmailIDArray[0].trim());
			
			Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID(userIDAndEmailIDArray[1].trim(), userIDAndEmailIDArray[0].trim());
			
			//IF NOT VALID ACCOUNT ACTIVATION CODE
			if(customerFromDB==null)
			{
				//System.out.println("customerFromDB==null");
				return "RegistrationActivationFailed";
			}
			
			//System.out.println("customerFromDB.getUserRoles().toString().trim() "+customerFromDB.getUserRoles().toString().trim());
			
			if(customerFromDB!=null && customerFromDB.getUserRoles()!=null && !customerFromDB.getUserRoles().toString().trim().equalsIgnoreCase("4"))
			{
				//System.out.println("customerFromDB.getUserRoles().toString()!=4--------------------->");
				return "RegistrationActivationFailed";
			}
			
			
			if(customerFromDB!=null && customerFromDB.getCustomerType()!=null && customerFromDB.getCustomerType().equalsIgnoreCase("USER"))
			{//USER ROLE
				customerFromDB.setUserRoles(3);
			}
			else
			{//BUSINESS USER ROLE
				customerFromDB.setUserRoles(2);
			}

			//UPDATE DATABASE
			registerCustomerService.updateCustomer(customerFromDB);
			
			
			
			
           //SEND EMAIL NOW
			
			Context ctx = new Context();
	        ctx.setVariable("name", "Welcome, "+customerFromDB.getFirstName()+" "+customerFromDB.getLastName()+"!");	        
	        //ctx.setVariable("verificationLink", "http://localhost:8080"+request.getContextPath()+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);
	        //ctx.setVariable("verificationLink", "http://cherrysportsbids.com"+"/RegisterVerificationLink.htm/"+base64EncodedActivationCode);
			
	        try {
	        	
	        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
	        	
	        	
	        	emailServiceCalls.cutomerRegistrationActivationSuccssful(customerFromDB.getEmailAddress(), ctx, locale);
	        }
	        catch(Exception ex)
	        {
	        	ex.printStackTrace();
	        }
			
			
		}
		
		return "RegistrationActivationSuccess";
	}

	
	
	
 
 
	/*@RequestMapping(method = RequestMethod.GET)
	public String initForm(ModelMap model){
 
		Customers cust = new Customers();
				//initilize a hidden value
		//cust.setSecretValue("I'm hidden value");
 
		//command object
		model.addAttribute("REGISTER_CUSTOMER", cust);
 
		//return form view
		return "RegisterUserCustomer";
	}*/
 
 
	/*@ModelAttribute("salutationList")
	public List<String> populateWebFrameworkList() {
 
		//Data referencing for web framework checkboxes
		List<String> salutationList = new ArrayList<String>();
		salutationList.add("Mr");
		salutationList.add("Ms");
		salutationList.add("Mrs");
		salutationList.add("Miss");
		
 
		return salutationList;
	}
 
*/	/*@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
 
	}*/	
	
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	
}