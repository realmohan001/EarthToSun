package com.rendevous.intention.trick.spring.util;

import java.io.InputStream;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AwsS3StorageInteraction {
	
	private static AmazonS3 s3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
	
	public static void putObjectToBucket(String bucketName, String keyObject, InputStream iStream, long size)
	{		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/jpeg");
		metadata.setContentLength(size);
		
		
		System.out.println("Uploading a new object to S3 from a file\n");
		s3.setEndpoint("http://s3.amazonaws.com");
        s3.putObject(new PutObjectRequest(bucketName, keyObject, iStream,metadata));
	}

}
