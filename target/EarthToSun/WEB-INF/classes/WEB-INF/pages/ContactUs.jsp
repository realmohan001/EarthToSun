<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Cherry Sports Bids:Contact Us</title>

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
							<div class="col-md-12 col-xs-12 text-left">
							
								<h3 class="text-left">  Contact Us</h3>	

									<div class="panel panel-default">
										<div class="panel-body">	
										
										
											<address>
											  <strong>Cherry Bids, Inc.</strong><br>
											  6501 Escena Blvd, Apt 1032<br>
											  Irving, TX 75039<br>
											  <abbr title="Phone">P:</abbr> (612) 558-3344
											</address>
											
											<address>						  
											  <a href="mailto:#">mullapudionly@gmail.com</a>
											</address>
										
										</div><!-- END OF PANEL BODY -->
									</div>	<!-- END OF PANEL -->		
						
						</div>
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