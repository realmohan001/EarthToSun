package com.rendevous.intention.trick.spring.util;

import com.rendevous.intention.trick.spring.model.CustomerAddress;
import com.rendevous.intention.trick.spring.model.Items;

public class AddressUtil {

	/**
	 * @param args
	 */
	public static String returnAddressAsString(CustomerAddress address)
	{
		String finalAddressString=null;
		
		if(address!=null)
		{
			finalAddressString = address.getAddressLine1() + "\n";
			
			if(address.getAddressLine2()!=null && address.getAddressLine2().trim().length()>0)
			{
				finalAddressString = finalAddressString + address.getAddressLine2()+"\n";
			}
			
			finalAddressString = finalAddressString + address.getCity()+ " " + address.getState()+ " " + address.getPostalZipcode()+ " "+address.getCountry();
			
			return finalAddressString;
		}
		
		return finalAddressString;		
	}
	
	
	
	public static void main(String args[])
	{
		CustomerAddress address = new CustomerAddress();
		
		address.setAddressLine1("663 e royal ln");
		address.setAddressLine2("#2060");
		
		address.setCity("irving");
		address.setState("TX");
		address.setPostalZipcode("75039");
		address.setCountry("United states of America");
		
		
		System.out.println(returnAddressAsString(address));
		
	}
	
}
