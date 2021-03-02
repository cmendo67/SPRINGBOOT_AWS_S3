package org.jcg.springboot.aws.s3.serv;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Service {

	void uploadFile(MultipartFile multipartFile);
	void downloadFile(String keyName);
	void deleteFileFromS3Bucket(String fileName);
	
	void listFilesFromBucket();
	
	void createBucket();
		
	void deleteS3Bucket();
	
	void emptyBucket();
	

	
}