package com.rendevous.intention.trick.spring.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.TimeZone;

public class DateUtil {
	
	private static Hashtable<String, String> timezoneToZoneID = null; 
	
	public static Date getCurrentDateTime()
	{
		Calendar TodayDate = Calendar.getInstance();
		
		return TodayDate.getTime();
	}
	
	public static String getTimezoneToZoneID(String timezone)
	{
		if(timezoneToZoneID==null || timezoneToZoneID.size()==0)
		{
			timezoneToZoneID = new Hashtable<String, String>();
			
			timezoneToZoneID.put("EST/EDT", "EST5EDT");
			timezoneToZoneID.put("PST/PDT", "PST8PDT");
			timezoneToZoneID.put("CST/CDT", "CST6CDT");
			timezoneToZoneID.put("MST/MDT", "MST7MDT");
			timezoneToZoneID.put("UTC", "UTC");			
		}
		
		return timezoneToZoneID.get(timezone);
	}
	
	
	
	public static boolean isDate1AfterDate2(String date1String, String date2String)
	{
		boolean result = false;
		
		try{
			 
    		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        	Date date1 = sdf.parse(date1String);
        	Date date2 = sdf.parse(date2String);
 
        	System.out.println(sdf.format(date1));
        	System.out.println(sdf.format(date2));
 
        	Calendar cal1 = Calendar.getInstance();
        	Calendar cal2 = Calendar.getInstance();
        	cal1.setTime(date1);
        	cal2.setTime(date2);
 
        	if(cal1.after(cal2))
        	{
        		return true;
        	}
        	else
        	{
        		return false;
        	}
 
    	}catch(ParseException ex){
    		ex.printStackTrace();
    	}
		
		
		return result;
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
	
	
	public static String doDateToString(Date dt, String format){
		
		SimpleDateFormat formatter=new SimpleDateFormat(format);
		
		String s=formatter.format(dt);
	
		return s;
	
		}
	
	
	
	public static String changeTimeZone(String inTZ,String outTZ, String inDateStr, String inFormat,String outFormat){
		
		//convert the incoming date string to a date object
		
		System.out.println("INSIDE DATEUTIL.changeTimeZone() METHOD");
		
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
	
	
	public static Date getCurrentDateInDesiredTimeZone(String outTZ, String outFormat )
	{
		DateFormat outDf=new SimpleDateFormat(outFormat);
		
		outDf.setTimeZone(TimeZone.getTimeZone(outTZ));
		
		String st=outDf.format(new Date());
		
		Date dt= doStringToDate(st, outFormat);
		return dt;
	}
	
	
	
	
	
	
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
	
	


	
	
	

}
