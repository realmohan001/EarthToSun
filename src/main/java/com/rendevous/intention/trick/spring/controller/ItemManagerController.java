package com.rendevous.intention.trick.spring.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.rendevous.intention.trick.spring.model.CustomerBids;
import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.scheduledtasks.ScheduledTasksService;
import com.rendevous.intention.trick.spring.service.BidsManagerService;
import com.rendevous.intention.trick.spring.service.ItemManagerService;
import com.rendevous.intention.trick.spring.util.AwsS3StorageInteraction;
import com.rendevous.intention.trick.spring.util.DateUtil;
import com.rendevous.intention.trick.spring.util.ItemsUtil;
import com.rendevous.intention.trick.spring.util.SmartFileStorageInteraction;
import com.rendevous.intention.trick.spring.validator.ItemManagerValidator;

@Controller
public class ItemManagerController {
	
	//100-----> OPEN FOR BIDDING
	//200----->CLOSED FOR BIDDING. CUSTOMER EMAILED!
	//300-----> BILLING DETAILS EMAILED
	
	
	private static final int DEFAULT_BUFFER_SIZE = 102400;
	private static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
	
	@Autowired
	ServletContext context; 
	
	@Autowired
	private ScheduledTasksService scheduledTasksService;
	
	@Autowired
	private ItemManagerService itemManagerService;
	
	@Autowired
	private BidsManagerService bidsManagerService;
	
	ItemManagerValidator itemManagerValidator;
	
	 @Autowired
	 private UserSessionScopedData userSessionScopedData;
	
	
	public static final String IMAGE_LOCATION="C:/itemImages";
	//public static final String IMAGE_LOCATION="/home/ec2-user/cherryImages";
	 
	 
	//SIZE LIMIT 2 MB
	public static final long FILE_SIZE_LIMIT=2097152;
	
	//ITEM FILE TYPE
	public static final String[] ALLOWED_FILE_TYPES={"jpg", "jpeg", "gif", "png", "tif", "tiff", "bmp","JPG", "JPEG", "GIF", "PNG", "TIF", "TIFF", "BMP"};
		
