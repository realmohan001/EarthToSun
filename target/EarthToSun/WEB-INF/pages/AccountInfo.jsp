<!DOCTYPE html>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Cherry Sports Bids:Account Info</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">


<script>
	$(document).ready(function() {

	});
</script>


</head>
<body>


	<jsp:include page="HeaderPage.jsp" />

	<div class="container-fluid">


		<div class="row">

			<div class="col-lg-3 col-xs-12">

					<ul class="nav nav-pills nav-stacked">
						<li><a
							href="${pageContext.request.contextPath}/AddressDetails.htm"
							id="addressDetails">Address Details</a></li>
						<li><a
							href="${pageContext.request.contextPath}/PaymentDetails.htm"
							id="paymentDetails">Payment Details </a></li>
						<li><a
							href="${pageContext.request.contextPath}/UserIDDetails.htm"
							id="userIDDetails">Update User ID </a></li>
						<li><a
							href="${pageContext.request.contextPath}/PasswordDetails.htm"
							id="passwordDetails">Update Password </a></li>
						<li><a
							href="${pageContext.request.contextPath}/EmailAddressDetails.htm"
							id="emailAddressDetails">Change Email Address</a></li>
						<li><a
							href="${pageContext.request.contextPath}/AccountPreferencesDetails.htm"
							id="accountPreferences">Change Account Preferences</a></li>
					</ul>
			</div>

			<div class="col-lg-9 col-xs-12">


				<h3 class="center-block">Welocme! You can View or Edit your
					account preferences!</h3>

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