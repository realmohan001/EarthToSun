<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Cherry Sports Bids:Email Address Info</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

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
			
	
			 		<div class="row">
		
		
		
			<div class="col-sm-3 col-md-3 col-lg-3 col-xs-12">
			
						<div class="panel panel-default">
				 <div class="panel-body">
				 
					<ul class="nav nav-pills nav-stacked">
						<li class="text-right"><a
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
						<li class="active text-right"><a
							href="${pageContext.request.contextPath}/EmailAddressDetails.htm"
							id="emailAddressDetails">Change Email Address</a></li>
						<li class="text-right"><a
							href="${pageContext.request.contextPath}/AccountPreferencesDetails.htm"
							id="accountPreferences">Change Account Preferences</a></li>
					</ul>
				</div>
				</div>	
					
			</div>

	<div class="col-sm-9 col-md-9 col-lg-9 col-xs-12">
	
			<div class="panel panel-default">
				 <div class="panel-body">
				 


<form:form method="POST" commandName="ACCOUNT_MANAGER" action="EmailAddressDetails.htm" class="form-horizontal" role="form">

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




<h4 class="text-left">Current Email Address Details</h4>


						<div class="form-group">						
							<label class="col-sm-2 control-label">Current Email Address</label>
							
							<div class="col-sm-3">
							<p class="form-control-static"> ${userData.customer.emailAddress }</p>
									</div>
						</div>


						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>New Email Address</label>
							
							<div class="col-sm-3">
							 <form:input path="newEmailAddress" maxlength="100" class="form-control input-sm" placeholder="Enter new Email Id"/>					
									</div>
						</div>							


	
		<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>	


<div class="text-center">
<input type="submit" value="Update"/>
<input type="button" value="Cancel" onclick="location.href='${pageContext.request.contextPath}/'"/>
</div>

</form:form>


				</div> <!-- END OF PANEL BODY -->
						</div> <!-- END OF PAENL -->
 

			</div>
			
				</div>	<!-- END OF ROW -->
	
							</div> <!-- END OF COLUMN -->			
			
			

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER FLUID -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>



</body>
</html>