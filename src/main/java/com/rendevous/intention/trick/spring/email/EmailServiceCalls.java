package com.rendevous.intention.trick.spring.email;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import com.rendevous.intention.trick.spring.util.EarthToSunConstants;

@Component
public class EmailServiceCalls {
	
    @Autowired 
    private EmailService emailService;

	
    @Async
	public void cutomerRegistration(String emailAddress, Context ctx, Locale locale) throws Exception
	{
    	System.out.println("INSIDE CUSTOMER REGISTRATION ASYNCHRONOUS CALL--------------->");
    	
		try {
			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Verify your Registration to "+EarthToSunConstants.WEBSITE_NAME, EarthToSunConstants.SENDER_EMAIL_ADDRESS, "register-success-email-simple.html", ctx, locale);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			System.out.println("INSIDE CUSTOMER REGISTRATION ASYNCHRONOUS CALL MESSAGING EXCEPTION--------------->");
			e.printStackTrace();
			throw e;
		}		
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("INSIDE CUSTOMER REGISTRATION ASYNCHRONOUS CALL EXCEPTION--------------->");
			e.printStackTrace();
			throw e;
		}
	}
    
    
    @Async
  	public void cutomerRegistrationActivationSuccssful(String emailAddress, Context ctx, Locale locale) throws Exception
  	{
      	System.out.println("INSIDE CUSTOMER REGISTRATION ACCOUNT ACTIVAITON SUCCESSFUL ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: ACCOUNT ACTIVATION SUCCESSFUL!", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "register-activation-success-email-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER REGISTRATION ACCOUNT ACTIVAITON SUCCESSFUL ASYNCHRONOUS CALL MESSAGING EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER REGISTRATION ACCOUNT ACTIVAITON SUCCESSFUL ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
      
    
    
    
    
    @Async
  	public void cutomerForgotUsername(String emailAddress, Context ctx, Locale locale) throws Exception
  	{
      	System.out.println("INSIDE CUSTOMER cutomerForgotUsername ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Your registered username", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "forgot-username-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotUsername ASYNCHRONOUS CALL MESSAGING EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotUsername ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
    
    
    @Async
  	public void cutomerForgotPasswordResetSuccessful(String emailAddress, Context ctx, Locale locale) throws Exception
  	{
      	System.out.println("INSIDE CUSTOMER cutomerForgotPasswordResetSuccessful ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "NOTICE : PASSWORD RESET SUCCESSFUL!", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "forgot-password-reset-successful-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotPasswordResetSuccessful ASYNCHRONOUS CALL MESSAGING EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotPasswordResetSuccessful ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
    
    
    
    
    @Async
  	public void cutomerForgotPassword(String emailAddress, Context ctx, Locale locale) throws Exception
  	{
      	System.out.println("INSIDE CUSTOMER cutomerForgotPassword ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Instructions to reset password", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "forgot-password-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotPassword ASYNCHRONOUS CALL MESSAGING EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerForgotPassword ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}

    
    @Async
  	public void cutomerAddressChange(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerAddressChange ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Address Changed", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "address-change-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerAddressChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerAddressChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
    
    @Async
  	public void cutomerPaymentChange(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerPaymentChange ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Payment Information Changed", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "payment-change-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPaymentChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPaymentChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}

  	}
    
    @Async
  	public void cutomerUserIDChange(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerUserIDChange ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: User ID Changed", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "userid-change-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerUserIDChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerUserIDChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}

    
    @Async
  	public void cutomerPasswordChange(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerPasswordChange ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Password Changed", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "password-change-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPasswordChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPasswordChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}

    
    @Async
  	public void cutomerEmailChange(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerEmailChange ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: E-mail Address Changed", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "email-change-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerEmailChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerEmailChange ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
        
    @Async
  	public void cutomerPaymentAndAddressAdded(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE CUSTOMER cutomerPaymentAndAddressAdded ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "ACTION REQUIRED: Payment and Address Information Added to Account", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "payment-address-added-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPaymentAndAddressAdded ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER cutomerPaymentAndAddressAdded ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
    
    @Async
  	public void lookOutForItemBidFinished(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE ITEM lookOutForItemBidFinished ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "NOTICE: CONGRATULATIONS, YOU GOT THE WINNING BID!", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "bid-won-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER lookOutForItemBidFinished ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER lookOutForItemBidFinished ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    
    
    
    @Async
  	public void sendBillingAndShippingInfoEmail(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE ITEM sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "NOTICE: CONGRATULATIONS, YOU GOT THE WINNING BID! PAYMENT CARD CHARGED SUCCESSFULLY!", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "bid-won-billing-shipping-success-transaction-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    

    @Async
  	public void sendBillingAndShippingInfoEmailFailedTransaction(String emailAddress, Context ctx, Locale locale) throws Exception
  	
  	{
      	System.out.println("INSIDE ITEM sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL--------------->");
      	
  		try {
  			emailService.sendSimpleMail(emailAddress, "NOTICE: PAYMENT IS UNSUCCESSFUL. PLEASE CONTACT THE CHERRY BIDS CUSTOMER SERVICE!", EarthToSunConstants.SENDER_EMAIL_ADDRESS, "bid-won-billing-shipping-failed-transaction-simple.html", ctx, locale);
  		} catch (MessagingException e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}		
  		catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("INSIDE CUSTOMER sendBillingAndShippingInfoEmail ASYNCHRONOUS CALL EXCEPTION--------------->");
  			e.printStackTrace();
  			throw e;
  		}
  	}
    

    
    
}
