package org.jcg.springboot.aws.s3.serv;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile);
	void downloadFile(String keyName);
	void deleteFileFromS3Bucket(String fileName);
	
	void listFilesFromBucket();
	
	void createBucket();
		
	void deleteS3Bucket();
	
	void emptyBucket();
	
	//----------testing 
	
	void editFile(String text);
	
	String readFile(String fileName) throws IOException;
	//-----------testing
	

	
}