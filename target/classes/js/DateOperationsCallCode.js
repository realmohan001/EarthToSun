

/*function CountDownTimer(className, coountDownToDate) 
			{
					//alert(coountDownToDate);						
					$("."+className).jCounter({
						date: ""+coountDownToDate, //format: DD month YYYY HH:MM:SS
						format: "dd:hh:mm:ss",
						twoDigits: 'on',
						callback: function() {
							if(className == 'countdownTimeLeft') { $('#bid-button').prop("disabled", true); };
						}
					});
					
						$("."+className).jCounter('start');
			}
*/

function CountDownTimer(className, coountDownToDate) 
			{
					//alert(coountDownToDate);
					$("#"+className).countdown({until: new Date(coountDownToDate), timezone: -0, format: 'dHMS', onExpiry: function() {  countExpired(className); } }); 
			}
	

function countExpired(className)
{
	//alert(className);
	if(className == 'countdownTimeLeft') 
	{ 
		//alert(className);
		document.getElementById("bidNowButton").disabled=true;
	}
}