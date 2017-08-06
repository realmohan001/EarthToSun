/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.rendevous.intention.trick.spring.scheduledtasks;

import java.util.List;
import java.util.Locale;

import net.authorize.aim.Result;
import net.authorize.aim.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.rendevous.intention.trick.spring.email.EmailServiceCalls;
import com.rendevous.intention.trick.spring.model.CustomerAddress;
import com.rendevous.intention.trick.spring.model.CustomerBids;
import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;
import com.rendevous.intention.trick.spring.model.Customers;
import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.service.BidsManagerService;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.service.RegisterCustomerService;
import com.rendevous.intention.trick.spring.util.AddressUtil;
import com.rendevous.intention.trick.spring.util.DateUtil;
import com.rendevous.intention.trick.spring.util.PaymentCardUtil;

@Service
public class ScheduledTasksService {
	
	@Autowired
    private ItemManagerService itemManagerService;
	
	 @Autowired 
	 private EmailServiceCalls emailServiceCalls;
	 
	 @Autowired
	 private RegisterCustomerService registerCustomerService;
	 
	
	@Autowired
	private BidsManagerService bidsManagerService;
	
	@Async
	 public void lookOutForItemBidFinished(Locale locale) {
	  String threadName = Thread.currentThread().getName();	 
	        
	        	
	        	System.out.println("---------------------> START INSIDE ASYNC lookOutForItemBidFinished -------------------> ");
	        	
	      	try{
	        	
	        	//THIS WILL BRING ALL THE ITEMS, WHCIH RECENTLY ENDED THE BIDDING
	        	//WE NEED TO GET THE ITEM, SEND E-MAIL TO THE WINNING CUSTOMER, ABOUT THE SUCCESSFUL BID AND TALK ABOUT THE PAYMENT DETAILS
	        	List<Items> allItemsList = itemManagerService.getItemsByBidClosed(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"),"100");
	        	
	        	System.out.println("allItemsList------> "+allItemsList.size());
	        	
	        	if(allItemsList!=null && allItemsList.size()>0)
	        	{
	        		for (Items item : allItemsList)
	        		{
	        			System.out.println("allItemsList---item.getItemId()---> "+item.getItemId());
	        			
	        			CustomerBids winningBid = bidsManagerService.getWinningBidOfItem(item.getItemId());
	        			
	        			
	        			if(winningBid!=null)
	        			{
	        				Customers customer = registerCustomerService.getCustomer(winningBid.getCustomers().getCustomerId());
	        			     				
	        				//SEND EMAIL NOW
	        				
	        				Context ctx = new Context();
	        		        ctx.setVariable("customerName", "Hello, "+customer.getFirstName()+" "+customer.getLastName()+"!");	        
	        		        ctx.setVariable("itemName", item.getItemName());
	        		        ctx.setVariable("winningBidValue", winningBid.getBidPrice());

	        		        try {
	        		        	emailServiceCalls.lookOutForItemBidFinished(customer.getEmailAddress(), ctx, locale);
	        		        }
	        		        catch(Exception ex)
	        		        {
	        		        	ex.printStackTrace();
	        		        }
	        				
	        				//UPDATE THE BID INFORMATION
	        				winningBid.setWinnginBid("TRUE");
	        				bidsManagerService.updateBid(winningBid);
	        				
	        				//UPDATE ITEM
	        				item.setItemStatusCode("200");
	        				item.setItemStatusReason("CLOSED FOR BIDDING. CUSTOMER EMAILED!");//MAXIMUM 120 CHARACTERS
	        				
	        				System.out.println("item getItemId---> "+item.getItemId().toString());
	        				System.out.println("item getItemName---> "+item.getItemName());
	        				System.out.println("item getItemCurrentBidPrice---> "+item.getItemCurrentBidPrice());
	        				
	        				System.out.println("itemManagerService------> "+itemManagerService);
	        				
	        			}
	        			else
	        			{//NO BIDS PLACED..BIDING TIME ENDED....SETTING STATUS TO '150'
	        				//UPDATE ITEM
	        				item.setItemStatusCode("150");
	        				item.setItemStatusReason("CLOSED FOR BIDDING. NO BIDS PLACED");//MAXIMUM 120 CHARACTERS
	        			}
	        			
	        			itemManagerService.updateItem(item);
	        			
	        			
	        		}
	        	}
	        	
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
	        	
	        	System.out.println("---------------------> END INSIDE ASYNC lookOutForItemBidFinished -------------------> ");
	                
	 }
	
	
	
	@Async
	 public void sendBillingAndShippingInfoEmail(Locale locale) {
	  String threadName = Thread.currentThread().getName();	 
	        
	        	
	        	System.out.println("---------------------> START INSIDE ASYNC sendBillingAndShippingInfoEmail -------------------> ");
	        	
	        try{
	        	
	        	//THIS WILL BRING ALL THE ITEMS, WHCIH RECENTLY ENDED AND CUSTOMER GOT REPORTED ABOUT WINNING THE BID AND WE HAVE TO SEND THE FOLLOWUP EMAIL ABOUT THE BILLIGN AND SHIPPING INFORMATION	        	
	        	List<Items> allItemsList = itemManagerService.getItemsByBidClosed(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"),"200");
	        	
	        	if(allItemsList!=null && allItemsList.size()>0)
	        	{
	        		for (Items item : allItemsList)
	        		{
	        			CustomerBids winningBid = bidsManagerService.getWinningBidOfItem(item.getItemId());
	        			
	        			
	        			if(winningBid!=null)
	        			{
	        				
	        				Customers customer = registerCustomerService.getCustomer(winningBid.getCustomers().getCustomerId());
	        				
	        				//CHARGE THE CUSTOMER NOW.........
	        				
	        				boolean paymentOperationSuccessful=false;
	        				Result<Transaction> paymentChargeResult=null;
	        				if(customer.getCustomerPaymentInformations()!=null)
		        		        {
		        		        	for(CustomerPaymentInformation customerPaymentInformation : customer.getCustomerPaymentInformations())
		        					{
		        		        		if(customerPaymentInformation!=null)
		        		        		{
		        		        			
		        		        			try
		        		        			{
		        		        				paymentChargeResult = PaymentCardUtil.chargeCustomerPaymentCard(customerPaymentInformation, winningBid.getBidPrice());
		        		        			}
		        		        			catch(Exception ex)
		        		        			{//IF PAYMENT OPERATION RETURED EXCEPTION, IGNORE THAT ITEM, WE WILL BILL IT IN NEXT ITERATION, NO E-MAILS TO THE CUSTOMER  
		        		        				continue;
		        		        			}
		        		        			
		        		        			if(paymentChargeResult!=null)
		        		        			{
		        		        				if(paymentChargeResult.isApproved()) {
		        		        					paymentOperationSuccessful=true;
		        		        					System.out.println("Approved!");
		        		        					System.out.println("Approved Transaction Id: " + paymentChargeResult.getTarget().getTransactionId());
		        		        				} else if (paymentChargeResult.isDeclined()) {
		        		        					paymentOperationSuccessful=false;
		        		        					System.out.println("Declined.");
		        		        					System.out.println("Declined Transaction Id: " + paymentChargeResult.getTarget().getTransactionId());
		        		        					System.out.println(paymentChargeResult.getReasonResponseCode() + " : " + paymentChargeResult.getResponseText());
		        		        				} else {
		        		        					paymentOperationSuccessful=false;
		        		        					System.out.println("Error.");
		        		        					System.out.println(paymentChargeResult.getReasonResponseCode() + " : " + paymentChargeResult.getResponseText());
		        		        				}
		        		        			}
		        		        		}
		        		        	}
		        		        }
	        				
	        				//SEND EMAIL NOW
	        				
	        				Context ctx = new Context();
	        		        ctx.setVariable("customerName", "Hello, "+customer.getFirstName()+" "+customer.getLastName()+"!");	        
	        		        ctx.setVariable("itemName", item.getItemName());
	        		        ctx.setVariable("winningBidValue", winningBid.getBidPrice());
	        		        ctx.setVariable("billingTransactionId", paymentChargeResult.getTarget().getTransactionId());
	        		        
	        		        
	        		        if(customer.getCustomerAddresses()!=null)
	        		        {
	        		        	for(CustomerAddress customerAddress : customer.getCustomerAddresses())
	        					{
	        		        		if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().equalsIgnoreCase("BILLING"))
	        		        		{
	        		        			ctx.setVariable("billingAddress", AddressUtil.returnAddressAsString(customerAddress));
	        		        		}
	        		        		
	        		        		if(customerAddress!=null && customerAddress.getAddressType()!=null && customerAddress.getAddressType().equalsIgnoreCase("SHIPPING"))
	        		        		{
	        		        			ctx.setVariable("shippingAddress", AddressUtil.returnAddressAsString(customerAddress));
	        		        		}
	        		        	}
	        		        }
	        		        
	        		        
	        		        if(customer.getCustomerPaymentInformations()!=null)
	        		        {
	        		        	for(CustomerPaymentInformation customerPaymentInformation : customer.getCustomerPaymentInformations())
	        					{
	        		        		if(customerPaymentInformation!=null)
	        		        		{
	        		        			ctx.setVariable("paymentCardDetails", PaymentCardUtil.returnPaymentCardLast4Digits(customerPaymentInformation));
	        		        		}
	        		        	}
	        		        }
	        		        
	        		        //IF CHARGE IS NOT SUCCESSFUL
	        		        if(!paymentOperationSuccessful)
	        		        {
	        		        	ctx.setVariable("chargeResult", paymentChargeResult.getResponseText());
	        		        }
	        		        
	        		        
	        		        if(paymentOperationSuccessful)
	        		        {//IF CHARGE TRANSACTION IS SUCCESSFUL
		        		        try {
		        		        	emailServiceCalls.sendBillingAndShippingInfoEmail(customer.getEmailAddress(), ctx, locale);
		        		        }
		        		        catch(Exception ex)
		        		        {
		        		        	ex.printStackTrace();
		        		        }	        				
	        		        }
	        		        
	        		        
	        		        if(!paymentOperationSuccessful)
	        		        {//IF CHARGE TRANSACTION IS FAILURE
		        		        try {
		        		        	emailServiceCalls.sendBillingAndShippingInfoEmailFailedTransaction(customer.getEmailAddress(), ctx, locale);
		        		        }
		        		        catch(Exception ex)
		        		        {
		        		        	ex.printStackTrace();
		        		        }	        				
	        		        }
	        		        
	        		        
	        		        if(paymentOperationSuccessful)
	        		        {//payment success case
	        		        	//UPDATE ITEM
	        		        	item.setItemStatusCode("300");	        				
	        		        	item.setItemStatusReason("CLOSED FOR BIDDING. PAYMENT TRANSACTION SUCCESSFUL. CUSTOMER EMAILED WITH BILLING AND SHIPPING INFORMATION!"); //MAXIMUM 120 CHARACTERS
	        		        }
	        		        else
	        		        {//payment failed case
	        		        	item.setItemStatusCode("250");	        				
	        		        	item.setItemStatusReason("CLOSED FOR BIDDING. PAYMENT TRANSACTION FAILED. CUSTOMER EMAILED WITH BILLING AND SHIPPING INFORMATION!"); //MAXIMUM 120 CHARACTERS
	        		        }
	        				
	        				
	        				itemManagerService.updateItem(item);
	        			}
	        			
	        		}
	        	}
	        	
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}	        	
	        	
	        	System.out.println("---------------------> END INSIDE ASYNC sendBillingAndShippingInfoEmail -------------------> ");
	                
	 }

		

}
