package com.rendevous.intention.trick.spring.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTest {
	
	public static void main(String[] args)
	{
		//CHECK ITEM itemFinalTimeToBid
		
		String zoneID = DateUtil.getTimezoneToZoneID("PST/PDT");			
		TimeZone timeZone1 = TimeZone.getTimeZone(zoneID);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(timeZone1);		
		long currentTimeInMillis = calendar.getTimeInMillis();
		
		System.out.println("zone information ----------selected timezone----------> "+ calendar.getTimeZone().getDisplayName());
		System.out.println("currentTimeInMillis in selected timezone--------------------> "+ currentTimeInMillis);
		System.out.println("calendar.getTime().toString()------selected timezone--------------> "+ calendar.getTime().toString());
		
		
		
		
		calendar.setTimeInMillis(doStringToDate("08/31/2013 17:34", "MM/dd/yyyy HH:mm").getTime());
		long selectedItemTimeInMillis = calendar.getTimeInMillis();
		
		System.out.println("selectedItemTimeInMillis--------------------> "+ selectedItemTimeInMillis);
		System.out.println("calendar.getTime().toString()------selected timezone and date time--------------> "+ calendar.getTime().toString());
		
		if(selectedItemTimeInMillis>currentTimeInMillis)
		{
			//DO NOTHING
		}
		else
		{
			//errors.rejectValue("itemFinalTimeToBid", "invalid.itemFinalTimeToBid.not.future");
		}
		
		
		String desiredTimezoneString = getCurrentDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("PST/PDT"), "MM/dd/yyyy HH:mm");
		String desiredTimezoneStringIndia = getCurrentDateStringInDesiredTimeZone("Asia/Calcutta", "MM/dd/yyyy HH:mm");
		
		System.out.println("desiredTimezoneString--------------> "+desiredTimezoneString);
		System.out.println("desiredTimezoneStringIndia--------------> "+desiredTimezoneStringIndia);
		
		
		
		String dateToDesiredTimezoneStringPST = getDateStringInDesiredTimeZone(DateUtil.getTimezoneToZoneID("PST/PDT"), "MM/dd/yyyy HH:mm", doStringToDate("08/31/2013 17:34", "MM/dd/yyyy HH:mm"));
		String dateToDesiredTimezoneStringIndia = getDateStringInDesiredTimeZone("Asia/Calcutta", "MM/dd/yyyy HH:mm", doStringToDate("08/31/2013 17:34", "MM/dd/yyyy HH:mm"));
		
		System.out.println("dateToDesiredTimezoneStringPST--------------> "+dateToDesiredTimezoneStringPST);
		System.out.println("dateToDesiredTimezoneStringIndia--------------> "+dateToDesiredTimezoneStringIndia);
		
		
	}

	
	public static Date doStringToDate(String date,String inFormat){
		
		DateFormat df = new SimpleDateFormat(inFormat);
		
		Date dt=null;
		
		try
		
		{		
		dt= df.parse(date);
		
		} catch (ParseException e)
		
		{
		
		//handle exception
		
		}
		
		return dt;
		
		}
	
	
	public static String changeTimeZone(String inTZ,String outTZ, String inDateStr, String inFormat,String outFormat){
		
		//convert the incoming date string to a date object
		
		Date inDate=null;
		
		DateFormat inDf=new SimpleDateFormat(inFormat);
		
		inDf.setTimeZone(TimeZone.getTimeZone(inTZ));
		
		try{
		
		inDate=inDf.parse(inDateStr);
		
		}catch(ParseException e){
		
		//handle exception
		
		}
		
		//System.out.println("inDate tostring="+inDate.toString());//Note this will give you time in default TZ and not in inTZ
		
		DateFormat outDf=new SimpleDateFormat(outFormat);
		
		outDf.setTimeZone(TimeZone.getTimeZone(outTZ));
		
		String st=outDf.format(inDate);
		
		return st;
		
		}
	
	public static Date getDateInDesiredTimeZone(String outTZ, String inDateStr, String inFormat, String outFormat)
	{
		Date inDate=null;
		
		DateFormat inDf=new SimpleDateFormat(inFormat);
		
		inDf.setTimeZone(TimeZone.getTimeZone(outTZ));
		
		try{
		
		inDate=inDf.parse(inDateStr);
		
		}catch(ParseException e){
		
		//handle exception
		
		}
		
		return inDate;
		
	}
	
	
	public static String getDateStringInDesiredTimeZone(String outTZ, String outFormat, Date inDate)
	{
		DateFormat outDf=new SimpleDateFormat(outFormat);
		
		outDf.setTimeZone(TimeZone.getTimeZone(outTZ));
		
		String st=outDf.format(inDate);
		
		return st;		
	}

	public static String getCurrentDateStringInDesiredTimeZone(String outTZ, String outFormat )
	{
		DateFormat outDf=new SimpleDateFormat(outFormat);
		
		outDf.setTimeZone(TimeZone.getTimeZone(outTZ));
		
		String st=outDf.format(new Date());
		
		return st;
	}

	
}
