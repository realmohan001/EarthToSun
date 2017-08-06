<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cherry Sports Bids:Access Denied page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

</head>
<body>

<jsp:include page="HeaderPage.jsp" />

<div class="container">


		<div class="row">

			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="panel panel-default">
			 	<div class="panel-body">			

					<h4 class="text-left">Access Denied!</h4>
					
					<p class="text-left">Possible reasons could be,</p>
								
								<ul class="text-left">
									<li>Your account is not activated, please check your E-mail and follow the instructions to activate your account!</li>
									<li>Based on your account authorization, you are not allowed to access this page. Please <a href="${pageContext.request.contextPath}/ContactUs">contact</a> admin for any questions!  </li>									
								</ul>
				</div>
			</div>							


		</div>

		</div><!-- END OF ROW -->
	</div><!-- END OF CONTAINER FLUID -->

	<jsp:include page="FooterDesktopPage.jsp" />
	
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	
	


</body>
</html>