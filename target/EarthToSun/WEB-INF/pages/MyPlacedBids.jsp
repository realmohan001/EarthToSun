<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>Cherry Sports Bids:My Placed Bids</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">


<script type="text/javascript">

</script>

</head>

<body>

<jsp:include page="HeaderPage.jsp" />

	<div class="container">
	
	 <h3 class="text-left">My Placed Bids</h3>
	
	
	  
					  
				  
        <!-- PAGINATION ITEMS -->		
		<div class="row">
			<div class="col-sm-10 col-md-10 col-lg-8"> 
				<div class="panel panel-default">
				  <div class="panel-body">
					  
					  <div class="row">
						 		
							<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12">
							
									<table class="table table-striped">
											<tr>
											    <td><strong>Auction Details </strong> </td>
											    <td><strong>Bid Price </strong> </td>
												<td><strong>Bid Status </strong> </td>
												<td><strong>Bid Date </strong></td>							
											</tr>	
									
										<c:forEach items="${userData.myPlacedBids}" var="myBid"  varStatus="theCount" begin="${userData.placedBidsBeginRecord}" end="${userData.placedBidsEndRecord}">
											<tr>												
												<td> <a href="${pageContext.request.contextPath}/itemDetails/${myBid.items.itemId}" class="btn btn-primary"> Auction Details </a> </td>
												<td> ${myBid.bidPrice} </td>
												<td> ${myBid.winnginBid} </td>
												<td> ${myBid.bidDate} </td>
											</tr>							
										</c:forEach>
									</table>
							
								
								  
							</div>		
						
					  </div> <!-- END OF ROW -->
				  </div><!-- end of panel body -->
				</div>	<!-- END OF PANEL -->	
			</div>	<!-- END OF COLUMN -->
		</div><!-- END OF ROW -->
		
		
		 <!-- PAGINATION LINKS HEADER -->
         <div class="row">
			<div class="col-sm-10 col-md-10 col-lg-8"> 
				<div class="panel panel-default panel_background">
				  <div class="panel-body">
				  
						   <div class="row">
							  
							  		  <div class="col-sm-4 col-md-4 text-left">
							  		  	Showing ${userData.placedBidsBeginRecord+1}-${userData.placedBidsEndRecord+1} of ${fn:length(userData.myPlacedBids)}
									  </div>
									  
							  		  <div class="col-sm-4 col-md-3 text-left">
							  		  <form name="itemsPerpageForm" action="${pageContext.request.contextPath}/closedAuctionPaginationItemsPerPage"  method="POST">
								  		  Bids per page <select name="placedBidsPerPage" onchange="this.form.submit()">
												
												<c:if test="${userData.placedBidsRecordsPerPage == 12}">
													<option value="12" selected="selected">12</option>	
												</c:if>
												
												<c:if test="${userData.placedBidsRecordsPerPage == 18}">
													<option value="18" selected="selected">18</option>
												</c:if>
												
												<c:if test="${userData.placedBidsRecordsPerPage == 24}">
													<option value="24" selected="selected">24</option>
												</c:if>
												
												
												<c:if test="${userData.placedBidsRecordsPerPage != 12}">
													<option value="12">12</option>	
												</c:if>
												
												<c:if test="${userData.placedBidsRecordsPerPage != 18}">
													<option value="18">18</option>
												</c:if>
												
												<c:if test="${userData.placedBidsRecordsPerPage != 24}">
													<option value="24">24</option>
												</c:if>
											
											</select>
									</form>				  		  
									  </div>
									  
							  
								  		<div class="col-sm-4 col-md-5 text-right">
										
								  
											<%--For displaying Previous link except for the 1st page --%>
										    <c:if test="${userData.placedBidsCurrentPage != 1}">
										        <a href="${pageContext.request.contextPath}/placedBidsPagination/?pageNo=${userData.placedBidsCurrentPage - 1}"><span class="glyphicon glyphicon-backward"></span> Previous</a>
										    </c:if>
										 
										    <%--For displaying Page numbers.
										    The when condition does not display a link for the current page--%>
										            <c:forEach begin="1" end="${userData.placedBidsTotalPages}" var="i">
										                <c:choose>
										                    <c:when test="${userData.placedBidsCurrentPage eq i}">
										                        ${i}
										                    </c:when>
										                    <c:otherwise>
										                        <a href="${pageContext.request.contextPath}/placedBidsPagination/?pageNo=${i}">${i}</a>
										                    </c:otherwise>
										                </c:choose>
										            </c:forEach>
		 
										    <%--For displaying Next link --%>
										    <c:if test="${userData.placedBidsCurrentPage lt userData.placedBidsTotalPages}">
										        <a href="${pageContext.request.contextPath}/placedBidsPagination/?pageNo=${userData.placedBidsCurrentPage + 1}">Next <span class="glyphicon glyphicon-forward"></span></a>
										    </c:if>						  
											
								  		</div>
							  </div>
		  		  </div><!-- end of panel body -->
				</div>	<!-- END OF PANEL -->	
			</div>	<!-- END OF COLUMN -->
		</div><!-- END OF ROW -->
		



	</div><!-- END OF CONTAINER FLUID -->

	<jsp:include page="FooterDesktopPage.jsp" />

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.countdown.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.countdown.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/DateOperationsCallCode.js"></script>
	


</body>
</html>