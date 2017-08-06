package com.rendevous.intention.trick.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.context.Context;

import com.rendevous.intention.trick.spring.email.EmailServiceCalls;
import com.rendevous.intention.trick.spring.model.AccountManager;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.service.LoginCustomerService;
import com.rendevous.intention.trick.spring.service.RegisterCustomerService;
import com.rendevous.intention.trick.spring.util.CustomUserImplementation;
import com.rendevous.intention.trick.spring.util.DateUtil;
import com.rendevous.intention.trick.spring.util.EarthToSunConstants;
import com.rendevous.intention.trick.spring.validator.CustomValidatorUtil;
import com.rendevous.intention.trick.spring.validator.LoginCustomerValidator;
 
@Controller
public class LoginCustomerController{
	
	 @Autowired
	    private LoginCustomerService loginCustomerService;
	 
	 @Autowired 
	    private EmailServiceCalls emailServiceCalls;
	 
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;
 
	 LoginCustomerValidator loginCustomerValidator;
	 
	 @Autowired
	    private ItemManagerService itemManagerService;
	 
	 @Autowired
	    private RegisterCustomerService registerCustomerService;
	 
	 static final Md5PasswordEncoder md5PasswordEncoder = new org.springframework.security.authentication.encoding.Md5PasswordEncoder();
	 
 
	@Autowired
	public LoginCustomerController(LoginCustomerValidator loginCustomerValidator){
		this.loginCustomerValidator = loginCustomerValidator;
	}
 
/*	@RequestMapping(value="/LoginCustomer.htm", method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("LOGIN_CUSTOMER") LoginForm loginForm, BindingResult result, SessionStatus status, Model model,Errors errors) {
 
		loginCustomerValidator.validate(loginForm, result);
		ModelAndView modelAndView = new ModelAndView();
		
		
 
		if (result.hasErrors()) {
			//if validator failed
			modelAndView.setViewName("LoginCustomer");
			return modelAndView;
		} else {
			
			Customers customer = loginCustomerService.getCustomers(loginForm.getLoginUserID(), loginForm.getLoginPassword());
			if(customer==null)
			{
				errors.rejectValue("loginUserID", "invalid.loginUserID.OrPassword");
				modelAndView.setViewName("LoginCustomer");
				return modelAndView;
			}
			userSessionScopedData.setCustomer(customer);			
			modelAndView.setViewName("redirect:LoginSuccess");
			
			//GET CUSTOMER ITEMS
			
			List<Items> customerItemsList = itemManagerService.getItemsOfCustomer(customer.getCustomerId());
			userSessionScopedData.setCutomerItems(customerItemsList);
			
			//GET ALL ITEMS
			
			if(userSessionScopedData.getAllItems()!=null)
			{
			List<Items> allItemsList = itemManagerService.getAllItems();
			userSessionScopedData.setAllItems(allItemsList);
			}
			
			
			
			return modelAndView;
		}
	}
 
	@RequestMapping(value="/LoginCustomer.htm",method = RequestMethod.GET)
	public String initForm(ModelMap model){
 
		LoginForm loginForm = new LoginForm();
				//initilize a hidden value
		//cust.setSecretValue("I'm hidden value");
 
		//command object
		model.addAttribute("LOGIN_CUSTOMER", loginForm);
 
		//return form view
		return "LoginCustomer";
	}
	
	@RequestMapping(value="/LoginSuccess", method=RequestMethod.GET)
	public String successView()
	{ 
		return "LoginSuccess";
	}
 */
 
	 
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
 
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
 
	}
 
	
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String restartFlow() {
	    return "redirect:/LoginCustomer";
	}
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	
	
	@RequestMapping(value = "/LoginCustomer.htm", method = RequestMethod.GET)
	public String getLoginPage(@RequestParam(value="error", required=false) String error, 
			ModelMap model) {
		//logger.debug("Received request to show login page");

		// Add an error message to the model if login is unsuccessful
		// The 'error' parameter is set to true based on the when the authentication has failed. 
		// We declared this under the authentication-failure-url attribute inside the spring-security.xml
		/* See below:
		 <form-login 
				login-page="/krams/auth/login" 
				authentication-failure-url="/krams/auth/login?error=true" 
				default-target-url="/krams/main/common"/>
		 */
		
		if(error!=null)
		{
			if (error.equalsIgnoreCase("badCredentialsException")) {
				// Assign an error message
				model.put("error", "You have entered an invalid username or password!");			
			} 
			else if(error.equalsIgnoreCase("usernameNotFoundException"))
			{
				model.put("error", "Username not found!");
			}
			else if(error.equalsIgnoreCase("disabledException"))
			{//WHEN ACCOUNT IS NOT ENABLED
				model.put("error", "User account not activated. Follow instructions in the E-mail to activate your account!");
			}
			else if(error.equalsIgnoreCase("dataAccessException"))
			{
				model.put("error", "System error..Please check later!");
			}
			else if(error.equalsIgnoreCase("lockedException"))
			{
				model.put("error", "User account not activated. Follow instructions in the E-mail to activate your account!");
			}		
			else 
			{//DEFAULT ERROR IS INVALID USERNAME AND PASSWORD
				model.put("error", "You have entered an invalid username or password!");
			}			
		}
		else
		{
			model.put("error", "");
		}
		
		
		// This will resolve to /WEB-INF/jsp/loginpage.jsp
		return "LoginCustomer";
	}
	
	
	@RequestMapping(value = "/denied.htm", method = RequestMethod.GET)
 	public String getDeniedPage() {
		//logger.debug("Received request to show denied page");
		
		// This will resolve to /WEB-INF/jsp/deniedpage.jsp
		return "deniedpage";
	}
	
	@RequestMapping(value="/LoginSuccess", method=RequestMethod.GET)
	public String successView(HttpServletRequest request)
	{
		if( !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUserImplementation) )
		{//need to login first
			return "LoginCustomer";
		}
		
		CustomUserImplementation user = (CustomUserImplementation)SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		System.out.println("User in login success controller-------> ");
		
		//HttpSession session = request.getSession(true); //create a new session
		//Customers customer = loginCustomerService.getCustomers(user.getUsername(), user.getPassword());
		userSessionScopedData.setCustomer(user.getCustomer());
		
		if(userSessionScopedData==null || userSessionScopedData.getAllItems()==null || userSessionScopedData.getAllItems().size()==0)
		{//IT MEANS ALL ITEMS ARE NOT SET.GET THEM AND SET AGAIN,
			
			//List<Items> allItemsList = itemManagerService.getAllItems();
			List<Items> allItemsList = itemManagerService.getAllItemsByFinalBidDate(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"));
			userSessionScopedData.setAllItems(allItemsList);
		}
		
		
		//Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
		
		return "index";
	}
	
	
	@RequestMapping(value="/LoginForgotPassword.htm", method = RequestMethod.GET)
	public String loginForgotPasswordGet(Model model, SessionStatus status) {
		
		AccountManager accountManager = new AccountManager();
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		return "LoginForgotPassword";
	}
	
	
	
	@RequestMapping(value="/LoginForgotPassword.htm")
	public Object loginForgotPasswordPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors,Locale locale ) {
		
		if(accountManager.getForgotAccountCredUserId()==null || accountManager.getForgotAccountCredUserId().trim().length()==0)
		{	
			errors.reject("required.forgotAccountCredUserId");			
			return "LoginForgotPassword";
		}
		
		
		//CHECK IF THE USER IF FOUND OR NOT		
		Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID("",accountManager.getForgotAccountCredUserId().trim());
		
		if(customerFromDB==null)
		{
			errors.reject("notFound.forgotAccountCredUserId");
			return "LoginForgotPassword";
		}
		
		//BASE 64 ENCODE FOR ACTIVATION CODE
		String base64EncodedActivationCode = new String(Base64.encodeBase64((customerFromDB.getCustomerId()+"##"+customerFromDB.getUserId()+"##"+customerFromDB.getEmailAddress()).getBytes()));
		System.out.println("loginForgotPasswordPost base 64 encoded activation code----> " + base64EncodedActivationCode);
		
		//BASE 64 ENCODE FOR TEMPORARY PASSWORD
		String base64EncodedTempPassword = new String(Base64.encodeBase64((customerFromDB.getCustomerId()+"##"+customerFromDB.getUserId()).getBytes()));
		
		
		if(base64EncodedTempPassword!=null && base64EncodedTempPassword.length()>10)
		{
			base64EncodedTempPassword= base64EncodedTempPassword.substring(0, 9);
		}
		
		System.out.println("loginForgotPasswordPost base 64 encoded base64EncodedTempPassword----> " + base64EncodedTempPassword);
		
		//SET THE TEMPORARY PASSWORD
		customerFromDB.setTempPassword(base64EncodedTempPassword);		
		registerCustomerService.updateCustomer(customerFromDB);
				
		
		//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Welcome "+customerFromDB.getFirstName()+" "+customerFromDB.getLastName());	        
        ctx.setVariable("username", customerFromDB.getUserId());
        ctx.setVariable("passwordResetLink", EarthToSunConstants.SITE_DOMAIN_ADDRESS+request.getContextPath()+"/ResetPassword.htm/"+base64EncodedActivationCode);
        //ctx.setVariable("passwordResetLink", "http://cherrysportsbids.com"+"/ResetPassword.htm/"+base64EncodedActivationCode);
        ctx.setVariable("tempPassword", base64EncodedTempPassword);
        
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerForgotPassword(customerFromDB.getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
		
		//form success
		//return "redirect:LoginForgotPasswordEmailRedirect.htm";		
		return new RedirectView("/LoginForgotPasswordEmailRedirect.htm", true);
	}
	
	
	@RequestMapping(value="/LoginForgotPasswordEmailRedirect.htm", method = RequestMethod.GET)
	public String loginForgotPasswordRedirect(Model model) {
		
		return "LoginForgotPasswordSuccess";	
	}
	
	
	
	
	@RequestMapping(value="/LoginForgotUsername.htm", method = RequestMethod.GET)
	public String loginForgotUsernameGet(Model model, SessionStatus status) {
		
		AccountManager accountManager = new AccountManager();
		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		return "LoginForgotUsername";
	}
	
	
	
	@RequestMapping(value="/LoginForgotUsername.htm")
	public Object loginForgotUsernamePost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,HttpServletRequest request, HttpServletResponse response,Errors errors,Locale locale ) {
		
		if(accountManager.getForgotAccountCredEmailId()==null || accountManager.getForgotAccountCredEmailId().trim().length()==0)
		{	
			errors.reject("required.forgotAccountCredEmailId");			
			return "LoginForgotUsername";
		}
		
		
		//CHECK IF THE EMAIL FOUND OR NOT		
		Customers customerFromDB = registerCustomerService.checkCustomerWithEmailAndUserID(accountManager.getForgotAccountCredEmailId().trim(), "");
		
		if(customerFromDB==null)
		{
			errors.reject("notFound.forgotAccountCredEmailId");
			return "LoginForgotPassword";
		}
		
		
		//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Welcome "+customerFromDB.getFirstName()+" "+customerFromDB.getLastName());	        
        ctx.setVariable("username", customerFromDB.getUserId());
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerForgotUsername(customerFromDB.getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
		
		
		//form success
		//return "redirect:LoginForgotUsernameEmailRedirect.htm";        
        return new RedirectView("/LoginForgotUsernameEmailRedirect.htm", true);
	}
	
	
	@RequestMapping(value="/LoginForgotUsernameEmailRedirect.htm", method = RequestMethod.GET)
	public String loginForgotUsernameRedirect(Model model) {
		
		return "LoginForgotUsernameSuccess";	
	}
	
	
	
	
	@RequestMapping(value="/ResetPassword.htm/{resetPasswordCode}", method = RequestMethod.GET)
	public String ResetPasswordGet(@PathVariable String resetPasswordCode, SessionStatus status, HttpServletRequest request, Model model) {
		
		
		if(resetPasswordCode==null || resetPasswordCode.trim().length()==0)
		{
			return null;
		}
		
		AccountManager accountManager = new AccountManager();		
		model.addAttribute("ACCOUNT_MANAGER", accountManager);
		
		
		String base64DecodedActivationCode = new String(Base64.decodeBase64(resetPasswordCode.getBytes()));
		
		System.out.println("--------------------> base64DecodedActivationCode -------> "+base64DecodedActivationCode);
		
		if(base64DecodedActivationCode!=null && base64DecodedActivationCode.trim().length()>0)
		{
			String[] userIDAndEmailIDArray = base64DecodedActivationCode.split("##");
			//Customers customerFromDB = registerCustomerService.getCustomer(new Integer(userIDAndEmailIDArray[0].trim()));
			
			
			//SET THE CUSTOMER ID TO USE IT IN TEH PASSWORD RESET POST, BELOW
			userSessionScopedData.setCustomerID(userIDAndEmailIDArray[0].trim());
			
		}
		
		
		return "ResetPasswordDetails";
	}
	
	
	
	@RequestMapping(value="/ResetPasswordSubmit.htm", method = RequestMethod.POST)
	public Object ResetPasswordSubmitPost(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, Model model, SessionStatus status,  HttpServletRequest request, HttpServletResponse response,Errors errors,Locale locale) {		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resetPasswordTemp", "required.account.resetPasswordTemp", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resetPasswordNew", "required.account.resetPasswordNew", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resetPasswordNewConfirm", "required.account.resetPasswordNewConfirm", "Field name is required.");
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "ResetPasswordDetails";
		}
		
		
		Customers customerFromDB = registerCustomerService.getCustomer(new Integer(userSessionScopedData.getCustomerID().trim()));
		
		if(customerFromDB!=null && customerFromDB.getTempPassword()!=null && !customerFromDB.getTempPassword().equals(accountManager.getResetPasswordTemp()))
		{
			errors.rejectValue("currentUserPassword", "invalid.reset.account.tempUserPassword");
		}
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "ResetPasswordDetails";
		}
		
		
		//CHECK FOR MATCH OF PASSWORD AND CONFIRMPASSWORD
		if(!accountManager.getResetPasswordNew().trim().equals(accountManager.getResetPasswordNewConfirm()))
		{
			errors.rejectValue("resetPasswordNewConfirm", "invalid.reset.account.password.mismatch");
		}
		
		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "ResetPasswordDetails";
		}
		
		
		//PASSWORD PATTERN CHECK
		String  passwordResult = CustomValidatorUtil.passwordPatternCheck(accountManager.getResetPasswordNew());
		
		if(passwordResult!=null)
		{
			errors.rejectValue("resetPasswordNew", "invalid.reset.account.password.pattern");
		}
		

		if(errors!=null && errors.hasErrors())
		{//IF THER ARE ERRORS
			return "ResetPasswordDetails";
		}
		
		
		//SET THE PASSWORD TO ACCOUNT HERE
		
		if(customerFromDB!=null)
		{
			String md5EncodedNewPassword = md5PasswordEncoder.encodePassword(accountManager.getResetPasswordNew(), null);
			customerFromDB.setUserPassword(md5EncodedNewPassword);
			registerCustomerService.updateCustomer(customerFromDB);
		}
		
		
	//SEND EMAIL NOW
		
		Context ctx = new Context();
        ctx.setVariable("name", "Hello, "+customerFromDB.getFirstName()+" "+customerFromDB.getLastName()+"!");	        
        //ctx.setVariable("username", customerFromDB.getUserId());
		
        try {        	
        	System.out.println("emailServiceCalls is ----> "+ emailServiceCalls);
        	emailServiceCalls.cutomerForgotPasswordResetSuccessful(customerFromDB.getEmailAddress(), ctx, locale);
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
        }
		
		
		
		
		//return "redirect:ResetPasswordSuccessRedirect.htm";
		return new RedirectView("/ResetPasswordSuccessRedirect.htm", true);
		
	}
	
	
	@RequestMapping(value="/ResetPasswordSuccessRedirect.htm", method = RequestMethod.GET)
	public String processResetPasswordSuccessRedirect(@ModelAttribute("ACCOUNT_MANAGER") AccountManager accountManager, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		return "ResetPasswordDetailsSuccess";	
	}
	
	
	
	
	
	
	
}