	@Autowired
	public ItemManagerController(ItemManagerValidator itemManagerValidator){
		this.itemManagerValidator = itemManagerValidator;
	}
	
	
	@RequestMapping(value = "/addItem", method = RequestMethod.GET)
	public String additemGet(@ModelAttribute("ITEM_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors){
 		
		System.out.println("TEST ADDITEM INIT FORM METHOD -------> ");
		
		/*US MAJOR TIME ZONES
		
		EST/EDT
		PST/PDT
		CST/CDT
		MST/MDT
		*/
		
		if(userSessionScopedData!=null && (userSessionScopedData.getTimeZoneList()==null || userSessionScopedData.getTimeZoneList().size()==0) )
		{
			List<String> timeZoneList = new ArrayList<String>();
			timeZoneList.add("EST/EDT");
			timeZoneList.add("PST/PDT");
			timeZoneList.add("CST/CDT");
			timeZoneList.add("MST/MDT");
			
			userSessionScopedData.setTimeZoneList(timeZoneList);
		}
		
		//return form view
		return "addItem";
	}
	
	
	

	@RequestMapping(value = "/addItemSubmit", method = RequestMethod.POST)
	public Object addItemSubmitPost(@ModelAttribute("ITEM_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		
		
		
		itemManagerValidator.validate(item, result);
		
			
		
		if (result.hasErrors()) {
			//if validator failed
			return "addItem";
		} else {
			
			 System.out.println("ITEM FINAL TIME TO BID------before validation--------> "+item.getItemFinalTimeToBid().toString());
				
			
			System.out.println("item.getFiles().size()---------------->"+item.getFiles().size());
			
			boolean foundIssue=false;
			boolean foundAtleastOneItem=false;
			boolean foundItemSizeLimitIssue=false;
			boolean foundInvalidFileTypeIssue=false;
			
			

			
			
			if(item.getFiles()==null || item.getFiles().size()==0)
			{	
				errors.rejectValue("files", "required.itemImage");				
				return "addItem";
			}
			
			if(item.getFiles()!=null && item.getFiles().size()>0)
			{
				MultipartFile multipartFile = null;
				for (int i=0; i<item.getFiles().size(); i++) {
										
	            	 multipartFile = item.getFiles().get(i);
	            	 
	            	 
	            	 System.out.println("file size---> "+multipartFile.getSize());
	            	 System.out.println("original file name---> "+multipartFile.getOriginalFilename());
	            	 System.out.println("file name---> "+multipartFile.getName());
	            	 
	            	 
	            	 
	            	 //FILE UPLOADED OR NOT
	            	 if(multipartFile!=null && multipartFile.getSize()>0)
	            	 {
	            		 foundAtleastOneItem=true;
	              	 }
	            	
	            	 //FOR SIZE CHECK
	            	 if(multipartFile!=null && multipartFile.getSize()>0 && multipartFile.getSize()>FILE_SIZE_LIMIT)
	            	 {
	            		 foundItemSizeLimitIssue=true;
	            	 }
	            	 
	            	//FOR FILE TYPE CHECK
	            	 if(multipartFile!=null && multipartFile.getSize()>0)
	            	 {
	            		 boolean itemTypeNotFound=true;
	            		 for(int k=0; k<ALLOWED_FILE_TYPES.length; k++)
	            		 {//TO CHECK WHETHER THE TYPE IS IN THE ACCEPTED ARRAY FILE TYPES
	            			 if( multipartFile.getOriginalFilename().contains(ALLOWED_FILE_TYPES[k])  )
	            			 {
	            				 itemTypeNotFound=false;
	            			 }
	            		 }
	            		 
	            		 if(itemTypeNotFound)
	            		 {
	            			 foundInvalidFileTypeIssue=true;
	            		 }
	            	 }
				}
			}
			
			
			if(!foundAtleastOneItem)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage");				
				return "addItem";
			}	
			
			if(foundItemSizeLimitIssue)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage.filesize");				
				return "addItem";
			}
			
			if(foundInvalidFileTypeIssue)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage.fileType");				
				return "addItem";
			}
			

			//SET THE ITEM FINAL TIME TO BID TO UTC
			
			//DateUtil.changeTimeZone(inTZ, outTZ, inDateStr, inFormat, outFormat);
			String utcDateTimeString = DateUtil.changeTimeZone(DateUtil.getTimezoneToZoneID(item.getItemFinalTimeToBidTimezone()), DateUtil.getTimezoneToZoneID("UTC"), DateUtil.doDateToString(item.getItemFinalTimeToBid(), "MM/dd/yyyy HH:mm"), "MM/dd/yyyy HH:mm", "MM/dd/yyyy HH:mm");
			System.out.println("converted utcDateTimeString--------------> "+utcDateTimeString);
			
			item.setItemFinalTimeToBid(DateUtil.doStringToDate(utcDateTimeString, "MM/dd/yyyy HH:mm"));
			
			//SET THE ITEM TEMP TIME TO BID IN DESIRED TIMEZONE,
			item.setTempItemFinalTimeToBid(DateUtil.changeTimeZone(DateUtil.getTimezoneToZoneID("UTC"), DateUtil.getTimezoneToZoneID("EST/EDT"), utcDateTimeString, "MM/dd/yyyy HH:mm", "MM/dd/yyyy HH:mm"));
			//DateUtil.changeTimeZone(inTZ, outTZ, inDateStr, inFormat, outFormat);
			
			
			
			List<MultipartFile> files = item.getFiles();
			int fileCounter=0;
				
		         
		        if(null != files && files.size() > 0) {
		            for (int i=0; i<files.size(); i++) {
		            	
		            	MultipartFile multipartFile = files.get(i);
		            	
		            	if(multipartFile!=null && multipartFile.getSize()>0)
		            	{
		            	
		            		
		                    
			            	System.out.println("multipartFile.getSize()------------> "+multipartFile.getSize()); 
			            	
			                
			            	String filename = "filename_"+Calendar.getInstance().getTimeInMillis()+i+".jpg";
			            	
			            	
			            	
			            	try {
			            		
			            		File tempFile = File.createTempFile("multiparToFile", ".tmp");
			                    tempFile.deleteOnExit();
			                    multipartFile.transferTo(tempFile);
			            		
			            		SmartFileStorageInteraction.putObjectToSmartFile(filename, tempFile, multipartFile.getSize());
			            		
								//AwsS3StorageInteraction.putObjectToBucket("CherryBids", filename, multipartFile.getInputStream(), multipartFile.getSize());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            	
			            	//TO FIND VALID GOOD FILES
			            	fileCounter = fileCounter+1;
			                
			                if(fileCounter==1)
			                {
			                	item.setItemImageLocation1(filename);
			                }
			                else if(fileCounter==2)
			            	{
			                	item.setItemImageLocation2(filename);
			            	}
			                else if(fileCounter==3)
			            	{
			                	item.setItemImageLocation3(filename);
			            	}
			                else if(fileCounter==4)
			            	{
			                	item.setItemImageLocation4(filename);
			            	}
			                else if(fileCounter==5)
			            	{
			                	item.setItemImageLocation5(filename);
			            	}		             
			                else
			                {
			                	
			                }
		            	} 
		            }
		        }
		        
		        System.out.println("ITEM FINAL TIME TO BID--------------> "+item.getItemFinalTimeToBid().toString());
		        
		        item.setCustomers(userSessionScopedData.getCustomer());
		        //item.setItemAddedDate(new Date());
		        //SET THE ADDDED TIME TO UTC
		        item.setItemAddedDate(DateUtil.getCurrentDateInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm"));
		        item.setItemCurrentBidPrice(item.getItemPrice());
		        
		        //CHECK ITEM QUANTITY
		        if(item.getItemQuantityAvailable()==null || item.getItemQuantityAvailable().trim().length()==0)
		        {//DEFAULT IS 1
		        	item.setItemQuantityAvailable("1");
		        }
		        
		        //SET ITEM STATUS CODE AND STATUS REASON
		        item.setItemStatusCode("100");
		        item.setItemStatusReason("OPEN FOR BIDDING");
		        
		        //SET THE BUSINESS/FOUNDATION NAME
		        item.setBusinessName(userSessionScopedData.getCustomer().getBusinessName());
		        
				
		        itemManagerService.addItem(item);
		        
		        
		        //UPDATE SESSION LIST WITH THE NEWLY ADDED ITEM.
		        if(userSessionScopedData.getAllItems()==null)
		        {
		        	List<Items> allitemsList = new ArrayList<Items>();
		        	allitemsList.add(item);
		        	userSessionScopedData.setAllItems(allitemsList);
		        }
		        else
		        {
		        	userSessionScopedData.getAllItems().add(item);
		        }
		        
		        //SORT THE LIST IN SESSION
		        userSessionScopedData.setAllItems(ItemsUtil.sortItemsListInSession(userSessionScopedData.getAllItems()));
		        
					//form success
					//return "redirect:addItemSucessRedirect.htm";
		        	return new RedirectView("/addItemSucessRedirect.htm", true);
		        
		}
	}
	
	
	@RequestMapping(value="/addItemSucessRedirect.htm", method = RequestMethod.GET)
	public String processSubmitRedirect(@ModelAttribute("ITEM_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		
		return "addItemSuccess";	
	}
	
	@RequestMapping("/Items")
    public ModelAndView showContacts() {
         
        return new ModelAndView();
    }
	
		
	
	@RequestMapping(value = "/itemDetails/{itemId}", method = RequestMethod.GET)
    public String itemDetails(@PathVariable String itemId, RedirectAttributes redirectAttributes, Model model) {
         
		System.out.println("itemId--->"+itemId);
	
		
		Items item = itemManagerService.getItem(Integer.parseInt(itemId));
		
		if(item!=null)
		{
			System.out.println("------------------------>");
			
			if(item!=null && (item.getItemCurrentBidPrice()==null || item.getItemCurrentBidPrice().trim().length()==0) )
			{//THIS WIL HAPPEN ONLY IF THE ITEM IS NEVER BID BEFORE
				item.setItemCurrentBidPrice(item.getItemPrice());
			}
			
		
			model.addAttribute("SELECTEDITEM", item);			
			userSessionScopedData.setCurrentBiddingItem(itemId);
			
			//redirectAttributes.addFlashAttribute("SELECTEDITEM",item );
			//redirectAttributes.addFlashAttribute("userData", userSessionScopedData);
            //redirectAttributes.ad
			//model.addAttribute("result1", "good!");
		}
		
		return "ItemDetail";
        //return "redirect:/itemDetailsPageRedirect.htm";
    }
	
	
	@RequestMapping(value="/itemDetailsPageRedirect.htm", method=RequestMethod.GET)
	public String itemDetailView(@ModelAttribute("SELECTEDITEM") Items item, BindingResult result, SessionStatus status, Model model)
	{ 
		System.out.println("----inside itemDetailView- redirect------------------->");
		
		model.addAttribute("SELECTEDITEM", item);
		return "ItemDetail";
	}
	
	
	
@RequestMapping(value = "/itemBidClicked/{itemId}", method = RequestMethod.GET)
public @ResponseBody String itemBid(@PathVariable String itemId, @RequestParam(value="newBidValue", required=true) String newBidValue, RedirectAttributes redirectAttributes, Model model ) {
     
	System.out.println("---------------inside Item bid ajax call----itemId-----> "+ itemId);
	
	String outputResponseString = null;
	
	//REMOVE , FROM THE newBidValue
	newBidValue = StringUtils.delete(newBidValue, ",");
	
	//REMOVE . FROM THE newBidValue
	newBidValue = StringUtils.delete(newBidValue, ".");
	
	try	
	{//TO CHECK IF THE VALUE ENTERED IS STRING OR NOT
	Integer.parseInt(newBidValue);
	}
	catch(Exception ex)
	{
		outputResponseString="Please enter valid integer value for new BID Price!";
		return outputResponseString;
	}
	
	
	
	synchronized(this) { //SYNCHRONIZED BLOCK
	
	Items item = itemManagerService.getItem(Integer.parseInt(itemId));
	//ItemsUtil.getItemFromItemsListByID(userSessionScopedData.getAllItems(),itemId);
	
	//CHECK ITEM FINAL TIME TO BID VALUE
	String currentDateStringInSelectedTimezone = DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm");	
	String itemFinalToBid = DateUtil.doDateToString(item.getItemFinalTimeToBid(),"MM/dd/yyyy HH:mm");
	
	//System.out.println("currentDateStringInSelectedTimezone-------> "+currentDateStringInSelectedTimezone);
	//System.out.println("itemFinalToBid-------> "+itemFinalToBid);
	
	
	boolean result = DateUtil.isDate1AfterDate2(itemFinalToBid, currentDateStringInSelectedTimezone);
	
	System.out.println("result-------> "+result);
	
	if(!result)
	{//IF FALSE
		outputResponseString="BID not placed. Allowed time to Bid Ended!";
		return outputResponseString;
	}
	
	//ITEM PRICE CHECK
	if(Integer.parseInt(newBidValue) <= Integer.parseInt(item.getItemCurrentBidPrice()))
	{// TO CHECK IF THE NEW PROCE IS GREATER THAN CURRENT BID OR NOT
		outputResponseString="New BID price must be greater than the current BID price!";
		return outputResponseString;
	}

	
	//String newBidPrice =  String.valueOf(Integer.parseInt(item.getItemCurrentBidPrice()) + Integer.parseInt(newBidValue) );
	String newBidPrice =  String.valueOf(Integer.parseInt(newBidValue) );
	
	

	//BID PLACED DATE SHOULD BE IN UTC
	CustomerBids customerBidsObj = new CustomerBids(userSessionScopedData.getCustomer(), item, newBidPrice, "NO", DateUtil.getCurrentDateInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm"));
	
	bidsManagerService.addBid(customerBidsObj);
	item.setItemCurrentBidPrice(newBidPrice);
	itemManagerService.updateItem(item);
	
	//UPDATE SESSION LIST WITH CURRENT BID DETAILS 
	List<Items> updatedItemsList = ItemsUtil.updateItemsListInSessionAfterBid(userSessionScopedData.getAllItems(), item);
	userSessionScopedData.setAllItems(updatedItemsList);
	
	//SORT THE LIST IN SESSION
    userSessionScopedData.setAllItems(ItemsUtil.sortItemsListInSession(userSessionScopedData.getAllItems()));
	
	}
	//return "ItemDetail2";
    return "Bid Placed Successfully!";
}



@RequestMapping(value = "/itemBidPriceCheck/{itemId}", method = RequestMethod.GET)
public @ResponseBody String itemBidPriceCheck(@PathVariable String itemId, RedirectAttributes redirectAttributes,  Model model, Locale locale ) {
     
	System.out.println("---------------inside itemBidPriceCheck ajax call----itemId-----> "+ itemId);
	String response=null;
	
    //System.out.println("---------------> "+currencyFormatter.getCurrency().toString());
   
	synchronized(this) { //SYNCHRONIZED BLOCK
	
	Items item = itemManagerService.getItem(Integer.parseInt(itemId));
	 
	response = currencyFormatter.format(Double.parseDouble(item.getItemCurrentBidPrice()));
	
	//System.out.println("---------------inside itemBidPriceCheck ajax call----response-----> "+ response);
	
		}
	//return "ItemDetail2";
    return response;
}



	@Scheduled(fixedDelay=5000*60*2)//	5000*60----> 300secs (5 mins)-----total 10 mins here
	//@Scheduled(fixedRate=5000)
	//@Scheduled(cron="*/5 * * * * ?")
	public void lookOutForItemBidFinished() {
		//logger.debug("Start schedule");	
		
		System.out.println("-------> START SCHEDULE lookOutForItemBidFinished <----------- ");
		
		scheduledTasksService.lookOutForItemBidFinished(Locale.getDefault());
		
		System.out.println("-------> END SCHEDULE lookOutForItemBidFinished <----------- ");
	 
		//logger.debug("End schedule");
	}
	
	
	
	@Scheduled(fixedDelay=5000*60*3)//	5000*60----> 300secs (5 mins)-------> total 15 mins
	//@Scheduled(fixedRate=5000)
	//@Scheduled(cron="*/5 * * * * ?")
	public void sendBillingAndShippingInfoEmail() {
		//logger.debug("Start schedule");
		
		System.out.println("-------> START SCHEDULE sendBillingAndShippingInfoEmail <----------- ");
		
		scheduledTasksService.sendBillingAndShippingInfoEmail(Locale.getDefault());
		
		System.out.println("-------> END SCHEDULE sendBillingAndShippingInfoEmail <----------- ");
	 
		//logger.debug("End schedule");
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		//binder.setValidator(itemManagerValidator);		
	}
	
	
	@ModelAttribute("userData")
	public UserSessionScopedData getUserSessionScopedData() {
	    return userSessionScopedData;
	}
	
	
	
	@RequestMapping(value = "/itemPullImage/{itemImageName}", method = RequestMethod.GET)
	public void returnImage(@PathVariable String itemImageName, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request, HttpServletResponse response ) {
	     
		
		
		itemImageName=itemImageName+".jpg";
		
		//System.out.println("---------------inside return Image CALL "+ itemImageName);
		
		 // Decode the file name (might contain spaces and on) and prepare file object.
        File image = null;
		try {
			image = new File(IMAGE_LOCATION, URLDecoder.decode(itemImageName, "UTF-8"));
			
			System.out.println("---------------inside return Image CALL "+ itemImageName);
			
			if (!image.exists()) {
	            // Do your thing if the file appears to be non-existing.
	            // Throw an exception, or send 404,	 or show default/warning image, or just ignore it.
	            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;
	        }
			//System.out.println("---------------inside return Image CALL-------> FILE EXISTS "+ itemImageName);
			
			
			String contentType = context.getMimeType(image.getName());
			
			 // Init servlet response.
	        response.reset();
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setContentType(contentType);
	        response.setHeader("Content-Length", String.valueOf(image.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception ex)
		{
			
		}
		
		//System.out.println("---------------inside return Image CALL 2");
		
		 // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch(Exception ex)
        {
        	
        }
        finally {
            // Gently close streams.
            close(output);
            close(input);
        }
        
        
        //System.out.println("---------------inside return Image CALL 3");
        
        response.toString();
        
	}
	
	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }
	
	
	@RequestMapping(value = "/ModifyItems", method = RequestMethod.GET)
	public String modifyItemsGet(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult bindingResult, SessionStatus status, Model model,Errors errors){
 		
		System.out.println("TEST modifyItemsGet INIT FORM METHOD -------> ");
		
		String currentDateStringInSelectedTimezone = DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm");
		if(userSessionScopedData!=null && userSessionScopedData.getAllItems()!=null && userSessionScopedData.getAllItems().size()>0) 
		{
			
			List modifyItemsList = new ArrayList<Items>();
			for(Items itemObj : userSessionScopedData.getAllItems())
			{
				if(itemObj!=null && itemObj.getCustomers().getCustomerId() == userSessionScopedData.getCustomer().getCustomerId())
				{
					//CHECK ITEM FINAL TIME TO BID VALUE					
					String itemFinalToBid = DateUtil.doDateToString(itemObj.getItemFinalTimeToBid(),"MM/dd/yyyy HH:mm");				
					boolean result = DateUtil.isDate1AfterDate2(itemFinalToBid, currentDateStringInSelectedTimezone);
					
					System.out.println("result-------> "+result);
					
					if(result)
					{//IF true, I.E, ITEMFINALTIMETOBID IS AFTER CURRENT TIME...THAT MEANS you can modify the auction
						modifyItemsList.add(itemObj);
					}
				}
			}
			
			userSessionScopedData.setModifyItems(modifyItemsList);			
		}
		
		
		
		//RESET THE PAGINATION COUNTER
		if(userSessionScopedData.getModifyItems()!=null && userSessionScopedData.getModifyItems().size()>0)
		{
			
			System.out.println("size----> "+ userSessionScopedData.getModifyItems().size());
			
			//RESET IT TO 6
			userSessionScopedData.setModifyRecordsPerPage(6);
			
			userSessionScopedData.setModifyTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getModifyItems().size()/userSessionScopedData.getModifyRecordsPerPage()) ) );
			
			System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getModifyTotalPages());
			
			userSessionScopedData.setModifyBeginRecord(0);
			userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyRecordsPerPage()-1);
			
			//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
			if(userSessionScopedData.getModifyEndRecord() > userSessionScopedData.getModifyItems().size())
			{
				userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyItems().size()-1);
			}
			
			
			userSessionScopedData.setModifyCurrentPage(1);
			
		}
		
		
		//return form view
		return "ModifyItems";
	}
	
	//GET/REDIRECT/GET
	@RequestMapping(value = "/ModifyCustomerItemGet/{itemId}", method = RequestMethod.GET)
    public String ModifyCustomerItemGet(@PathVariable String itemId, RedirectAttributes redirectAttributes, Model model) {
         
		System.out.println("itemId--->"+itemId);
		
		if(userSessionScopedData!=null && (userSessionScopedData.getTimeZoneList()==null || userSessionScopedData.getTimeZoneList().size()==0) )
		{
			List<String> timeZoneList = new ArrayList<String>();
			timeZoneList.add("EST/EDT");
			timeZoneList.add("PST/PDT");
			timeZoneList.add("CST/CDT");
			timeZoneList.add("MST/MDT");
			
			userSessionScopedData.setTimeZoneList(timeZoneList);
		}
		
		
		
		Items item = itemManagerService.getItem(Integer.parseInt(itemId));
		
		//YOU NEED TO CONVERT THE DATE FROM UTC TO THE ITEM'S TIME ZONE, BEFORE DISLAYING IN GUI
		//public static String changeTimeZone(String inTZ,String outTZ, String inDateStr, String inFormat,String outFormat)
		String itemTimezoneDateTimeString = DateUtil.changeTimeZone(DateUtil.getTimezoneToZoneID("UTC"), DateUtil.getTimezoneToZoneID(item.getItemFinalTimeToBidTimezone()), DateUtil.doDateToString(item.getItemFinalTimeToBid(), "MM/dd/yyyy HH:mm"), "MM/dd/yyyy HH:mm", "MM/dd/yyyy HH:mm");
		System.out.println("converted itemTimezoneDateTimeString--------------> "+itemTimezoneDateTimeString);
		
		item.setItemFinalTimeToBid(DateUtil.doStringToDate(itemTimezoneDateTimeString, "MM/dd/yyyy HH:mm"));
		
		
		if(item!=null)
		{
			System.out.println("------------------------>");			
			model.addAttribute("ITEM_MODIFY_MANAGER", item);
			//redirectAttributes.addFlashAttribute("ITEM_MODIFY_MANAGER", item);
		}
		
		return "ModifyCustomerItem";
		
		//return "redirect:ModifyCustomerReItemPageRedirect.htm";
   }
	

	//@RequestMapping(value="*/ModifyCustomerReItemPageRedirect.htm", method = RequestMethod.GET)
	//public String ModifyCustomerItemPageRedirect(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors) {
	//	
	//	
	//	return "ModifyCustomerItem";	
	//}
	
	
	
	

	@RequestMapping(value = "/ModifyCustomerItemGet/ModifyItemSubmit", method = RequestMethod.POST)
	public Object modifyItemSubmitPost(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		
		
		
		itemManagerValidator.validate(item, result);
		
			
		
		if (result.hasErrors()) {
			//if validator failed
			return "ModifyCustomerItem";
		} else {
			
			 System.out.println("ITEM FINAL TIME TO BID------before validation--------> "+item.getItemFinalTimeToBid().toString());
				
			
			System.out.println("item.getFiles().size()---------------->"+item.getFiles().size());
			
			boolean foundIssue=false;
			boolean foundAtleastOneItem=false;
			boolean foundItemSizeLimitIssue=false;
			boolean foundInvalidFileTypeIssue=false;
			
			
			/*if(item.getFiles()==null || item.getFiles().size()==0)
			{	
				errors.rejectValue("files", "required.itemImage");				
				return "ModifyCustomerItem";
			}
			*/
			if(item.getFiles()!=null && item.getFiles().size()>0)
			{
				MultipartFile multipartFile = null;
				for (int i=0; i<item.getFiles().size(); i++) {
										
	            	 multipartFile = item.getFiles().get(i);
	            	 
	            	 
	            	 System.out.println("file size---> "+multipartFile.getSize());
	            	 System.out.println("original file name---> "+multipartFile.getOriginalFilename());
	            	 System.out.println("file name---> "+multipartFile.getName());
	            	 
	            	 
	            	 
	            	 //FILE UPLOADED OR NOT
	            	 if(multipartFile!=null && multipartFile.getSize()>0)
	            	 {
	            		 foundAtleastOneItem=true;
	              	 }
	            	
	            	 //FOR SIZE CHECK
	            	 if(multipartFile!=null && multipartFile.getSize()>0 && multipartFile.getSize()>FILE_SIZE_LIMIT)
	            	 {
	            		 foundItemSizeLimitIssue=true;
	            	 }
	            	 
	            	//FOR FILE TYPE CHECK
	            	 if(multipartFile!=null && multipartFile.getSize()>0)
	            	 {
	            		 boolean itemTypeNotFound=true;
	            		 for(int k=0; k<ALLOWED_FILE_TYPES.length; k++)
	            		 {//TO CHECK WHETHER THE TYPE IS IN THE ACCEPTED ARRAY FILE TYPES
	            			 if( multipartFile.getOriginalFilename().contains(ALLOWED_FILE_TYPES[k])  )
	            			 {
	            				 itemTypeNotFound=false;
	            			 }
	            		 }
	            		 
	            		 if(itemTypeNotFound)
	            		 {
	            			 foundInvalidFileTypeIssue=true;
	            		 }
	            	 }
				}
			}
			
			
			/*if(!foundAtleastOneItem)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage");				
				return "ModifyCustomerItem";
			}	*/
			
			if(foundItemSizeLimitIssue)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage.filesize");				
				return "ModifyCustomerItem";
			}
			
			if(foundInvalidFileTypeIssue)
			{	//IF THERE ARE ERRORS
				errors.rejectValue("files", "required.itemImage.fileType");				
				return "ModifyCustomerItem";
			}
			

			//SET THE ITEM FINAL TIME TO BID TO UTC
			
			//DateUtil.changeTimeZone(inTZ, outTZ, inDateStr, inFormat, outFormat);
			String utcDateTimeString = DateUtil.changeTimeZone(DateUtil.getTimezoneToZoneID(item.getItemFinalTimeToBidTimezone()), DateUtil.getTimezoneToZoneID("UTC"), DateUtil.doDateToString(item.getItemFinalTimeToBid(), "MM/dd/yyyy HH:mm"), "MM/dd/yyyy HH:mm", "MM/dd/yyyy HH:mm");
			System.out.println("converted utcDateTimeString--------------> "+utcDateTimeString);
			
			item.setItemFinalTimeToBid(DateUtil.doStringToDate(utcDateTimeString, "MM/dd/yyyy HH:mm"));
			
			
			List<MultipartFile> files = item.getFiles();
			int fileCounter=0;
				
		         //	foundAtleastOneItem is true..then only upload the new images
		        if(foundAtleastOneItem && null != files && files.size() > 0) {
		            for (int i=0; i<files.size(); i++) {
		            	
		            	MultipartFile multipartFile = files.get(i);
		            	
		            	if(multipartFile!=null && multipartFile.getSize()>0)
		            	{
		            	
			            	System.out.println("multipartFile.getSize()------------> "+multipartFile.getSize());               
			                
			            	String filename = "filename_"+Calendar.getInstance().getTimeInMillis()+i+".jpg";
			            	
			            	try {
								AwsS3StorageInteraction.putObjectToBucket("CherryBids", filename, multipartFile.getInputStream(), multipartFile.getSize());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			            	
			            	//TO FIND VALID GOOD FILES
			            	fileCounter = fileCounter+1;
			                
			                if(fileCounter==1)
			                {
			                	item.setItemImageLocation1(filename);
			                }
			                else if(fileCounter==2)
			            	{
			                	item.setItemImageLocation2(filename);
			            	}
			                else if(fileCounter==3)
			            	{
			                	item.setItemImageLocation3(filename);
			            	}
			                else if(fileCounter==4)
			            	{
			                	item.setItemImageLocation4(filename);
			            	}
			                else if(fileCounter==5)
			            	{
			                	item.setItemImageLocation5(filename);
			            	}		             
			                else
			                {
			                	
			                }
		            	} 
		            }
		        }
		        
		        System.out.println("ITEM FINAL TIME TO BID--------------> "+item.getItemFinalTimeToBid().toString());
		        
		        item.setCustomers(userSessionScopedData.getCustomer());
		        //item.setItemAddedDate(new Date());
		        //SET THE ADDDED TIME TO UTC
		        //item.setItemAddedDate(DateUtil.getCurrentDateInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm"));
		        item.setItemCurrentBidPrice(item.getItemPrice());
		        
		        //CHECK ITEM QUANTITY
		        if(item.getItemQuantityAvailable()==null || item.getItemQuantityAvailable().trim().length()==0)
		        {//DEFAULT IS 1
		        	item.setItemQuantityAvailable("1");
		        }
		        
		        
		        //SET THE MODIFIED DATE.		        
		        item.setItemModifiedDate(DateUtil.getCurrentDateInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm"));
		        
		        //SET ITEM STATUS CODE AND STATUS REASON
		        item.setItemStatusCode("100");
		        item.setItemStatusReason("OPEN FOR BIDDING");
		        
				
		        //UPDATE ITEM
		        itemManagerService.updateItem(item);
		        
		        
		        //UPDATE SESSION LIST WITH THE NEWLY ADDED ITEM.
		        if(userSessionScopedData.getAllItems()==null)
		        {
		        	List<Items> allitemsList = new ArrayList<Items>();
		        	allitemsList.add(item);
		        	userSessionScopedData.setAllItems(allitemsList);
		        }
		        else
		        {
		        	for(Items listItem : userSessionScopedData.getAllItems())
		        	{
		        		if(listItem!=null && listItem.getItemId().toString().equalsIgnoreCase(item.getItemId().toString()))
		        		{
		        			userSessionScopedData.getAllItems().remove(listItem);
		        			userSessionScopedData.getAllItems().add(item);
		        			break;
		        		}
		        	}
		        }
		        
		      //SORT THE LIST IN SESSION
		        userSessionScopedData.setAllItems(ItemsUtil.sortItemsListInSession(userSessionScopedData.getAllItems()));
		        
					
					//form success
					//return ":modifyItemSucessRedirect.htm";
		        	return new RedirectView("/modifyItemSucessRedirect.htm", true);
		}
	}
	
	
	@RequestMapping(value="/modifyItemSucessRedirect.htm", method = RequestMethod.GET)
	public String processModifyItemSubmitRedirect(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult result, SessionStatus status, Model model,Errors errors) {
		
		
		return "ModifyItemSuccess";	
	}
	
	
	
	@RequestMapping(value = "/modifyItemPagination/", method = RequestMethod.GET)
	public String modifyItemPaginationList(@RequestParam(value="pageNo", required=true) String pageNo, RedirectAttributes redirectAttributes, Model model ) {
		
		
		if(userSessionScopedData.getModifyItems()!=null && userSessionScopedData.getModifyItems().size()>0)
		{	
			//SET BEGIN INDEX
			if((Integer.parseInt(pageNo)-1) != 0)
			{
				userSessionScopedData.setModifyBeginRecord((Integer.parseInt(pageNo)-1) * userSessionScopedData.getModifyRecordsPerPage());
			}
			else
			{
				userSessionScopedData.setModifyBeginRecord(0);
			}
			
			//SET END RECORD
			userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyBeginRecord()+(userSessionScopedData.getModifyRecordsPerPage()-1));
			
			//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
			if(userSessionScopedData.getModifyEndRecord() > userSessionScopedData.getModifyItems().size())
			{
				userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyItems().size()-1);
			}
			
			//SET THE CURRENT PAGE NUMBER
			userSessionScopedData.setModifyCurrentPage(Integer.parseInt(pageNo));

		}
		
		//return "ItemDetail2";
		return "ModifyItems";
	}


	@RequestMapping(value = "/modifyItemPaginationItemsPerPage", method = RequestMethod.POST)
	public String modifyItemPaginationitemsPerPage(@RequestParam(value="modifyItemsPerPage", required=true) String itemsPerPage, RedirectAttributes redirectAttributes, Model model ) {
		
		
		if(userSessionScopedData.getModifyItems()!=null && userSessionScopedData.getModifyItems().size()>0)
		{		
			//CHANGE THE RECORDS PER PAGE
			userSessionScopedData.setModifyRecordsPerPage(Integer.parseInt(itemsPerPage));
			
			userSessionScopedData.setModifyTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getModifyItems().size()/userSessionScopedData.getModifyRecordsPerPage()) ) );
			
			System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getModifyTotalPages());
			
			userSessionScopedData.setModifyBeginRecord(0);
			userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyRecordsPerPage()-1);
			//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
			if(userSessionScopedData.getModifyEndRecord() > userSessionScopedData.getModifyItems().size())
			{
				userSessionScopedData.setModifyEndRecord(userSessionScopedData.getModifyItems().size()-1);
			}
			
			userSessionScopedData.setModifyCurrentPage(1);
		}
		
		//return "ItemDetail2";
		return "ModifyItems";
	}

		
		
	
	
	
	
	
	
