<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cherry Sports Bids:Reset Password Details</title>

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

			<div class="col-lg-12 col-xs-12">
			
			<div class="panel panel-default">
				 <div class="panel-body">
	

<form:form method="POST" commandName="ACCOUNT_MANAGER" action="${pageContext.request.contextPath}/ResetPasswordSubmit.htm" class="form-horizontal" role="form">

					<form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>
					
									
				<div class="successResult">
					<c:if test="${not empty SUCCESS_RESULT}">
						<p> <c:out value="${SUCCESS_RESULT}" /> </p>
					</c:if>
				</div>



<h4 class="text-left">Reset Password</h4>


						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Enter Temporary Password</label>
							
							<div class="col-sm-3">
										<form:password path="resetPasswordTemp" class="form-control input-sm" placeholder="Enter temporary password"/>			
									</div>
						</div>
						
						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Enter new Password</label>
							
							<div class="col-sm-3">
										<form:password path="resetPasswordNew" maxlength="20" onkeyup="passwordStrength(this.value)" class="form-control input-sm" placeholder="Enter new password"/> 			
									</div><span id="passwordStrength" class="strength0 pull-left"></span> <span
								id="passwordDescription" class="pull-left"></span>	
						</div>
						
						
						<div class="form-group">						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Enter Confirm New Password</label>
							
							<div class="col-sm-3">
										<form:password path="resetPasswordNewConfirm" maxlength="20"  class="form-control input-sm" placeholder="Enter Confirm Password"/>			
									</div>
						</div>
						
						
						<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>
						





<div class="text-center">


		<input type="submit" value="Submit"/>
		<input type="button" value="Cancel"/>
</div>		

</form:form> 


		
			 </div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->
				


			</div>



		</div><!-- END OF ROW -->
	</div><!-- END OF CONTAINER -->

	<jsp:include page="FooterDesktopPage.jsp" />
	
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxCallCode.js"></script>
	

</body>
</html>