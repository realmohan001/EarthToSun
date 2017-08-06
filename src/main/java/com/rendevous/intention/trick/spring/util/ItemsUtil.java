package com.rendevous.intention.trick.spring.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.rendevous.intention.trick.spring.controller.UserSessionScopedData;
import com.rendevous.intention.trick.spring.model.Items;

public class ItemsUtil {
	
	
	
	public static Items getItemFromItemsListByID(List<Items> itemsList, String id)
	{
		for(Items item : itemsList)
		{
			if(item != null && item.getItemId()!=null && id != null && id.equalsIgnoreCase(item.getItemId().toString()) )
			{
				return item;
			}
		}
		
		return null;
	}
	
	
	public static List<Items> updateItemsListInSessionAfterBid(List<Items> itemsList, Items item)
	{
		System.out.println("INSIDE ITEMS LIST updateItemsListInSessionAfterBid ----> ");

		
		for(Items sessionItem : itemsList)
		{
			if(sessionItem != null && sessionItem.getItemId()!=null && item != null && item.getItemId()!=null && item.getItemId().toString().equalsIgnoreCase(sessionItem.getItemId().toString()) )
			{				
				System.out.println("INSIDE ITEMS LIST FOUND MATCH----> ");
				itemsList.remove(sessionItem);
				itemsList.add(item);
				break;
			}
		}
		
		return itemsList;
	}
	
	
	
	public static List<Items> sortItemsListInSession(List<Items> itemsList)
	{
		System.out.println("INSIDE ITEMS LIST sortItemsListInSession ----> ");
		
		Map<Date, Items> map = new TreeMap<Date, Items>();
		
		
		for(Items itemObj : itemsList)
		{
			map.put(itemObj.getItemFinalTimeToBid(), itemObj);
		}
		
		Set<Date> keysSet = map.keySet();
		
		Iterator keysIterator = keysSet.iterator();
		List<Items> sortedList = new ArrayList<Items>();
		
		while(keysIterator.hasNext())
		{
			sortedList.add(map.get(keysIterator.next()));
		}
	
		return sortedList;
	}
	
	
	
	

}
