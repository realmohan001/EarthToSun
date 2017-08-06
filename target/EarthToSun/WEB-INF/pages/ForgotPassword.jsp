<!DOCTYPE html>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Customer Forgot Password Page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

</head>

<body>

<jsp:include page="HeaderPage.jsp" />


<div class="container">


		<div class="row">

			<div class="col-md-12 col-xs-12 text-center">
			
				<div class="panel panel-default">
				 <div class="panel-body">
				 
			 		<div class="row">
						<div class="col-md-6 col-xs-12 text-center mycontent-left">

					
									<h4 class="text-left">Cherry Bids Password Reset/Account Recovery</h4>
									
									




Customer Forgot Password

<form:form method="POST" commandName="FORGOT_PASSWORD">

<form:errors path="*" cssClass="errorblock" element="div"/>


Email ID
<form:input path="customerEmailID" />

<input type="submit" name="Submit" />



</form:form>

</body>
</html>