<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Cherry Sports Bids:New Item Added Successful!</title>
	
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
			 
				<h4 class="text-left">New Item added Successful! </h4> 
				
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
