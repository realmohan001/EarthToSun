<!DOCTYPE html>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Cherry Sports Bids:About Us</title>

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

			<div class="col-lg-12 col-xs-12 text-left">

				<h3 class="text-left">About Us</h3>	

				<div class="panel panel-default">
					<div class="panel-body">

									<p>Cherry Sports Bids was formed in April of 2013 by a group of
										friends who have a passion for sports and helping others. The
										friends wanted to bring both of these worlds together and came up
										with a novel idea connecting passionate sports fans with their
										favorite sports teams and players.</p>

									<p>The goal of Cherry Sports Bids is to provide a simple
										platform for professional sports teams to raise money for their own
										charitable foundations and allow fans to have access to
										sought-after authentic team memorabilia and experiences.</p>
					
					</div><!-- END OF PANEL BODY -->
				</div>	<!-- END OF PANEL  -->

			</div>

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER -->


	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>


</body>
</html>