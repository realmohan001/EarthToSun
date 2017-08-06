<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<nav class="navbar navbar-default navbar-fixed-top" role="navigation">

<div class="container">

          <div class="navbar-header">
          
          	 <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex1-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>           
           
		  </div>		  		  
		 	  
		  
		            <!-- Collect the nav links, forms, and other content for toggling -->
          <div class="collapse navbar-collapse" id="navbar-ex1-collapse">
            <ul class="nav navbar-nav">
              <li><a href="${pageContext.request.contextPath}/"><span class="glyphicon glyphicon-home"></span> Home</a></li>              
  			  <c:if test="${not empty userData.customer}">
				<c:if test="${userData.customer.customerType == 'USER'}">
					   <li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">My Bids <b class="caret"></b></a>
							<ul class="dropdown-menu">
							  <li><a href="${pageContext.request.contextPath}/MyPlacedBidsPage">My Placed Bids</a></li>
							  <li><a href="${pageContext.request.contextPath}/ClosedAuctionsPage">Recently Closed Auctions</a></li>
							  <li><a href="#">Upcoming Auctions</a></li>
							</ul>
					  </li>
				</c:if>
			  </c:if>
              			  
			  
			  <c:if test="${not empty userData.customer}">
				<c:if test="${userData.customer.customerType == 'BUSINESS'}">

				   <li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">My Auctions <b class="caret"></b></a>
						<ul class="dropdown-menu">
						  <li><a href="${pageContext.request.contextPath}/addItem"> Add New Auctions </a></li>
						  <li><a href="${pageContext.request.contextPath}/ModifyItems"> Modify Auctions </a> </li>
						  <li><a href="${pageContext.request.contextPath}/ClosedAuctionsPage"> My closed Auctions </a> </li>						 
						</ul>
				  </li>
				</c:if>
			  </c:if>
              
           
            </ul>
            <form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/searchController"  method="POST">
              <div class="form-group">
                <input type="text" name="searchWord" class="form-control" placeholder="Search"/>
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
            
               <c:if test="${empty userData.customer}">             
	            <ul class="nav navbar-nav navbar-right">
	              <li><a href="${pageContext.request.contextPath}/LoginCustomer.htm"><span class="glyphicon glyphicon-log-in"></span> Sign On</a></li>
	             <li><a href="${pageContext.request.contextPath}/RegisterCustomer.htm"><span class="glyphicon glyphicon-registration-mark"></span> Register Now</a></li>
	            </ul>
            </c:if>
            
            
            <c:if test="${not empty userData.customer}">
            	<ul class="nav navbar-nav navbar-right">
					<li><a>Welcome <span class="glyphicon glyphicon-user"></span> ${userData.customer.firstName }</a></li>
					<li><a href="${pageContext.request.contextPath}/AddressDetails.htm">Account</a> </li> 
					<li><a href="${pageContext.request.contextPath}/HelpCustomer.htm"><span class="glyphicon glyphicon-info-sign"></span>Help </a> </li>
					<li> <a href="<c:url value="${pageContext.request.contextPath}/sign-out" />"><span class="glyphicon glyphicon-log-out"></span>Log Out </a> </li>
				</ul>	
			</c:if>
            
          </div><!-- /.navbar-collapse -->
          
          </div> <!-- END OF CONTAINER DIV -->	
          
</nav>