@RequestMapping(value = "/itemPagination/", method = RequestMethod.GET)
public String itemPaginationList(@RequestParam(value="pageNo", required=true) String pageNo, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getAllItems()!=null && userSessionScopedData.getAllItems().size()>0)
	{	
		//SET BEGIN INDEX
		if((Integer.parseInt(pageNo)-1) != 0)
		{
			userSessionScopedData.setBeginRecord((Integer.parseInt(pageNo)-1) * userSessionScopedData.getRecordsPerPage());
		}
		else
		{
			userSessionScopedData.setBeginRecord(0);
		}
		
		//SET END RECORD
		userSessionScopedData.setEndRecord(userSessionScopedData.getBeginRecord()+(userSessionScopedData.getRecordsPerPage()-1));
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getEndRecord() > userSessionScopedData.getAllItems().size())
		{
			userSessionScopedData.setEndRecord(userSessionScopedData.getAllItems().size()-1);
		}
		
		//SET THE CURRENT PAGE NUMBER
		userSessionScopedData.setCurrentPage(Integer.parseInt(pageNo));

	}
	
	//return "ItemDetail2";
	return "index";
}


@RequestMapping(value = "/itemPaginationItemsPerPage", method = RequestMethod.POST)
public String itemPaginationitemsPerPage(@RequestParam(value="itemsPerPage", required=true) String itemsPerPage, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getAllItems()!=null && userSessionScopedData.getAllItems().size()>0)
	{		
		//CHANGE THE RECORDS PER PAGE
		userSessionScopedData.setRecordsPerPage(Integer.parseInt(itemsPerPage));
		
		userSessionScopedData.setTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getAllItems().size()/userSessionScopedData.getRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getTotalPages());
		
		userSessionScopedData.setBeginRecord(0);
		userSessionScopedData.setEndRecord(userSessionScopedData.getRecordsPerPage()-1);
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getEndRecord() > userSessionScopedData.getAllItems().size())
		{
			userSessionScopedData.setEndRecord(userSessionScopedData.getAllItems().size()-1);
		}
		
		userSessionScopedData.setCurrentPage(1);
	}
	
	//return "ItemDetail2";
	return "index";
}

	
	
	
	
