


function doAjaxItemBidCall(urlTemp) {
     // get the form values
    // var name = $('#name').val();
     //var education = $('#education').val();
	
		//alert(urlTemp);
	
	var newBidValue = document.getElementById('newBidValue').value;
	if(newBidValue=='')
		{
			alert("Please Enter new Bid price!");
		}
	else
		{

     $.ajax({
     url: ""+urlTemp,
     data: "newBidValue=" + newBidValue,
     success: function(response){

			
      $('#bidResult').html(response);
	 	  
	  
	  //alert($('#bidResult').text().indexOf("Successfully!"))
	  if ($('#bidResult').text().indexOf("Successfully!") != -1)
		{
			//alert('success');
			$('#bidResult').removeClass('label label-danger');  
			$('#bidResult').addClass('label label-success');  
		}		
		
		
		if($('#bidResult').text().indexOf("Successfully!") == -1)
		{
			//alert('failure');
			$('#bidResult').removeClass('label label-success'); 
			$('#bidResult').addClass('label label-danger');  		
		}
	  
		$("#current-bid").toggleClass("text_color_flash_green");
      doAjaxItemBidPriceCheck(urlTemp.replace("itemBidClicked","itemBidPriceCheck"));	  
	  setTimeout(function() {
        $('#current-bid').toggleClass("text_color_flash_green");}, 1000);
  		
     },
     error: function(e){
     alert('Error got : ' + e);
     }
     });
     
     }
}
	
	



function doAjaxItemBidPriceCheck(urlTemp) {
    // get the form values
   // var name = $('#name').val();
    //var education = $('#education').val();
	
		//alert(urlTemp);

    $.ajax({
    url: ""+urlTemp,     
    success: function(response){
    // we have the response
   // $('#info').html(response);
    //$('#name').val('');
    //$('#education').val('');
     //$('#current-bid').css("background-color","red");
     $('#current-bid').html(response);
     //$('#current-bid').css("background-color","transparent");
	 	      
    },
    error: function(e){
    alert('Error got : ' + e);
    }
    });
    }



function fillBillingAndCardDetails()
{
	
	
	
}


function shippingAddressCheckedOrUnchecked(blnchecked)
{
if(blnchecked)
{
	document.getElementById("customerShippingAddress.companyName").value = document.getElementById("customerBillingAddress.companyName").value;
	document.getElementById("customerShippingAddress.addressContactPhoneNumber").value = document.getElementById("customerBillingAddress.addressContactPhoneNumber").value;
	document.getElementById("customerShippingAddress.addressLine1").value = document.getElementById("customerBillingAddress.addressLine1").value;
	document.getElementById("customerShippingAddress.addressLine2").value = document.getElementById("customerBillingAddress.addressLine2").value;
	document.getElementById("customerShippingAddress.city").value = document.getElementById("customerBillingAddress.city").value;
	document.getElementById("customerShippingAddress.state").value = document.getElementById("customerBillingAddress.state").value;
	document.getElementById("customerShippingAddress.postalZipcode").value = document.getElementById("customerBillingAddress.postalZipcode").value;
	document.getElementById("customerShippingAddress.country").value = document.getElementById("customerBillingAddress.country").value;
	
	
	document.getElementById("customerShippingAddress.companyName").disabled=true;
	document.getElementById("customerShippingAddress.addressContactPhoneNumber").disabled=true;
	document.getElementById("customerShippingAddress.addressLine1").disabled=true;
	document.getElementById("customerShippingAddress.addressLine2").disabled=true;
	document.getElementById("customerShippingAddress.city").disabled=true;
	document.getElementById("customerShippingAddress.state").disabled=true;
	document.getElementById("customerShippingAddress.postalZipcode").disabled=true;
	document.getElementById("customerShippingAddress.country").disabled=true;	
	
} 
else
{
	document.getElementById("customerShippingAddress.companyName").disabled=false;
	document.getElementById("customerShippingAddress.addressContactPhoneNumber").disabled=false;
	document.getElementById("customerShippingAddress.addressLine1").disabled=false;
	document.getElementById("customerShippingAddress.addressLine2").disabled=false;
	document.getElementById("customerShippingAddress.city").disabled=false;
	document.getElementById("customerShippingAddress.state").disabled=false;
	document.getElementById("customerShippingAddress.postalZipcode").disabled=false;
	document.getElementById("customerShippingAddress.country").disabled=false;
	
	
	
	document.getElementById("customerShippingAddress.companyName").value = "";
	document.getElementById("customerShippingAddress.addressContactPhoneNumber").value = "";
	document.getElementById("customerShippingAddress.addressLine1").value = "";
	document.getElementById("customerShippingAddress.addressLine2").value = "";
	document.getElementById("customerShippingAddress.city").value = "";
	document.getElementById("customerShippingAddress.state").value = "";
	document.getElementById("customerShippingAddress.postalZipcode").value = "";
	document.getElementById("customerShippingAddress.country").value = "NONE";
}

}




function passwordStrength(password)

{

        var desc = new Array();

        desc[0] = "Weak";

        desc[1] = "Medium";

        desc[2] = "Strong";

        desc[3] = "Excellent";


        var score   = 0;



        //if password bigger than 6 give 1 point

        if (password.length > 6) score++;



        //if password has both lower and uppercase characters give 1 point      
        if ( ( password.match(/[a-z]/) ) && ( password.match(/[A-Z]/) ) ) score++;



        //if password has at least one number give 1 point
        if (password.match(/\d+/)) score++;



        //if password has at least one special caracther give 1 point
        //if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;



        //if password bigger than 12 give another 1 point
        //if (password.length > 12) score++;
        
        //if password bigger than 20 give another 1 point
        //if (password.length > 20) score++;



         document.getElementById("passwordDescription").innerHTML = desc[score];

         document.getElementById("passwordStrength").className = "strength" + score;

}

