<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Cherry Sports Bids:Registration Page</title>

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
			
			<h3 class="text-left"> Register to my Cherry Bids</h3>	

				<div class="panel panel-default">
				 <div class="panel-body">
				 
			 		<div class="row">
						<div class="col-md-6 col-xs-12 text-center">

								<h4 class="text-left">Register for User/Fan Account</h4>
								
								<div class="text-left">
				
									<a href="${pageContext.request.contextPath}/RegisterUserCustomer.htm" class="btn btn-primary btn-sm"> Register Now <span class="rarrow">&#9658;</span></a>
								
								</div>
								
						</div> <!-- END OF REGISTER FOR USER ACCOUNT -->
							
							<div class="col-md-6 col-xs-12 text-center mycontent-right">
			
										<h4 class="text-left">Register for Auction/Foundation Account</h4>
						
										<p class="text-left">You need approval code to create a foundation account, please <a href="${pageContext.request.contextPath}/ContactUs">Contact Us</a>.</p>										
						
										<p class="text-left">If you already have the access code, enter below to continue.
										</p>
						
						
										<form:form method="POST" commandName="ACCOUNT_MANAGER"
											action="RegisterCustomerSubmit.htm" class="form-horizontal" role="form">
						
											
										<form:errors path="*">								
											<div class="alert alert-danger text-left">					
												<form:errors path="*"/>	
											</div>
										</form:errors>
															
															
												<div class="form-group">												
													<label for="accountApprovalCode" class="col-sm-6 control-label">Enter the access/approval code</label>											
													<div class="col-sm-5">
														<form:input path="accountApprovalCode" maxlength="10" id="accountApprovalCode" class="form-control input-sm" placeholder="Enter Approval code" />											
													</div>	
												</div>
						
													<button type="submit" class="btn btn-primary">Submit <span class="rarrow">&#9658;</span></button>
						
										</form:form>
							</div> <!-- END OF REGISTER FOR AUCTION ACCOUNT COLUMN -->

						</div><!-- END OF ROW -->
					 </div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PANEL -->	
		
					
			</div> <!-- END OF COLUMN -->
	</div> <!-- END OF ROW -->

	</div><!-- END OF CONTAINER FLUID -->


	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>



</body>
</html>