@RequestMapping(value = "/ClosedAuctionsPage", method = RequestMethod.GET)
public String ClosedAuctionsPageGet(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult bindingResult, SessionStatus status, Model model,Errors errors){
		
	System.out.println("TEST MyPlacedBidsPage INIT FORM METHOD -------> ");
	
	List<Items> allClosedItemsList = itemManagerService.getItemsByBidClosed(DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "yyyy/MM/dd HH:mm"));
	
	if(userSessionScopedData!=null && allClosedItemsList!=null && allClosedItemsList.size()>0) 
	{
		
		List closedItemsList = new ArrayList<Items>();
		String currentDateStringInSelectedTimezone = DateUtil.getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("UTC"), "MM/dd/yyyy HH:mm");
		for(Items itemObj : allClosedItemsList)
		{
			if(userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerType()!=null && userSessionScopedData.getCustomer().getCustomerType().trim().equalsIgnoreCase("BUSINESS"))
			{//FOR BUSINESS OR FOUNDATION USER, GET ONLY HIS ITEMS
				if( itemObj!=null && itemObj.getCustomers().getCustomerId() == userSessionScopedData.getCustomer().getCustomerId())
				{
					
					//CHECK ITEM FINAL TIME TO BID VALUE					
					String itemFinalToBid = DateUtil.doDateToString(itemObj.getItemFinalTimeToBid(),"MM/dd/yyyy HH:mm");				
					boolean result = DateUtil.isDate1AfterDate2(itemFinalToBid, currentDateStringInSelectedTimezone);
					
					System.out.println("result-------> "+result);
					
					if(!result)
					{//IF FALSE, I.E, ITEMFINALTIMETOBID IS NOT AFTER CURRENT TIME...THAT MEANS CLOSED AUCTION
						closedItemsList.add(itemObj);
					}
				}
			}
			
			
			
			if(userSessionScopedData.getCustomer()!=null && userSessionScopedData.getCustomer().getCustomerType()!=null && userSessionScopedData.getCustomer().getCustomerType().trim().equalsIgnoreCase("USER"))
			{//FOR FAN/USER , GET GET ALL CLOSED AUCITONS
								
					//CHECK ITEM FINAL TIME TO BID VALUE					
					String itemFinalToBid = DateUtil.doDateToString(itemObj.getItemFinalTimeToBid(),"MM/dd/yyyy HH:mm");				
					boolean result = DateUtil.isDate1AfterDate2(itemFinalToBid, currentDateStringInSelectedTimezone);
					
					System.out.println("result-------> "+result);
					
					if(!result)
					{//IF FALSE, I.E, ITEMFINALTIMETOBID IS NOT AFTER CURRENT TIME...THAT MEANS CLOSED AUCTION
						closedItemsList.add(itemObj);
					}
			}
		}
		
		userSessionScopedData.setClosedItems(closedItemsList);			
	}
	
	
	
	//RESET THE PAGINATION COUNTER
	if(userSessionScopedData.getClosedItems()!=null && userSessionScopedData.getClosedItems().size()>0)
	{
		
		System.out.println("size----> "+ userSessionScopedData.getClosedItems().size());
		
		//RESET IT TO 6
		userSessionScopedData.setClosedRecordsPerPage(6);
		
		userSessionScopedData.setClosedTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getClosedItems().size()/userSessionScopedData.getClosedRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getClosedTotalPages());
		
		userSessionScopedData.setClosedBeginRecord(0);
		userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedRecordsPerPage()-1);
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getClosedEndRecord() > userSessionScopedData.getClosedItems().size())
		{
			userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedItems().size()-1);
		}
		
		
		userSessionScopedData.setClosedCurrentPage(1);		
	}
	
	
	//return form view
	return "ClosedAuctions";
}

	
	
	
	
