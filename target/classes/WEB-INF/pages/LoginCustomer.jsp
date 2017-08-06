<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Cherry Sports Bids:Customer Login Page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">
		 <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>
	

</head>

<body>

	<jsp:include page="HeaderPage.jsp" />

	<div class="container">


		<div class="row">

			<div class="col-md-12 col-xs-12 text-center">
			
			<h3 class="text-left">  Welcome to my Cherry Bids</h3>		
			
			
				<div class="panel panel-default">
				 <div class="panel-body">
				 
			 		<div class="row">
						<div class="col-md-6 col-xs-12 text-center mycontent-left">

					
									<h4 class="text-left">Log In to My Account</h4>
					
					
									<form action="<c:url value='/j_spring_security_check' />" method="post" class="form-horizontal" role="form">
					
					
											<c:if test="${not empty error}">
														<div class="alert alert-danger text-left">${error}</div>					
											</c:if>
											
											
					
											<div class="form-group">												
													<label for="Username" class="col-sm-2 control-label">Username</label>											
													<div class="col-sm-5">
														<input id="j_username" name="j_username" type="text" maxlength="15" id="Username" class="form-control input-sm" placeholder="Enter Username"/>											
													</div>	
											</div>
											
											<div class="form-group">											
												<label for="Password" class="col-sm-2 control-label">Password</label>												
												<div class="col-sm-5">
													<input id="j_password" name="j_password" type="password" maxlength="20" id="Password" class="form-control input-sm" placeholder="Enter password" />
												</div>	
											</div>
					
											<div class="col-md-12 text-left">
												<a href="${pageContext.request.contextPath}/LoginForgotPassword.htm">Forgot Your Password?</a>
											</div>
											
											<div class="col-md-12 text-left">
												<a href="${pageContext.request.contextPath}/LoginForgotUsername.htm">Forgot Your Username?</a>
											</div>
											
											<div class="col-md-12 text-left">
												&nbsp;
											</div>
											
											<div class="col-md-4">
												<button type="submit" class="btn btn-primary">Sign On <span class="rarrow">&#9658;</span></button>
											</div>
													
									</form>
							</div> <!-- END OF LOGIN COLUMN -->
							
							<div class="col-md-6 col-xs-12 text-center">
								<h4 class="text-left">Register for My Account</h4>
								
								<p class="text-left">Get easy, online access to your Cherry Sports Bids.</p>
								
								<ul class="text-left">
									<li>Bid on your favorite team's collectables</li>
									<li>View your bids</li>
									<li>Manage your account</li>
									<li>Get Support for your Account</li>
								</ul>
								
								<p class="text-left">Much More!</p>
								
								<div class="col-md-4">
								<a href="${pageContext.request.contextPath}/RegisterCustomer.htm" class="btn btn-primary btn-sm"> Register Now <span class="rarrow">&#9658;</span></a>
								</div>
							
							</div> <!-- END OF REGISTER COLUMN -->
							
							
				</div>	
				
				
			 </div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->
				
				
			</div> <!-- END OF COLUMN -->
	</div> <!-- END OF ROW -->

	</div><!-- END OF CONTAINER FLUID -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>