package com.rendevous.intention.trick.spring.util;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;
import net.authorize.data.creditcard.CreditCard;

import com.rendevous.intention.trick.spring.model.CustomerPaymentInformation;


public class PaymentCardUtil {
	
	private  static final String apiLoginID = "8RhP4kMd3dQ5";
	private static final String transactionKey = "4e8775Bj4vmQH4f3";
	
	public static String returnPaymentCardLast4Digits(CustomerPaymentInformation customerPaymentInformation)
	{
		String finalPaymentCardDetails=null;
		
		if(customerPaymentInformation!=null)
		{
			//GET THE LAST 4 DIGITS
			finalPaymentCardDetails = customerPaymentInformation.getCardNumber().substring(customerPaymentInformation.getCardNumber().length()-5, customerPaymentInformation.getCardNumber().length()-1);
			return finalPaymentCardDetails;
			
		}
		
		return finalPaymentCardDetails;		
	}
	
	
	
	public static Result<Transaction> chargeCustomerPaymentCard(CustomerPaymentInformation customerPaymentInformation, String winningBidValue)
	{
		
		Merchant merchant = Merchant.createMerchant(Environment.SANDBOX,apiLoginID, transactionKey);

				//create credit card
				CreditCard creditCard = CreditCard.createCreditCard();
				creditCard.setCreditCardNumber(customerPaymentInformation.getCardNumber());
				creditCard.setExpirationMonth(customerPaymentInformation.getCardExpirationMonth());
				creditCard.setExpirationYear(customerPaymentInformation.getCardExpirationYear());

				// create transaction
				Transaction authCaptureTransaction = merchant.createAIMTransaction(TransactionType.AUTH_CAPTURE, new BigDecimal(winningBidValue));
				authCaptureTransaction.setCreditCard(creditCard);

				Result<Transaction> result = (Result<Transaction>)merchant.postTransaction(authCaptureTransaction);

				if(result.isApproved()) {
					System.out.println("Approved!</br>");
					System.out.println("Transaction Id: " + result.getTarget().getTransactionId());
				} else if (result.isDeclined()) {
					System.out.println("Declined.</br>");
					System.out.println(result.getReasonResponseCode() + " : " + result.getResponseText());
				} else {
					System.out.println("Error.</br>");
					System.out.println(result.getReasonResponseCode() + " : " + result.getResponseText());
				}
				
				return result;
	}

	

}