@RequestMapping(value = "/closedAuctionPagination/", method = RequestMethod.GET)
public String closedAuctionPaginationList(@RequestParam(value="pageNo", required=true) String pageNo, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getClosedItems()!=null && userSessionScopedData.getClosedItems().size()>0)
	{	
		//SET BEGIN INDEX
		if((Integer.parseInt(pageNo)-1) != 0)
		{
			userSessionScopedData.setClosedBeginRecord((Integer.parseInt(pageNo)-1) * userSessionScopedData.getClosedRecordsPerPage());
		}
		else
		{
			userSessionScopedData.setClosedBeginRecord(0);
		}
		
		//SET END RECORD
		userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedBeginRecord()+(userSessionScopedData.getClosedRecordsPerPage()-1));
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getClosedEndRecord() > userSessionScopedData.getClosedItems().size())
		{
			userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedItems().size()-1);
		}
		
		//SET THE CURRENT PAGE NUMBER
		userSessionScopedData.setClosedCurrentPage(Integer.parseInt(pageNo));

	}
	
	//return "ItemDetail2";
	return "ClosedAuctions";
}


@RequestMapping(value = "/closedAuctionPaginationItemsPerPage", method = RequestMethod.POST)
public String closedAuctionPaginationitemsPerPage(@RequestParam(value="closedItemsPerPage", required=true) String itemsPerPage, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getClosedItems()!=null && userSessionScopedData.getClosedItems().size()>0)
	{		
		//CHANGE THE RECORDS PER PAGE
		userSessionScopedData.setClosedRecordsPerPage(Integer.parseInt(itemsPerPage));
		
		userSessionScopedData.setClosedTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getClosedItems().size()/userSessionScopedData.getClosedRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getClosedTotalPages());
		
		userSessionScopedData.setClosedBeginRecord(0);
		userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedRecordsPerPage()-1);
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getClosedEndRecord() > userSessionScopedData.getClosedItems().size())
		{
			userSessionScopedData.setClosedEndRecord(userSessionScopedData.getClosedItems().size()-1);
		}
		
		userSessionScopedData.setClosedCurrentPage(1);
	}
	
	return "ClosedAuctions";
}

	




