package com.rendevous.intention.trick.spring.util;

import java.io.File;
import java.io.InputStream;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.smartfile.api.BasicClient;
import com.smartfile.api.SmartFileException;

public class SmartFileStorageInteraction {
	
	private static final String SMARTFILE_API_KEY="5q2XHCRDpsXgOplWyzzLcSXxVU2HyD";
	private static final String SMARTFILE_API_PASSWORD="ODUHFQn8OyAvtPSgEXuPNhWgjJdhSa";
	
	
	public static void putObjectToSmartFile(String objectName, File tmpfile, long size)
	{	
		
		try {
				BasicClient api = new BasicClient(SMARTFILE_API_KEY,SMARTFILE_API_PASSWORD);
				//api.setApiUrl("krishna.test");
				//File file=null;
				
				api.post("/Home/","/"+objectName,tmpfile);
				
				System.out.println("success");
			} catch (SmartFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
	
	
	public static void main(String[] args)
	{
		File tmfile = new File("C://devtools//football.jpg");
		
		putObjectToSmartFile("2017", tmfile, 34343);
		
	}

}
