<!DOCTYPE html>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Cherry Sports Bids:Item Manager</title>

<!-- Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">


<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/datetimepicker_css.js"></script>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/custom.css">


</head>
<body>

	<jsp:include page="HeaderPage.jsp" />

	<div class="container">


		<div class="row">

			<div class="col-lg-12 col-xs-12">
			
				<div class="panel panel-default">
				 <div class="panel-body">



				<h4 class="text-left">Add new Auction</h4>


				<form:form method="post" commandName="ITEM_MANAGER"
					action="addItemSubmit" enctype="multipart/form-data" class="form-horizontal" role="form">
					
						<form:errors path="*">								
						<div class="alert alert-danger text-left">					
							<form:errors path="*"/>	
						</div>
					</form:errors>
						

						<div class="form-group">		
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Name</label>
							<div class="col-sm-3"><form:input path="itemName"
									maxlength="100" class="form-control input-sm" placeholder="Enter Item Name" />
									</div>
						</div>	
						
						<div class="form-group">	
							<label class="col-sm-2 control-label"><span
								class="requiredField">*</span>Description <br /> <small>
								(maximum 900 characters)</small></label>
							<div class="col-sm-8">
							<form:textarea
									path="itemDescription" rows="8" cols="70" maxlength="900"  class="form-control input-sm" placeholder="Enter Description" />
									</div>
						</div>	
						
						<div class="form-group">	
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Price</label>
							<div class="col-sm-2">
							<form:input path="itemPrice"
									maxlength="7" class="form-control input-sm" placeholder="Enter price"/>
									</div>
						</div>	
						
							<div class="form-group">		
							<label class="col-sm-2 control-label">Number of Items</label>
							<div class="col-sm-2">
							<form:input
									path="itemQuantityAvailable" maxlength="7"   class="form-control input-sm" placeholder="Enter Number of Items"/>
									</div>
						</div>	
						
						
						<div class="form-group">		
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Time
								Left To Bid</label>
							
							<div class="col-md-6">
							
								<div class="row">
										
										<div class="col-md-4">
											<form:input path="itemFinalTimeToBid"
									id="demo1" maxlength="25" size="25"  class="form-control input-sm" placeholder="Enter Time Left To Bid"/> 
										
										</div>
										
										<div class="col-md-1 text-left">
										
											<img
									src="${pageContext.request.contextPath}/img/cal/cal.gif"
									onclick="javascript:NewCssCal('demo1','MMddyyyy','arrow',true,'24','','future')"
									style="cursor: pointer" /> 
										
										</div>

										<div class="col-md-4 text-left">
										
											<form:select path="itemFinalTimeToBidTimezone"  class="form-control input-sm" >
												<form:option value="NONE" label="TIMEZONE" />
												<form:options items="${userData.timeZoneList}" />
											</form:select>
										
										</div>
								
								</div>
							</div>	
							</div>
							
							
							
							
						<div class="form-group">	
						
							<label class="col-sm-2 control-label"><span class="requiredField">*</span>Select
								Image(s) to upload</label>
								
								<div class="col-sm-3">
							<input name="files[0]" type="file" class="form-control input-sm"/>
						

						
							
							<input name="files[1]" type="file" class="form-control input-sm" />
							
							
							<input name="files[2]" type="file" class="form-control input-sm" />
							
							
							
							<input name="files[3]" type="file" class="form-control input-sm" />
							

						
							<input name="files[4]" type="file" class="form-control input-sm" />
							
							</div>
						
						</div>


						<div class="text-right">
						
							<span class="generalInstruc">All
									the fields marked with <span class="requiredField">*</span> are
									required!
							</span>
						</div>	
							
							<div class="text-center">
							<input type="submit"
								value="Add Auction" />
							</div>	
								
				</form:form>
			
			</div> <!-- END OF PANEL BODY -->
		</div> <!-- END OF PAENL -->
				
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
