<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Cherry Sports Bids:User/Fan Registration Page</title>

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

				<div class="panel panel-default">
				 <div class="panel-body">
				 
				<h4 class="text-left">User/Fan Account Registration</h2>

				<form:form method="POST" commandName="REGISTER_CUSTOMER"
					action="RegisterUserCustomerSubmit.htm" class="form-horizontal" role="form">
								
					<form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>
									
					
								
							<div class="form-group">				
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>First
								Name</label>
								<div class="col-sm-2">
							<form:input path="firstName"
									maxlength="60"  class="form-control input-sm" placeholder="Enter First name"/>
									</div>	
							</div>		
						
						<div class="form-group">			
							<label class="col-sm-2 control-label">Middle Initial</label>
							<div class="col-sm-2">
							<form:input path="middleInitial"
									maxlength="1" class="form-control input-sm" placeholder="Enter Middle Initial" />
									</div>	
						</div>			
									
						
						<div class="form-group">			
						<label class="col-sm-2 control-label"><span class="requiredField">*</span>Last
								Name</label>
								<div class="col-sm-2">
							<form:input path="lastName"
									maxlength="60"  class="form-control input-sm" placeholder="Enter last name"/>
									</div>	
						</div>			
									
									
						<div class="form-group">			
						<label class="col-sm-2 control-label"><span class="requiredField">*</span>Email
								ID</label>
								<div class="col-sm-3">
							<form:input path="emailAddress"
									maxlength="100"  class="form-control input-sm" placeholder="Enter Email ID"/>
									</div>	
						</div>						

							<div class="form-group">			
								<label class="col-sm-2 control-label"><span class="requiredField">*</span>User ID</label>
								<div class="col-sm-2">
							<form:input path="userId"
									maxlength="15"  class="form-control input-sm" placeholder="Enter User ID"/>
									</div>	
						</div>						

						<div class="form-group">			
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Password</label>
							<div class="col-sm-3">
							<form:password path="userPassword"
									maxlength="20" onkeyup="passwordStrength(this.value)"  class="form-control input-sm" placeholder="Enter password"/>
								</div> <span id="passwordStrength" class="strength0 pull-left"></span> <span
								id="passwordDescription" class="pull-left"></span>	
						</div>

						<div class="form-group">			
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Confirm
								Password</label>
								<div class="col-sm-3">
							<form:password
									path="confirmUserPassword" maxlength="20"  class="form-control input-sm" placeholder="Enter Confirm Password"/>
									</div>	
						</div>

						<div class="form-group">			
							<label class="col-sm-2 control-label">Phone
								Number</label>
								<div class="col-sm-2">
							<form:input path="phoneNumber"
									maxlength="12" size="12" class="form-control input-sm" placeholder="Enter Phone Number" /> 
									</div><span class="pull-left">xxx-xxx-xxxx</span>
						</div>

						<div class="form-group">			
							<label class="col-sm-2 control-label">Business/Foundation Name</label>
							<div class="col-sm-4">
							<form:input path="businessName"
									maxlength="60" class="form-control input-sm" placeholder="Enter Business/Foundation name" />
									</div>	
						</div>
						
						<div class="text-center">
						
							<span class="requiredField">*</span>
							<form:checkbox path="acceptedTermsAndConditions" /> I accept the
								terms and conditions.
						</div>
						
						<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>	
							
						<div class="text-center">

							<input type="submit" />
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
	 <script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxCallCode.js"></script>


</body>
</html>