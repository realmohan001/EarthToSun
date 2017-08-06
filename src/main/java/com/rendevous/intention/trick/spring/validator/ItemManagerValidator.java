package com.rendevous.intention.trick.spring.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rendevous.intention.trick.spring.model.Items;
import com.rendevous.intention.trick.spring.util.DateUtil;



public class ItemManagerValidator implements Validator{
	
	@Override
	public boolean supports(Class clazz) {
		//just validate the Items instances
		return Items.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName",
			"required.itemName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemDescription",
			"required.itemDescription", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemPrice",
				"required.itemPrice", "Field name is required.");		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemQuantityAvailable","required.itemQuantityAvailable", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemFinalTimeToBid",
				"required.itemFinalTimeToBid", "Field name is required.");
 
		Items item = (Items)target;
		
		if(errors!=null && !errors.hasErrors())
		{//IF THERE ARE NO FIELD LEVEL ERRORS, THEN ONLY DO THE VALIDATIONS
			
			//CHECK TIMEZONE
			
			System.out.println("------------------------> "+item.getItemFinalTimeToBidTimezone());
			
			
			
			if( item.getItemFinalTimeToBidTimezone().trim().equalsIgnoreCase("NONE"))
			{
				errors.rejectValue("itemFinalTimeToBidTimezone", "required.itemFinalTimeToBidTimezone");
				return;
			}
			
			
			//CHECK ITEM DESCRIPTION LENGTH
			
				if(item.getItemPrice()!=null && item.getItemPrice().trim().length()>900)
				{
					errors.rejectValue("itemFinalTimeToBidTimezone", "invalid.itemDescription");
					return;
				}
			
			
			//CHECK ITEM PRICE
			try{
				Integer.parseInt(item.getItemPrice());
			}
			catch(Exception ex)
			{
				errors.rejectValue("itemPrice", "invalid.itemPrice");
			}
			
			//CHECK ITEM itemQuantityAvailable
			if(item.getItemQuantityAvailable()!=null && item.getItemQuantityAvailable().trim().length()>0)
			{
				try{
					Integer.parseInt(item.getItemQuantityAvailable());
				}
				catch(Exception ex)
				{
					errors.rejectValue("itemQuantityAvailable", "invalid.itemQuantityAvailable");
				}			
			}
			
			
			//CHECK ITEM itemFinalTimeToBid
			
			String selectedTimeZoneID = DateUtil.getTimezoneToZoneID(item.getItemFinalTimeToBidTimezone());		
			
			
			//String selectedDateStringInTimezone = DateUtil.getDateStringInDesiredTimeZone(selectedTimeZoneID, "MM/dd/yyyy HH:mm", item.getItemFinalTimeToBid());
			String selectedDateStringInTimezone = DateUtil.doDateToString(item.getItemFinalTimeToBid(),"MM/dd/yyyy HH:mm");			
			String currentDateStringInSelectedTimezone = DateUtil.getCurrentDateStringInDesiredTimeZone(selectedTimeZoneID, "MM/dd/yyyy HH:mm");
			
			
			System.out.println("selectedDateStringInTimezone-----> "+selectedDateStringInTimezone);
			System.out.println("currentDateStringInSelectedTimezone-----> "+currentDateStringInSelectedTimezone);
			
			//isDate1AfterDate2(String date1String, String date2String)
			
			boolean result = DateUtil.isDate1AfterDate2(selectedDateStringInTimezone, currentDateStringInSelectedTimezone);
			
			if(!result)
			{//IF FALSE
				errors.rejectValue("itemFinalTimeToBid", "invalid.itemFinalTimeToBid.not.future");
			}
			
			
			/*
			Calendar calendar = new GregorianCalendar();
			calendar.setTimeZone(timeZone1);
			System.out.println("zone information --------------------> "+ calendar.getTimeZone().getDisplayName());
			
			long currentTimeInMillis = calendar.getTimeInMillis();
			//calendar.
			System.out.println("currentTimeInMillis--------------------> "+ currentTimeInMillis);
			
			calendar.setTimeInMillis(item.getItemFinalTimeToBid().getTime());
			long selectedItemTimeInMillis = calendar.getTimeInMillis();
			System.out.println("selectedItemTimeInMillis--------------------> "+ selectedItemTimeInMillis);
			
			if(selectedItemTimeInMillis>currentTimeInMillis)
			{
				//DO NOTHING
			}
			else
			{
				errors.rejectValue("itemFinalTimeToBid", "invalid.itemFinalTimeToBid.not.future");
			}
			
			*/
			
		/*	if(item.getItemFinalTimeToBid().after(new Date()))
			{
				//DO NOTHING
			}
			else
			{
				errors.rejectValue("itemFinalTimeToBid", "invalid.itemFinalTimeToBid.not.future");
			}*/
				
			
		}
 
		
	}
}