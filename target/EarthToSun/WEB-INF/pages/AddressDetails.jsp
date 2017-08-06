<!DOCTYPE html>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Cherry Sports Bids:Address Info</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/DateOperationsCallCode.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxCallCode.js"></script>
 
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
			
			<h3 class="text-left">Current Address Details</h3>
				 
			 		<div class="row">

					<div class="col-sm-3 col-md-3 col-lg-3 col-xs-12"> <!-- START OF COLUMN -->
					
						<div class="panel panel-default">
				 			<div class="panel-body">
					

						<ul class="nav nav-pills nav-stacked">
							<li class="active text-right"><a
								href="${pageContext.request.contextPath}/AddressDetails.htm"
								id="addressDetails">Address Details</a></li>
							<li class="text-right"><a
								href="${pageContext.request.contextPath}/PaymentDetails.htm"
								id="paymentDetails">Payment Details </a></li>
							<li class="text-right"><a
								href="${pageContext.request.contextPath}/UserIDDetails.htm"
								id="userIDDetails">Update User ID </a></li>
							<li class="text-right"><a
								href="${pageContext.request.contextPath}/PasswordDetails.htm"
								id="passwordDetails">Update Password </a></li>
							<li class="text-right"><a
								href="${pageContext.request.contextPath}/EmailAddressDetails.htm"
								id="emailAddressDetails">Change Email Address</a></li>
							<li class="text-right"><a
								href="${pageContext.request.contextPath}/AccountPreferencesDetails.htm"
								id="accountPreferences">Change Account Preferences</a></li>
						</ul>
						
							</div>
						</div>
						
					</div>


			<div class="col-sm-9 col-md-9 col-lg-9 col-xs-12"> <!-- START OF COLUMN -->
			
			
			<div class="panel panel-default">
				 			<div class="panel-body">


<form:form method="POST" commandName="ACCOUNT_MANAGER" action="${pageContext.request.contextPath}/AddressDetails.htm" name="addressEditForm" class="form-horizontal" role="form">

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
	
	
	
	<c:if test="${ empty ACCOUNT_MANAGER.customerShippingAddress && empty ACCOUNT_MANAGER.customerBillingAddress }">
		
				<p class="text-center">No address information in the Account</p>	
	</c:if>
	
	<c:if test="${not empty ACCOUNT_MANAGER.customerBillingAddress}">
	
			<h4 class="text-left">Billing Address on account</h4>
			
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
							<div class="col-sm-3">
							<form:select
									path="customerBillingAddress.country" class="form-control input-sm" placeholder="Select Country">
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
						<input type="button" value="Edit Billing Address"  onclick="document.addressEditForm.action='AddressDetailsEditBillingAddress.htm';document.addressEditForm.submit()"/>
						<input type="button" value="Cancel" onclick="location.href='${pageContext.request.contextPath}/'"/>
	</c:if>
	
	<c:if test="${not empty ACCOUNT_MANAGER.customerShippingAddress}">
	
		
		<h4 class="text-left mycontent-separator_top">Shipping Address on account</h4>
			
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
							<div class="col-sm-3">
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
				
			
						<input type="button" value="Edit Shipping Address" onclick="document.addressEditForm.action='AddressDetailsEditShippingAddress.htm';document.addressEditForm.submit()"/>
						<input type="button" value="Cancel" onclick="location.href='${pageContext.request.contextPath}/'"/>
						</div>
				
			
	
	</c:if>
	
	

</form:form>

							 </div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->
			


			</div> <!-- END OF COLUMN -->
			
			
										
				</div>	<!-- END OF ROW -->
				
				

				
				
			</div> <!-- END OF COLUMN -->
			
			

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>



</body>
</html>