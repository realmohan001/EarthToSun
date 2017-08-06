<!DOCTYPE html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>

<title>Cherry Sports Bids:Auction Detail</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">




<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/ajaxCallCode.js"></script>




</head>
<body>

	<jsp:include page="HeaderPage.jsp" />

	<div class="container">


	 <div class="row">

		<div class="col-sm-4 col-md-4 col-xs-12">
		
		<div class="panel panel-default">
			<div class="panel-body">	

			<div class="row">
				<div class="col-sm-12 col-md-12 col-xs-12">
					<h2 class="text-center">${SELECTEDITEM.itemName}</h2>
				</div>
			</div>
			
			<div class="row">
				<div class="col-sm-12 col-md-12 col-xs-12">
				
					<div id="mainBigImmageDiv" class="text-center">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation1}"
							 alt="image"
							title="${SELECTEDITEM.itemName}" class="mainImage center-block img-thumbnail img-responsive img_height_itemdetail"
							id="mainBigImage" />
					</div>
				</div>
			</div>	


			<div class="row">
				<div class="col-sm-4 col-md-4 col-xs-4">
					<c:if test="${not empty SELECTEDITEM.itemImageLocation1}">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation1}"
							alt="image"
							title="${SELECTEDITEM.itemName}" class="thumbImage center-block img-thumbnail img-responsive img_height_itemdetailthumbnail"
							onclick="document.getElementById('mainBigImage').src=this.src" />
					</c:if>
				</div>
				
				<div class="col-sm-4 col-md-4 col-xs-4">				
					<c:if test="${not empty SELECTEDITEM.itemImageLocation2}">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation2}"
							alt="image"
							title="${SELECTEDITEM.itemName}" class="thumbImage center-block img-thumbnail img-responsive img_height_itemdetailthumbnail"
							onclick="document.getElementById('mainBigImage').src=this.src" />
					</c:if>
				</div>	

				<div class="col-sm-4 col-md-4 col-xs-4">				

					<c:if test="${not empty SELECTEDITEM.itemImageLocation3}">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation3}"
							alt="image"
							title="${SELECTEDITEM.itemName}" class="thumbImage center-block img-thumbnail img-responsive img_height_itemdetailthumbnail"
							onclick="document.getElementById('mainBigImage').src=this.src" />
					</c:if>
				</div>
				
			</div> <!-- END OF ROW -->
				
				
			<div class="row">	
				<div class="col-sm-4 col-md-4 col-xs-4">
					<c:if test="${not empty SELECTEDITEM.itemImageLocation4}">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation4}"
							alt="image"
							title="${SELECTEDITEM.itemName}" class="thumbImage center-block img-thumbnail img-responsive img_height_itemdetailthumbnail"
							onclick="document.getElementById('mainBigImage').src=this.src" />
					</c:if>
				</div>
				
				<div class="col-sm-4 col-md-4 col-xs-4">
					<c:if test="${not empty SELECTEDITEM.itemImageLocation5}">
						<img
							src="https://s3.amazonaws.com/CherryBids/${SELECTEDITEM.itemImageLocation5}"
							alt="image"
							title="${SELECTEDITEM.itemName}" class="thumbImage center-block img-thumbnail img-responsive img_height_itemdetailthumbnail"
							onclick="document.getElementById('mainBigImage').src=this.src" />
					</c:if>
				</div>
				
				<div class="col-sm-4 col-md-4 col-xs-4  text-center">
				</div>
				
				
			</div> <!-- END OF ROW -->
			
			</div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->
			
		</div> <!-- END OF COLUMN -->
		
		
		

			<div class="col-sm-8 col-md-8 col-xs-12"> <!-- NEXT COLUMN -->
			
			<div class="panel panel-default">
				<div class="panel-body">	
				
				
				<jsp:useBean id="now" class="java.util.Date" />
				<fmt:formatDate value="${now}" timeZone="UTC" type="both"
					dateStyle="long" var="nowUTC" pattern="yyyy-MM-dd HH:mm" />			

					<c:if test="${SELECTEDITEM.itemFinalTimeToBid gt nowUTC  }">
					
							<div class="row">
								<div class="col-md-12 col-xs-12  text-center">
									<h3>Bid Ends</h3>
								</div>
							</div>		
								
								<div class="row">
									<div class="col-md-12 col-xs-12  text-center">							
										<div id="countdownTimeLeft" class="countdown_row"></div>
									</div>
								</div>		
													
								<div class="row">
									<div class="col-md-12 col-xs-12  text-center">				
										<div id="bidResult" class="label label-info">
												Start Bidding Now!
										</div>
									</div>
								</div>
								
							<div class="row  text-center">
								<div class="col-md-12 col-xs-12">
								&nbsp;
								</div>
							</div>			

								<div class="row">
									<div class="col-md-12 col-xs-12  text-center">	
										Current Bid: <span id="current-bid"> <fmt:formatNumber value="${SELECTEDITEM.itemCurrentBidPrice}" type="currency" /></span>
									</div>
								</div>	
								<div class="row  text-center">
									<div class="col-md-12 col-xs-12">
										&nbsp;
									</div>
								</div>			
									
					</c:if>


					<c:if test="${SELECTEDITEM.itemFinalTimeToBid lt nowUTC  }">
								<div class="row">
									<div class="col-md-12 col-xs-12  text-center">
										<h3>Bid Ended at</h3>
									</div>
								</div>
								
								<div class="row text-center">
									<div class="col-md-12 col-xs-12">
										&nbsp;
									</div>
								</div>			
								
								
							<div class="row">
									<div class="col-md-12 col-xs-12 text-center">
					
										<!-- <fmt:formatDate value="${SELECTEDITEM.itemFinalTimeToBid}" timeZone="EST" type="both" dateStyle="long" var="itemEST" />  -->
										<c:out value="${SELECTEDITEM.tempItemFinalTimeToBid}" /> <c:out
											value=" EST/EDT" />
									</div>
							</div>	
							
							<div class="row  text-center">
									<div class="col-md-12 col-xs-12">
										&nbsp;
									</div>
								</div>				
								
							<div class="row">
								<div class="col-md-12 col-xs-12 text-center">	
									Winning Bid: <span
											id="current-bid"> <fmt:formatNumber
													value="${SELECTEDITEM.itemCurrentBidPrice}" type="currency" />
										</span>
							    </div>
							</div>
							
							<div class="row text-center">
									<div class="col-md-12 col-xs-12">
										&nbsp;
									</div>
								</div>				
							
					</c:if>
					
					
							<div class="row">
								<div class="col-md-12 col-xs-12 text-center">	
									<div id="bidAmount">
										Enter new Bid Value: <strong><span class="glyphicon glyphicon-usd"></span></strong> <input type="text" id="newBidValue"
											name="newBidValue" size="6" maxlength="6" /><strong><small>.00</small></strong>
									</div>
								</div>
							</div>	
							
								<div class="row text-center">
									<div class="col-md-12 col-xs-12">
										&nbsp;
									</div>
								</div>				
								

							<c:if test="${ empty userData.customer.customerPaymentInformations}">								
								<div class="row">
									<div class="col-md-12 col-xs-12 text-center">	
										<div id="bid-button">
										<a href="#"
											onclick="location.href='${pageContext.request.contextPath}/fillBillingAndCardDetails.htm'"
											class="btn btn-primary btn-sm"> Place Bid <span class="rarrow">&#9658;</span></a>
									</div>
									</div>
								</div>	
							</c:if> 
							
							
							
							
							<c:if test="${not empty userData.customer.customerPaymentInformations}">
							
								<div class="row">
									<div class="col-md-12 col-xs-12  text-center">		

														<!-- <c:out value="${nowUTC}"/>
											<c:out value="${SELECTEDITEM.itemFinalTimeToBid}"/>  
											  -->

											<c:if test="${SELECTEDITEM.itemFinalTimeToBid gt nowUTC  }">
			
												
													<div id="bid-button">
														<!-- <a href="" onclick="doAjaxItemBidCall('${pageContext.request.contextPath}/itemBidClicked/${SELECTEDITEM.itemId}')" class="btn btn-primary btn-sm"> Place Bid <span class="rarrow">&#9658;</span></a>   -->
														<input type="button" id="bidNowButton" value="Place Bid"
															onclick="doAjaxItemBidCall('${pageContext.request.contextPath}/itemBidClicked/${SELECTEDITEM.itemId}')" class="btn btn-primary btn-sm"/>
													</div>
											</c:if>
			
			
											<c:if test="${SELECTEDITEM.itemFinalTimeToBid lt nowUTC }">
												
													<div id="bid-button">
														<!-- <a href="" onclick="doAjaxItemBidCall('${pageContext.request.contextPath}/itemBidClicked/${SELECTEDITEM.itemId}')" class="buttonClass"> Place Bid <span class="rarrow">&#9658;</span></a>   -->
														<input type="button" id="bidNowButton" value="Place Bid"
															onclick="" disabled="disabled" />
													</div>
											</c:if>
									</div> <!-- END OF COLUMN -->			
								</div><!-- END OF ROW -->
								
								
							</c:if>
							
							</div> <!-- END OF PANEL BODY -->
						</div> <!-- END OF PAENL -->
							
							
					</div> <!-- END OF COLUMN -->

		</div><!-- END OF ROW -->


			
		<div class="row"> <!--  ROW FOR TABS -->

			<div class="col-sm-4 col-md-4 col-xs-12"></div>

			<div class="col-sm-8 col-md-8 col-xs-12">
			
				<div class="panel panel-default">
					<div class="panel-body">	
				
					 <ul class="nav nav-tabs">
		                <li><a href="#itemDescription" data-toggle="tab">Description</a></li>
		                <li><a href="#itemShare" data-toggle="tab">Share<span class="glyphicon glyphicon-share"></span></a></li>
		             </ul>
		             
		             <!-- text for each callout -->
		             <!-- Item Description -->
			        <div class="tab-content">
			            <div class="tab-pane fade active in" id="itemDescription">
			             	<p> <pre> ${SELECTEDITEM.itemDescription} </pre></p>
			            </div>
			    
			             <!-- Item Share -->
			            <div class="tab-pane fade" id="itemShare">
			            
									            <span id="socialMediaButtons"> <span id="twitterDiv">
												<a href="http://twitter.com/share?text=An%20Awesome%20Link&url=http://cherrysportsbids.com/itemDetails/${SELECTEDITEM.itemId}">Share This on Twitter</a>
										</span> <span id="facebDiv">
										
										<a href="http://www.facebook.com/sharer.php?u=http://cherrysportsbids.com/itemDetails/${SELECTEDITEM.itemId}">Share This Link on Facebook</a>
						
										</span>
										</span>
			             
			            </div>
			         </div>  
			         
			         </div> <!-- END OF PANEL BODY -->
				</div> <!-- END OF PAENL -->
		          
			</div>


		</div>
		<!-- END OF ROW -->
	</div>
	<!-- END OF CONTAINER -->


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
	
	<!-- SCRIPT TO CALL THE COUNTDOWN TIMER -->
	<script type="text/javascript">
				CountDownTimer(
						'countdownTimeLeft',
						'<fmt:formatDate  type="both" dateStyle="short" timeStyle="short" value="${SELECTEDITEM.itemFinalTimeToBid}" />');
	</script>

	<!-- SCRIPT TO START THE TABS -->
 <script>
        $(function () {
            $('.nav-tabs a:first').tab('show');
        });
    </script>
    
    <script>
		$(document)
			.ready(
					function() {
						setInterval("doAjaxItemBidPriceCheck('${pageContext.request.contextPath}/itemBidPriceCheck/${SELECTEDITEM.itemId}')",10000);
					});
</script>


</body>
</html>