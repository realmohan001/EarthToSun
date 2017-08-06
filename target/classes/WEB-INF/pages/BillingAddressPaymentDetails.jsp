<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>

<head>
<title>Cherry Sports Bids:Payment and Billing Address</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">

</head>

<body>


	<jsp:include page="HeaderPage.jsp" />

	<div class="container">


		<div class="row">

			<div class="col-lg-12 col-xs-12 text-center">
			
				<h3 class="text-left">Please enter the payment and billing address details to continue with	biding.....</h3>		

					<div class="panel panel-default">
					 <div class="panel-body">		


				<form:form method="POST" commandName="ACCOUNT_MANAGER"
					action="fillBillingAndCardDetails.htm" class="form-horizontal" role="form">

					<form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>

					<div class="successResult">
						<c:if test="${not empty SUCCESS_RESULT}">
							<p>
								<c:out value="${SUCCESS_RESULT}" />
							</p>
						</c:if>
					</div>

					
							
								
								
						<h4 class="text-left">Payment Details</h4>


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


						<h4 class="text-left mycontent-separator_top">Billing Address</h4>

						<div class="form-group">						
							<label class="col-sm-2 control-label">Company Name (optional)</label>
							
							<div class="col-sm-3">
							<form:input
									path="customerBillingAddress.companyName" maxlength="100"
									size="50"  class="form-control input-sm" placeholder="Enter Company Name"/>
									</div>
						</div>
						
						<div class="form-group">							
						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Contact
								Phone</label>
							<div class="col-sm-2">
							<form:input
									path="customerBillingAddress.addressContactPhoneNumber"
									maxlength="12" size="12" class="form-control input-sm" placeholder="Enter Contact Phone" />xxx-xxx-xxxx
									</div>
						</div>

						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Street
								Address</label>
								<div class="col-sm-5">
							<form:input
									path="customerBillingAddress.addressLine1" maxlength="100"
									size="70" class="form-control input-sm" placeholder="Enter Street Address" />
									</div>
						</div>			
									
						<div class="form-group">						
							<label class="col-sm-2 control-label">Apt, Suite, Bldg</label>
							<div class="col-sm-2">
							<form:input
									path="customerBillingAddress.addressLine2" maxlength="50"
									size="25" class="form-control input-sm" placeholder="Enter Apt" />
									</div>
						</div>			
						
						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>City</label>
							<div class="col-sm-3">
							<form:input
									path="customerBillingAddress.city" maxlength="70" size="40" class="form-control input-sm" placeholder="Enter City" />
									</div>
						</div>


						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>State</label>
							<div class="col-sm-8">
								<div class="row">								
										<div class="col-md-4">
												<form:input
											path="customerBillingAddress.state" maxlenght="60"  class="form-control input-sm" placeholder="Enter State"/>
										</div>
										
										<div class="col-md-8 pull-left">
											<div class="form-group">						
													<label class="col-sm-5 control-label pull-left"><span class="requiredField">*</span>ZIP Code</label>
													<div class="col-sm-5">
															<form:input
														path="customerBillingAddress.postalZipcode" maxlength="10"
														size="12" class="form-control input-sm" placeholder="Enter Zip code" />
													</div>
											</div>
										</div>
										
								</div>	
							</div>
							
						</div>					
						
						<div class="form-group">						

						<label class="col-sm-2 control-label"><span class="requiredField">*</span>Country</label>
							<div class="col-sm-2">
							<form:select
									path="customerBillingAddress.country" class="form-control input-sm" placeholder="Select Country">
									<form:option value="NONE" label="--- Select ---" />
									<form:options items="${userData.countryList}" />
								</form:select>
								</div>
						</div>		

						<!-- SHIPPING ADDRESS -->




						<h4 class="text-left mycontent-separator_top">Shipping Address</h4>
						
						
							<div class="row">
							
								<div class="col-md-1 text-right">
								<form:checkbox
									path="shippingSameAsBillingAddress"
									onclick="shippingAddressCheckedOrUnchecked(this.checked);"  class="form-control input-sm"/>
								
								</div>
								
								<div class="col-md-11 text-left">
								Shipping address same as billing address
								
								</div>
						
							</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">Company Name (optional)</label>
							<div class="col-sm-3">
							<form:input
									path="customerShippingAddress.companyName" maxlength="100"
									size="50" class="form-control input-sm" placeholder="Enter Company name" />
									</div>
						</div>


						<div class="form-group">
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Shipping
								Contact Phone</label>
								<div class="col-sm-2">
							<form:input
									path="customerShippingAddress.addressContactPhoneNumber"
									maxlength="12" size="12" class="form-control input-sm" placeholder="Enter Contact Phone" />xxx-xxx-xxxx
									</div>
						</div>

						<div class="form-group">							
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Street
								Address</label>
								<div class="col-sm-5">
							<form:input
									path="customerShippingAddress.addressLine1" maxlength="100"
									size="70" class="form-control input-sm" placeholder="Enter Street Address" />
									</div>
						</div>			
									
						<div class="form-group">						
							<label class="col-sm-2 control-label">Apt, Suite, Bldg</label>
							<div class="col-sm-2">
							<form:input
									path="customerShippingAddress.addressLine2" maxlength="50"
									size="25" class="form-control input-sm" placeholder="Enter Apt" />
									</div>
						</div>
						
									
						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>City</label>
							<div class="col-sm-3">
							<form:input
									path="customerShippingAddress.city" maxlength="70" size="40" class="form-control input-sm" placeholder="Enter City" />
									</div>
						</div>			
						
						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>State</label>
							<div class="col-sm-8">
								<div class="row">								
										<div class="col-md-4">
												<form:input
											path="customerShippingAddress.state" maxlenght="60"  class="form-control input-sm" placeholder="Enter State"/>
										</div>
										
										<div class="col-md-8 pull-left">
											<div class="form-group">						
													<label class="col-sm-5 control-label pull-left"><span class="requiredField">*</span>ZIP Code</label>
													<div class="col-sm-5">
															<form:input
														path="customerShippingAddress.postalZipcode" maxlength="10"
														size="12" class="form-control input-sm" placeholder="Enter Zip code" />
													</div>
											</div>
										</div>
										
								</div>	
							</div>
							
						</div>					
						
						
						<div class="form-group">						
							
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Country</label>
							<div class="col-sm-2">
							<form:select
									path="customerShippingAddress.country" class="form-control input-sm" placeholder="Select Country">
									<form:option value="NONE" label="--- Select ---" />
									<form:options items="${userData.countryList}" />
								</form:select>
								</div>
						</div>
						
											
						<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>	

						<div class="text-center">

							<input type="submit"
								value="Continue" /> <input type="button" value="Cancel"
								onclick="location.href='${pageContext.request.contextPath}/index'" />
						</div>		

				</form:form>
				
			</div><!-- END OF PANEL BODY -->
		</div>	<!-- END OF PANEL -->			

			</div> <!-- END OF COLUMN -->

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ajaxCallCode.js"></script>
	
	<script>
    $(document).ready(function(){
        var image = '<img src="${pageContext.request.contextPath}/img/cvv-info-panel.png">';
        $('#popover').popover({placement: 'bottom', content: image, html: true,trigger: 'hover'});
    });
</script>


</body>
</html>