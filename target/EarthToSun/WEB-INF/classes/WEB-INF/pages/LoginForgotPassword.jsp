<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Cherry Sports Bids:Forgot Password Page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

</head>

<body>
<jsp:include page="HeaderPage.jsp" />

<div class="container">

	<div class="row">

			<div class="col-lg-12 col-xs-12 text-center">

				<div class="panel panel-default">
				 <div class="panel-body">
				 
				<h4 class="text-left">Cherry Sports Bids Password Reset/Account Recovery</h4>
				

<form:form method="POST" commandName="ACCOUNT_MANAGER" action="LoginForgotPassword.htm" class="form-horizontal" role="form">


                     <form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>

  					<p class="text-left"> Please enter User Id below to reset your Password. If you do not remember your User Id, please <a href="${pageContext.request.contextPath}/ContactUs">Contact Us</a>.</p>


					<div class="form-group">				
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Enter your User ID</label>
								<div class="col-sm-2">
							<form:input path="forgotAccountCredUserId"
									maxlength="60"  class="form-control input-sm" placeholder="Enter user ID"/>
									</div>	
					</div>		


						<div class="text-left">

							<input type="submit" value="Reset Password" />

						</div>	

	
</form:form>

		</div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->	
				
	</div> <!-- END OF COLUMN -->

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER  -->
	
	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>