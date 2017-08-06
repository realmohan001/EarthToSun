<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cherry Sports Bids:Logout Page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
</head>
<body>


<jsp:include page="HeaderPage.jsp" />

<div class="container">


		<div class="row">

			<div class="col-lg-12 col-xs-12">
			
			<div class="panel panel-default">
			 <div class="panel-body">
			 
				<h4 class="text-left">log-out Successful! </h4> 
				
				<p class="text-left">
					Click here to go to Account <a href="${pageContext.request.contextPath}/">Home</a>
				</p> 
				
			 </div> <!-- END OF PANEL BODY -->
			</div> <!-- END OF PAENL -->				

		   </div>

		</div><!-- END OF ROW -->
	</div><!-- END OF CONTAINER -->

	<jsp:include page="FooterDesktopPage.jsp" />
	
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	
</body>
</html>