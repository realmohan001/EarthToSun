<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Cherry Sports Bids:Error Page</title>

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
			<div class="panel panel-danger">
				<div class="panel-heading">
    				<h3 class="panel-title">System Error</h3>
  				</div>
					
			 	<div class="panel-body">
					<h4 class="text-left">Oppss...System error, please visit it later </h4>
					<p class="text-left">Please <a href="${pageContext.request.contextPath}/ContactUs">contact</a> admin for any questions! </p>
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