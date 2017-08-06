<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>Cherry Sports Bids:Customer Account Home</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">


<script type="text/javascript">
	
</script>

</head>

<body>

	<jsp:include page="HeaderPage.jsp" />

	<div class="container-fluid">


		<div class="row">

			<div class="col-lg-12 col-xs-12">



				<h2>Items available for Bidding</h2>

				<ul>


					<c:forEach items="${userData.allItems}" var="item">

						<li><img
							src="https://s3.amazonaws.com/CherryBids/${item.itemImageLocation1}"
							HEIGHT="100" WIDTH="100" BORDER="0" alt="my image"
							title="${item.itemName}" />
							<h3>${item.itemName}</h3>
							<p class="timeLeft">
								Time Left : <span id="countdown${item.itemId}"
									class="countdown_row"> </span>
							</p>
							<p class="currentBid">
								Current Bid :
								<fmt:formatNumber value="${item.itemCurrentBidPrice}"
									type="currency" />
							</p> <a
							href="${pageContext.request.contextPath}/itemDetails/${item.itemId}" class="btn btn-primary btn-sm">Details <span class="rarrow">&#9658;</span>
						</a></li>
					</c:forEach>

				</ul>


			</div>

		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER FLUID -->


	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/jquery.countdown.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.countdown.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/DateOperationsCallCode.js"></script>

				<script type="text/javascript">
					<c:forEach items="${userData.allItems}" var="item">
					CountDownTimer(
							'countdown${item.itemId}',
							'<fmt:formatDate  type="both" dateStyle="long" timeStyle="short" value="${item.itemFinalTimeToBid}" />');
					</c:forEach>
				</script>
</body>
</html>