@RequestMapping(value = "/MyPlacedBidsPage", method = RequestMethod.GET)
public String placedBidsGet(@ModelAttribute("ITEM_MODIFY_MANAGER") Items item, BindingResult bindingResult, SessionStatus status, Model model,Errors errors){
		
	System.out.println("TEST MyPlacedBidsPage INIT FORM METHOD -------> ");	
	
	List<CustomerBids>  bidsOfCusotmer = bidsManagerService.getBidsOfCustomer(userSessionScopedData.getCustomer().getCustomerId());	
	
	
	if(bidsOfCusotmer!=null && bidsOfCusotmer.size()>0) 
	{	
		userSessionScopedData.setMyPlacedBids(bidsOfCusotmer);			
	}
	
	
	//RESET THE PAGINATION COUNTER
	if(userSessionScopedData.getMyPlacedBids()!=null && userSessionScopedData.getMyPlacedBids().size()>0)
	{
		
		System.out.println("size----> "+ userSessionScopedData.getMyPlacedBids().size());
		
		//RESET IT TO 12
		userSessionScopedData.setPlacedBidsRecordsPerPage(12);
		
		userSessionScopedData.setPlacedBidsTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getMyPlacedBids().size()/userSessionScopedData.getPlacedBidsRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getPlacedBidsTotalPages());
		
		userSessionScopedData.setPlacedBidsBeginRecord(0);
		userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getPlacedBidsRecordsPerPage()-1);
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getPlacedBidsEndRecord() > userSessionScopedData.getMyPlacedBids().size())
		{
			userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getMyPlacedBids().size()-1);
		}
		
		
		userSessionScopedData.setPlacedBidsCurrentPage(1);		
	}
	
	
	//return form view
	return "MyPlacedBids";
}

	
	
	
	
