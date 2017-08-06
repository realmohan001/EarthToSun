<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<title>Cherry Sports Bids:Closed Auctions</title>

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
	
	 <h3 class="text-left">Closed Auctions</h3>
	
	
	   <!-- PAGINATION LINKS HEADER -->
        <div class="row">
			<div class="col-sm-10 col-md-10 col-lg-8"> 
				<div class="panel panel-default panel_background">
				  <div class="panel-body">
				  
						   <div class="row">
							  
							  		  <div class="col-sm-4 col-md-4 text-left">
							  		  	Showing ${userData.closedBeginRecord+1}-${userData.closedEndRecord+1} of ${fn:length(userData.closedItems)}
									  </div>
									  
							  		  <div class="col-sm-4 col-md-3 text-left">
							  		  <form name="itemsPerpageForm" action="${pageContext.request.contextPath}/closedAuctionPaginationItemsPerPage"  method="POST">
								  		  Items per page <select name="closedItemsPerPage" onchange="this.form.submit()">
												
												<c:if test="${userData.closedRecordsPerPage == 6}">
													<option value="6" selected="selected">6</option>	
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage == 9}">
													<option value="9" selected="selected">9</option>
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage == 12}">
													<option value="12" selected="selected">12</option>
												</c:if>
												
												
												<c:if test="${userData.closedRecordsPerPage != 6}">
													<option value="6">6</option>	
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage != 9}">
													<option value="9">9</option>
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage != 12}">
													<option value="12">12</option>
												</c:if>
											
											</select>
									</form>				  		  
									  </div>
									  
							  
								  		<div class="col-sm-4 col-md-5 text-right">
										
								  
											<%--For displaying Previous link except for the 1st page --%>
										    <c:if test="${userData.closedCurrentPage != 1}">
										        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${userData.closedCurrentPage - 1}"><span class="glyphicon glyphicon-backward"></span> Previous</a>
										    </c:if>
										 
										    <%--For displaying Page numbers.
										    The when condition does not display a link for the current page--%>
										            <c:forEach begin="1" end="${userData.closedTotalPages}" var="i">
										                <c:choose>
										                    <c:when test="${userData.closedCurrentPage eq i}">
										                        ${i}
										                    </c:when>
										                    <c:otherwise>
										                        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${i}">${i}</a>
										                    </c:otherwise>
										                </c:choose>
										            </c:forEach>
		 
										    <%--For displaying Next link --%>
										    <c:if test="${userData.closedCurrentPage lt userData.closedTotalPages}">
										        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${userData.closedCurrentPage + 1}">Next <span class="glyphicon glyphicon-forward"></span></a>
										    </c:if>						  
											
								  		</div>
							  </div>
		  		  </div><!-- end of panel body -->
				</div>	<!-- END OF PANEL -->	
			</div>	<!-- END OF COLUMN -->
		</div><!-- END OF ROW -->
					  
				  
        <!-- PAGINATION ITEMS -->		
		<div class="row">
			<div class="col-sm-10 col-md-10 col-lg-8"> 
				<div class="panel panel-default">
				  <div class="panel-body">
					  
					  <div class="row">
						 <c:forEach items="${userData.closedItems}" var="closedAuction"  varStatus="theCount" begin="${userData.closedBeginRecord}" end="${userData.closedEndRecord}">		
							<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12">
								  <div class="thumbnail">
										<div class="caption">
										  <div class="thumbnail_heigt_pagination">
											<img src="https://s3.amazonaws.com/CherryBids/${closedAuction.itemImageLocation1}" BORDER="0" alt="my image" title="${closedAuction.itemName}" class="center-block img-thumbnail img-responsive img_height_paginationlist"/>
													<p class="text-center text_color_21A5BC"><a href="${pageContext.request.contextPath}/itemDetails/${closedAuction.itemId}">  ${closedAuction.itemName} </a> </p>
													<p class="text-center"> Foundation Name :</p>		
													<p class="text-center text_color_21A5BC"> ${closedAuction.businessName}</p>															
													<p class="timeLeft text-center"> Time Left : <span id="itemsCountdown${closedAuction.itemId}" class="countdown_row"> </span> </p>  
													<p class="text-center">Current Bid : </p>			    	
													<p class="text-center text_color_21A5BC"><fmt:formatNumber value="${closedAuction.itemCurrentBidPrice}" type="currency"/> </p>			    	
													<p class="text-center"> <a href="${pageContext.request.contextPath}/itemDetails/${closedAuction.itemId}" class="btn btn-primary">Details <span class="rarrow">&#9658;</span> </a>	</p>
										 </div>
										</div>
									</div>
							</div>		
						</c:forEach>
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
							  		  	Showing ${userData.closedBeginRecord+1}-${userData.closedEndRecord+1} of ${fn:length(userData.closedItems)}
									  </div>
									  
							  		  <div class="col-sm-4 col-md-3 text-left">
							  		  <form name="itemsPerpageForm" action="${pageContext.request.contextPath}/closedAuctionPaginationItemsPerPage"  method="POST">
								  		  Items per page <select name="closedItemsPerPage" onchange="this.form.submit()">
												
												<c:if test="${userData.closedRecordsPerPage == 6}">
													<option value="6" selected="selected">6</option>	
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage == 9}">
													<option value="9" selected="selected">9</option>
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage == 12}">
													<option value="12" selected="selected">12</option>
												</c:if>
												
												
												<c:if test="${userData.closedRecordsPerPage != 6}">
													<option value="6">6</option>	
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage != 9}">
													<option value="9">9</option>
												</c:if>
												
												<c:if test="${userData.closedRecordsPerPage != 12}">
													<option value="12">12</option>
												</c:if>
											
											</select>
									</form>				  		  
									  </div>
									  
							  
								  		<div class="col-sm-4 col-md-5 text-right">
										
								  
											<%--For displaying Previous link except for the 1st page --%>
										    <c:if test="${userData.closedCurrentPage != 1}">
										        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${userData.closedCurrentPage - 1}"><span class="glyphicon glyphicon-backward"></span> Previous</a>
										    </c:if>
										 
										    <%--For displaying Page numbers.
										    The when condition does not display a link for the current page--%>
										            <c:forEach begin="1" end="${userData.closedTotalPages}" var="i">
										                <c:choose>
										                    <c:when test="${userData.closedCurrentPage eq i}">
										                        ${i}
										                    </c:when>
										                    <c:otherwise>
										                        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${i}">${i}</a>
										                    </c:otherwise>
										                </c:choose>
										            </c:forEach>
		 
										    <%--For displaying Next link --%>
										    <c:if test="${userData.closedCurrentPage lt userData.closedTotalPages}">
										        <a href="${pageContext.request.contextPath}/closedAuctionPagination/?pageNo=${userData.closedCurrentPage + 1}">Next <span class="glyphicon glyphicon-forward"></span></a>
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
	
<script type="text/javascript">
   <c:forEach items="${userData.closedItems}" var="item">
		CountDownTimer('itemsCountdown${item.itemId}','<fmt:formatDate  type="both" dateStyle="short" timeStyle="short" value="${item.itemFinalTimeToBid}" />');
	</c:forEach>
</script>

</body>
</html>