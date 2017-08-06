<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html>

<head>
  <meta charset="utf-8">
<title>Cherry Sports Bids:Home Page</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
	 <script src="${pageContext.request.contextPath}/js/respond.min.js"></script>



</head>
<body>

<jsp:include page="HeaderPage.jsp" />
		
		<div class="container">
			  <div class="jumbotron">
					<div class="row">
					
						<div class="col-md-4 col-sm-4">
							<img src="${pageContext.request.contextPath}/img/Cherry-Bids-Logo6.png" BORDER="0" alt="my image" title="${bidItem.itemName}" class="img_heigt_jumbotron center-block img-responsive"/>								
						</div>
						
						
						<div class="col-md-8 col-sm-8">
						
							<h1 class="text_color_21A5BC"> Cherry Sports Bids</h1>
							<p class="text_color_21A5BC">Bid your favorite sports items directly from the foundation</p>
							<c:if test="${empty userData.customer}"> 
								<a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/RegisterCustomer.htm">Register Now!</a>
							</c:if>
						
						</div>
						
					</div>
		        	
   			 </div>
   		</div>
		
		

 <div class="container"> 
 
  <div class="row">
            <div class="col-sm-10 col-md-10 col-lg-8">   
			
				<div class="panel panel-info">
					
					<div class="panel-heading">
						<h2 class="panel-title"><strong>Featured Bids</strong></h2>
					</div>
				<div class="panel-body">	
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
				
				  <!-- Indicators -->
				  <ol class="carousel-indicators">
				  
					   <c:forEach items="${userData.allItems}" var="bidItem"  varStatus="theCount" begin="0" end="4">  
							<c:if test="${theCount.index == 0 }">	
								<li data-target="#myCarousel" data-slide-to="${theCount.index}" class="active"></li>
							</c:if>
							<c:if test="${theCount.index != 0 }">	
								<li data-target="#myCarousel" data-slide-to="${theCount.index}"></li>
							</c:if>
						</c:forEach>     
				  </ol>

				  <!-- Wrapper for slides -->
				  <div class="carousel-inner">		
						  <c:forEach items="${userData.allItems}" var="bidItem"  varStatus="theCount"  begin="0" end="4">			
									   <c:if test="${theCount.index == 0 }">	
											<div class="item active">
										</c:if>
										
										<c:if test="${theCount.index != 0 }">	
											<div class="item">
										</c:if>
											<div class="row">
											
												<div class="col-sm-1">
												</div>
												<div class="col-sm-5">
													<img src="https://s3.amazonaws.com/CherryBids/${bidItem.itemImageLocation1}" BORDER="0" alt="my image" title="${bidItem.itemName}" class="img-thumbnail center-block img-responsive img_height_carousel"/>	
												</div>
												
												<div class="col-sm-5">
													<p> <h3 class="text_color_21A5BC text-center"> ${bidItem.itemName} </h3></p>
													<p class="text-center"> Foundation Name : <span class="text_color_21A5BC"> ${bidItem.businessName} </span> </p>
														
													<p class="timeLeft text-center"> Time Left : <span id="carouselCountdown${bidItem.itemId}" class="countdown_row"> </span> </p>  
													<p class="text-center">Current Bid : <span class="text_color_21A5BC"> <fmt:formatNumber value="${bidItem.itemCurrentBidPrice}" type="currency"/> </span> </p>			    	
													<p class="text-center"> <a href="${pageContext.request.contextPath}/itemDetails/${bidItem.itemId}" class="btn btn-primary">Details <span class="rarrow">&#9658;</span> </a>	</p>
												</div>
												<div class="col-sm-1">
												</div>
												
											</div>						
									</div>			
							</c:forEach>
				  </div> <!-- END OF CAROUSEL-INNER -->

				  <!-- Controls -->
						  <a class="left carousel-control" href="#myCarousel" data-slide="prev"> <img src="${pageContext.request.contextPath}/img/autoscroll-horizontal-prev.png" class="center-block"/> </a>
						  <a class="right carousel-control" href="#myCarousel" data-slide="next"> <img src="${pageContext.request.contextPath}/img/autoscroll-horizontal-next.png" class="center-block"/></a>
				</div><!-- END OF CAROUSEL -->
				</div> <!-- end of panel body -->
				</div> <!-- END OF PANEL -->
			</div> <!-- END OF COLUMN -->

        </div><!-- END OF CAROUSEL ROW -->
        
       <!-- PAGINATION LINKS HEADER -->
        <div class="row">
			<div class="col-sm-10 col-md-10 col-lg-8"> 
				<div class="panel panel-default panel_background">
				  <div class="panel-body">
				  
						   <div class="row">
							  
							  		  <div class="col-sm-4 col-md-4 text-left">
							  		  	Showing ${userData.beginRecord+1}-${userData.endRecord+1} of ${fn:length(userData.allItems)}
									  </div>
									  
							  		  <div class="col-sm-4 col-md-3 text-left">
							  		  <form name="itemsPerpageForm" action="${pageContext.request.contextPath}/itemPaginationItemsPerPage"  method="POST">
								  		  Items per page <select name="itemsPerPage" onchange="this.form.submit()">
												
												<c:if test="${userData.recordsPerPage == 6}">
													<option value="6" selected="selected">6</option>	
												</c:if>
												
												<c:if test="${userData.recordsPerPage == 9}">
													<option value="9" selected="selected">9</option>
												</c:if>
												
												<c:if test="${userData.recordsPerPage == 12}">
													<option value="12" selected="selected">12</option>
												</c:if>
												
												
												<c:if test="${userData.recordsPerPage != 6}">
													<option value="6">6</option>	
												</c:if>
												
												<c:if test="${userData.recordsPerPage != 9}">
													<option value="9">9</option>
												</c:if>
												
												<c:if test="${userData.recordsPerPage != 12}">
													<option value="12">12</option>
												</c:if>
											
											</select>
									</form>				  		  
									  </div>
									  
							  
								  		<div class="col-sm-4 col-md-5 text-right">
										
								  
											<%--For displaying Previous link except for the 1st page --%>
										    <c:if test="${userData.currentPage != 1}">
										        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${userData.currentPage - 1}"><span class="glyphicon glyphicon-backward"></span> Previous</a>
										    </c:if>
										 
										    <%--For displaying Page numbers.
										    The when condition does not display a link for the current page--%>
										            <c:forEach begin="1" end="${userData.totalPages}" var="i">
										                <c:choose>
										                    <c:when test="${userData.currentPage eq i}">
										                        ${i}
										                    </c:when>
										                    <c:otherwise>
										                        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${i}">${i}</a>
										                    </c:otherwise>
										                </c:choose>
										            </c:forEach>
		 
										    <%--For displaying Next link --%>
										    <c:if test="${userData.currentPage lt userData.totalPages}">
										        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${userData.currentPage + 1}">Next <span class="glyphicon glyphicon-forward"></span></a>
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
						 <c:forEach items="${userData.allItems}" var="bidItem"  varStatus="theCount" begin="${userData.beginRecord}" end="${userData.endRecord}">		
							<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12">
								  <div class="thumbnail">
										<div class="caption">
										  <div class="thumbnail_heigt_pagination">										
											<img src="https://s3.amazonaws.com/CherryBids/${bidItem.itemImageLocation1}" BORDER="0" alt="my image" title="${bidItem.itemName}" class="center-block img-thumbnail img-responsive img_height_paginationlist"/>
													<p class="text-center text_color_21A5BC"><a href="${pageContext.request.contextPath}/itemDetails/${bidItem.itemId}">  ${bidItem.itemName} </a> </p>
													<p class="text-center"> Foundation Name :</p>		
													<p class="text-center text_color_21A5BC"> ${bidItem.businessName}</p>															
													<p class="timeLeft text-center"> Time Left : <span id="itemsCountdown${bidItem.itemId}" class="countdown_row"> </span> </p>  
													<p class="text-center">Current Bid : </p>			    	
													<p class="text-center text_color_21A5BC"><fmt:formatNumber value="${bidItem.itemCurrentBidPrice}" type="currency"/> </p>			    	
													<p class="text-center"> <a href="${pageContext.request.contextPath}/itemDetails/${bidItem.itemId}" class="btn btn-primary">Details <span class="rarrow">&#9658;</span> </a>	</p>
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
							  		  	Showing ${userData.beginRecord+1}-${userData.endRecord+1} of ${fn:length(userData.allItems)}
									  </div>
									  
							  		  <div class="col-sm-4 col-md-5 text-left">
							  		  <form name="itemsPerpageForm" action="${pageContext.request.contextPath}/itemPaginationItemsPerPage"  method="POST">
								  		  Items per page <select name="itemsPerPage" onchange="this.form.submit()">
												
												<c:if test="${userData.recordsPerPage == 6}">
													<option value="6" selected="selected">6</option>	
												</c:if>
												
												<c:if test="${userData.recordsPerPage == 9}">
													<option value="9" selected="selected">9</option>
												</c:if>
												
												<c:if test="${userData.recordsPerPage == 12}">
													<option value="12" selected="selected">12</option>
												</c:if>
												
												
												<c:if test="${userData.recordsPerPage != 6}">
													<option value="6">6</option>	
												</c:if>
												
												<c:if test="${userData.recordsPerPage != 9}">
													<option value="9">9</option>
												</c:if>
												
												<c:if test="${userData.recordsPerPage != 12}">
													<option value="12">12</option>
												</c:if>
												
																
											</select>
									</form>				  		  
									  </div>
									  
							  
								  		<div class="col-sm-4 col-md-3 text-right">
										
											<%--For displaying Previous link except for the 1st page --%>
										    <c:if test="${userData.currentPage != 1}">
										        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${userData.currentPage - 1}"><span class="glyphicon glyphicon-backward"></span> Previous</a>
										    </c:if>
										 
										    <%--For displaying Page numbers.
										    The when condition does not display a link for the current page--%>
										            <c:forEach begin="1" end="${userData.totalPages}" var="i">
										                <c:choose>
										                    <c:when test="${userData.currentPage eq i}">
										                        ${i}
										                    </c:when>
										                    <c:otherwise>
										                        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${i}">${i}</a>
										                    </c:otherwise>
										                </c:choose>
										            </c:forEach>
		 
										    <%--For displaying Next link --%>
										    <c:if test="${userData.currentPage lt userData.totalPages}">
										        <a href="${pageContext.request.contextPath}/itemPagination/?pageNo=${userData.currentPage + 1}">Next <span class="glyphicon glyphicon-forward"></span></a>
										    </c:if>						  
											
								  		</div>
							  </div>
		  		  </div><!-- end of panel body -->
				</div>	<!-- END OF PANEL -->	
			</div>	<!-- END OF COLUMN -->
		</div><!-- END OF ROW -->
		
		
		
		
		
		
		
 </div> <!-- END OF CONTAINER -->
 
 
	<jsp:include page="FooterDesktopPage.jsp" />


   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
   <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
   
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/jquery.countdown.css">
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.countdown.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/DateOperationsCallCode.js"></script>
	
	<script type="text/javascript">
		<c:forEach items="${userData.allItems}" var="item">
			CountDownTimer("carouselCountdown${item.itemId}",'<fmt:formatDate  type="both" dateStyle="short" timeStyle="short" value="${item.itemFinalTimeToBid}"/>');
		</c:forEach>
		
		<c:forEach items="${userData.allItems}" var="item">
			CountDownTimer("itemsCountdown${item.itemId}",'<fmt:formatDate  type="both" dateStyle="short" timeStyle="short" value="${item.itemFinalTimeToBid}"/>');
		</c:forEach>

		$('.carousel').carousel();
		
	</script>

</body>
</html>