@RequestMapping(value = "/placedBidsPagination/", method = RequestMethod.GET)
public String placedBidsPaginationList(@RequestParam(value="pageNo", required=true) String pageNo, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getMyPlacedBids()!=null && userSessionScopedData.getMyPlacedBids().size()>0)
	{	
		//SET BEGIN INDEX
		if((Integer.parseInt(pageNo)-1) != 0)
		{
			userSessionScopedData.setPlacedBidsBeginRecord((Integer.parseInt(pageNo)-1) * userSessionScopedData.getPlacedBidsRecordsPerPage());
		}
		else
		{
			userSessionScopedData.setPlacedBidsBeginRecord(0);
		}
		
		//SET END RECORD
		userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getPlacedBidsBeginRecord()+(userSessionScopedData.getPlacedBidsRecordsPerPage()-1));
		
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getPlacedBidsEndRecord() > userSessionScopedData.getMyPlacedBids().size())
		{
			userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getMyPlacedBids().size()-1);
		}
		
		//SET THE CURRENT PAGE NUMBER
		userSessionScopedData.setPlacedBidsCurrentPage(Integer.parseInt(pageNo));

	}
	
	//return "ItemDetail2";
	return "MyPlacedBids";
}


@RequestMapping(value = "/placedBidsPaginationItemsPerPage", method = RequestMethod.POST)
public String placedBidsPaginationitemsPerPage(@RequestParam(value="placedBidsPerPage", required=true) String itemsPerPage, RedirectAttributes redirectAttributes, Model model ) {
	
	
	if(userSessionScopedData.getMyPlacedBids()!=null && userSessionScopedData.getMyPlacedBids().size()>0)
	{		
		//CHANGE THE RECORDS PER PAGE
		userSessionScopedData.setPlacedBidsRecordsPerPage(Integer.parseInt(itemsPerPage));
		
		userSessionScopedData.setPlacedBidsTotalPages( (int) Math.ceil( ((float)userSessionScopedData.getMyPlacedBids().size()/userSessionScopedData.getPlacedBidsRecordsPerPage()) ) );
		
		System.out.println("TOTAL PAGES-------------> "+userSessionScopedData.getPlacedBidsTotalPages());
		
		userSessionScopedData.setPlacedBidsBeginRecord(0);
		userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getPlacedBidsRecordsPerPage()-1);
		//SET THE SIZE TO LIST SIZE IF THE SIZE OF END RECORD IS MORE THAN THE SIZE OF LIST
		if(userSessionScopedData.getPlacedBidsEndRecord() > userSessionScopedData.getMyPlacedBids().size())
		{
			userSessionScopedData.setPlacedBidsEndRecord(userSessionScopedData.getMyPlacedBids().size()-1);
		}
		
		userSessionScopedData.setPlacedBidsCurrentPage(1);
	}
	
	return "MyPlacedBids";
}

	

}
