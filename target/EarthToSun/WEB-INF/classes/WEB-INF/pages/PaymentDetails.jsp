<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
<head>

<title>Cherry Sports Bids:payment Info</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/DateOperationsCallCode.js"></script> 

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
 
 <script>

$(document).ready(function() {
    
});

</script>
 
 
</head>
<body>


<jsp:include page="HeaderPage.jsp" />

<div class="container">


		<div class="row">
		
			<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12 text-center">
			
				<h3 class="text-left">Payment Details</h3>
			
			 		<div class="row">

			<div class="col-sm-3 col-md-3 col-lg-3 col-xs-12">
			
				<div class="panel panel-default">
					 <div class="panel-body">
				
						<ul class="nav nav-pills nav-stacked">
							<li><a
								href="${pageContext.request.contextPath}/AddressDetails.htm"
								id="addressDetails">Address Details</a></li>
							<li class="active"><a
								href="${pageContext.request.contextPath}/PaymentDetails.htm"
								id="paymentDetails">Payment Details </a></li>
							<li><a
								href="${pageContext.request.contextPath}/UserIDDetails.htm"
								id="userIDDetails">Update User ID </a></li>
							<li><a
								href="${pageContext.request.contextPath}/PasswordDetails.htm"
								id="passwordDetails">Update Password </a></li>
							<li><a
								href="${pageContext.request.contextPath}/EmailAddressDetails.htm"
								id="emailAddressDetails">Change Email Address</a></li>
							<li><a
								href="${pageContext.request.contextPath}/AccountPreferencesDetails.htm"
								id="accountPreferences">Change Account Preferences</a></li>
						</ul>
					</div>
				</div>			
					
			</div>

<div class="col-sm-9 col-md-9 col-lg-9 col-xs-12">

<div class="panel panel-default">
				 <div class="panel-body">

<form:form method="POST" commandName="ACCOUNT_MANAGER" action="PaymentDetails.htm" name="paymentEditForm" class="form-horizontal" role="form">

		<form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>

<div>
	<c:if test="${not empty SUCCESS_RESULT}">
		<div class="alert alert-success text-center"> <c:out value="${SUCCESS_RESULT}" /> </div>
	</c:if>
</div>	
	
	<c:if test="${ empty ACCOUNT_MANAGER.customerPaymentInformation }">
		<p class="text-center">
				No Payment information in the Account.
		</p>	
	</c:if>
	
	
	<c:if test="${not empty ACCOUNT_MANAGER.customerPaymentInformation}">
	
	<div class="form-group">
						<label class="col-sm-2 control-label"> <span class="requiredField">*</span>Name
								on Card</label>
								<div class="col-sm-3">
							<form:input
									path="customerPaymentInformation.nameOnCard" maxlength="100" class="form-control input-sm" placeholder="Enter Nmae On Card" />
									</div>
						</div>

						
						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Contact
								Phone Number</label>
							<div class="col-sm-2">
							<form:input
									path="customerPaymentInformation.billingContactPhoneNumber"
									maxlength="12" size="12"  class="form-control input-sm" placeholder="Enter Contact Phone Number"/> 
									</div><span class="pull-left">xxx-xxx-xxxx</span>
						</div>			

						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Card
								Type</label>
								<div class="col-sm-2">
							<form:select
									path="customerPaymentInformation.cardType" class="form-control input-sm" placeholder="Select Card Type">
									<form:option value="NONE" label="Card Type" />
									<form:option value="Visa" label="Visa" />
									<form:option value="MasterCard" label="MasterCard" />
									<form:option value="American Express" label="American Express" />
									<form:option value="Discover" label="Discover" />
								</form:select>
								</div><span class="pull-left"><img
								src="${pageContext.request.contextPath}/img/payment_type.jpg"
								style="" /></span>
						</div>

						<div class="form-group">	
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Credit/Debit
								Card Number </label>
								<div class="col-sm-3">
							<form:input
									path="customerPaymentInformation.cardNumber" maxlength="16" class="form-control input-sm" placeholder="Enter Card Number" />
									</div>
						</div>

						
							<div class="form-group">
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>CVV</label>
							<div class="col-sm-2">
							<form:input
									path="customerPaymentInformation.cvv" maxlength="4" size="5" class="form-control input-sm" placeholder="Enter CVV" />								
								</div>
								<span class="pull-left"> <img	src="${pageContext.request.contextPath}/img/cvv-info-button.jpg" style="vertical-align: bottom; cursor: pointer;" id="popover"  /></span>
							</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Expires</label>
							<div class="col-sm-4">
							
								<div class="row">
									<div class="col-md-6">
										<form:select
											path="customerPaymentInformation.cardExpirationMonth" class="form-control input-sm" placeholder="Select Expiry Month">
											<form:option value="NONE" label="Month" />
											<form:options items="${userData.monthList}" />
										</form:select> 								
									</div>
									
									<div class="col-md-6">								
										<form:select
											path="customerPaymentInformation.cardExpirationYear" class="form-control input-sm" placeholder="Enter Expiry Year">
											<form:option value="NONE" label="year" />
											<form:options items="${userData.yearList}" />
										</form:select>								
									</div>
								</div>						
							</div>	
						</div>	
	
	
	<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>		

	
	<div class="text-center">
			<input type="button" value="Edit Payment Information"  onclick="document.paymentEditForm.action='PaymentDetails.htm';document.paymentEditForm.submit()"/>
			<input type="button" value="Cancel" onclick="location.href='${pageContext.request.contextPath}/'"/>
	</div>
				
			
			
	</c:if>
	
	

</form:form>

	 </div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->


		</div>
		
						</div>	<!-- END OF ROW -->
		
				
				
			</div> <!-- END OF COLUMN -->
		
		

		</div><!-- END OF ROW -->
	</div><!-- END OF CONTAINER -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script>
    $(document).ready(function(){
        var image = '<img src="${pageContext.request.contextPath}/img/cvv-info-panel.png">';
        $('#popover').popover({placement: 'bottom', content: image, html: true,trigger: 'hover'});
    });
</script>

</body>
</html>