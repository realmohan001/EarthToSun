package com.rendevous.intention.trick.spring.model;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
	
	private String newUserId=null;
	private String currentUserPassword=null;
	private String newUserPassword=null;
	private String confirmNewUserPassword=null;
	private String newEmailAddress=null;
	private CustomerAddress customerBillingAddress=null;
	private CustomerAddress customerShippingAddress=null;	
	private CustomerPaymentInformation customerPaymentInformation=null;
	private boolean shippingSameAsBillingAddress=false;
	private String accountApprovalCode=null;
	private String forgotAccountCredUserId=null;
	private String forgotAccountCredEmailId=null;
	private String resetPasswordTemp=null;
	private String resetPasswordNew=null;
	private String resetPasswordNewConfirm=null;
	
	
	
	
	public AccountManager(String newUserId, String currentUserPassword,
			String newUserPassword, String confirmNewUserPassword,
			String newEmailAddress, CustomerAddress customerBillingAddress, CustomerAddress customerShippingAddress, CustomerPaymentInformation customerPaymentInformation, boolean shippingSameAsBillingAddress ,
			String accountApprovalCode, String forgotAccountCredUserId, String resetPasswordTemp, String resetPasswordNew, String resetPasswordNewConfirm, String forgotAccountCredEmailId) {
		super();
		this.newUserId = newUserId;
		this.currentUserPassword = currentUserPassword;
		this.newUserPassword = newUserPassword;
		this.confirmNewUserPassword = confirmNewUserPassword;
		this.newEmailAddress = newEmailAddress;
		this.customerBillingAddress = customerBillingAddress;
		this.customerPaymentInformation = customerPaymentInformation;
		this.customerShippingAddress = customerShippingAddress;
		this.shippingSameAsBillingAddress= shippingSameAsBillingAddress;
		this.accountApprovalCode=accountApprovalCode;
		this.forgotAccountCredUserId = forgotAccountCredUserId;
		this.resetPasswordTemp= resetPasswordTemp;
		this.resetPasswordNew=resetPasswordNew;
		this.resetPasswordNewConfirm = resetPasswordNewConfirm;
		this.forgotAccountCredEmailId = forgotAccountCredEmailId;
	}
	
	public AccountManager()
	{
		
	}
	
	
	public String getNewUserId() {
		return newUserId;
	}
	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}
	public String getCurrentUserPassword() {
		return currentUserPassword;
	}
	public void setCurrentUserPassword(String currentUserPassword) {
		this.currentUserPassword = currentUserPassword;
	}
	public String getNewUserPassword() {
		return newUserPassword;
	}
	public void setNewUserPassword(String newUserPassword) {
		this.newUserPassword = newUserPassword;
	}
	public String getConfirmNewUserPassword() {
		return confirmNewUserPassword;
	}
	public void setConfirmNewUserPassword(String confirmNewUserPassword) {
		this.confirmNewUserPassword = confirmNewUserPassword;
	}
	public String getNewEmailAddress() {
		return newEmailAddress;
	}
	public void setNewEmailAddress(String newEmailAddress) {
		this.newEmailAddress = newEmailAddress;
	}
	
	
	public CustomerPaymentInformation getCustomerPaymentInformation() {
		return customerPaymentInformation;
	}
	
	public void setCustomerPaymentInformation(CustomerPaymentInformation customerPaymentInformation) {
		this.customerPaymentInformation = customerPaymentInformation;
	}

	public CustomerAddress getCustomerBillingAddress() {
		return customerBillingAddress;
	}

	public void setCustomerBillingAddress(CustomerAddress customerBillingAddress) {
		this.customerBillingAddress = customerBillingAddress;
	}

	public CustomerAddress getCustomerShippingAddress() {
		return customerShippingAddress;
	}

	public void setCustomerShippingAddress(CustomerAddress customerShippingAddress) {
		this.customerShippingAddress = customerShippingAddress;
	}
	
	public boolean isShippingSameAsBillingAddress() {
		return shippingSameAsBillingAddress;
	}

	public void setShippingSameAsBillingAddress(boolean shippingSameAsBillingAddress) {
		this.shippingSameAsBillingAddress = shippingSameAsBillingAddress;
	}

	public String getAccountApprovalCode() {
		return accountApprovalCode;
	}

	public void setAccountApprovalCode(String accountApprovalCode) {
		this.accountApprovalCode = accountApprovalCode;
	}

	public String getForgotAccountCredUserId() {
		return forgotAccountCredUserId;
	}

	public void setForgotAccountCredUserId(
			String forgotAccountCredUserId) {
		this.forgotAccountCredUserId = forgotAccountCredUserId;
	}

	public String getResetPasswordTemp() {
		return resetPasswordTemp;
	}

	public void setResetPasswordTemp(String resetPasswordTemp) {
		this.resetPasswordTemp = resetPasswordTemp;
	}

	public String getResetPasswordNew() {
		return resetPasswordNew;
	}

	public void setResetPasswordNew(String resetPasswordNew) {
		this.resetPasswordNew = resetPasswordNew;
	}

	public String getResetPasswordNewConfirm() {
		return resetPasswordNewConfirm;
	}

	public void setResetPasswordNewConfirm(String resetPasswordNewConfirm) {
		this.resetPasswordNewConfirm = resetPasswordNewConfirm;
	}

	public String getForgotAccountCredEmailId() {
		return forgotAccountCredEmailId;
	}

	public void setForgotAccountCredEmailId(String forgotAccountCredEmailId) {
		this.forgotAccountCredEmailId = forgotAccountCredEmailId;
	}
	
	
	
	
	
}
