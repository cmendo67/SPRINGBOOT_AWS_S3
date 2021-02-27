package org.jcg.springboot.aws.s3.serv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ListVersionsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.S3VersionSummary;
import com.amazonaws.services.s3.model.VersionListing;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import io.netty.util.Constant;

@Service
public class AWSS3ServiceImpl implements AWSS3Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3ServiceImpl.class);
	
	@Autowired
	private AmazonS3 amazonS3;
	@Value("${aws.s3.bucket}")
	private String bucketName;

	@Override
	// @Async annotation ensures that the method is executed in a different background thread 
	// but not consume the main thread.
	@Async
	public void uploadFile(final MultipartFile multipartFile) {
		LOGGER.info("File upload in progress.");
		try {
			final File file = convertMultiPartFileToFile(multipartFile);
			uploadFileToS3Bucket(bucketName, file);
			LOGGER.info("File upload is completed.");
			file.delete();	// To remove the file locally created in the project folder.
		} catch (final AmazonServiceException ex) {
			LOGGER.info("File upload is failed.");
			LOGGER.error("Error= {} while uploading file.", ex.getMessage());
		}
	}

	private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			LOGGER.error("Error converting the multi-part file to file= ", ex.getMessage());
		}
		return file;
	}

	private void uploadFileToS3Bucket(final String bucketName, final File file) {
		final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
		LOGGER.info("Uploading file with name= " + uniqueFileName);
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
		amazonS3.putObject(putObjectRequest);
	}
		
	@Async
    public void deleteFileFromS3Bucket(String fileName)
    {
		try {
		  /* Send Delete Object Request */
        /* Delete single object 's3.png' */
		amazonS3.deleteObject(bucketName,fileName);
		
	      /* Create an Object of DeleteObjectsRequest - Arguments: BucketName */
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName);
         
        /* Set Object Keys to delete */
        request.setKeys(Arrays.asList(new KeyVersion[] { new KeyVersion(fileName)}));
        
        /* Send Delete Objects Request */
        DeleteObjectsResult result = amazonS3.deleteObjects(request);
         
        /* Printing Deleted Object Keys */
        if (result.getDeletedObjects() != null) {
            result.getDeletedObjects().stream().forEach(e -> System.out.println(e.getKey()));
        }
         
    } catch (AmazonServiceException e) {
         
        System.out.println(e.getErrorMessage());
         
    } finally {
         
        if(amazonS3 != null) {
        	amazonS3.shutdown();
        }           
    }
				
		
    }

	@Override
	@Async
	public void listFilesFromBucket() {
		// TODO Auto-generated method stub
		
		try {   
            /* Get first batch of objects in a given bucket */
            ObjectListing objects = amazonS3.listObjects(bucketName);
             
            /* Recursively get all the objects inside given bucket */
            if(objects != null && objects.getObjectSummaries() != null) {               
                while (true) {                  
                    for(S3ObjectSummary summary : objects.getObjectSummaries()) {
                        System.out.println("Object Id :-" + summary.getKey());
                    }
                     
                    if (objects.isTruncated()) {
                        objects = amazonS3.listNextBatchOfObjects(objects);
                    } else {
                        break;
                    }                   
                }
            }
             
        } catch (AmazonServiceException e) {
             
            System.out.println(e.getErrorMessage());
             
        } finally {
             
            if(amazonS3 != null) {
            	amazonS3.shutdown();
            }           
        }
	}
	

	@Override
	@Async
	public void createBucket() {
		// TODO Auto-generated method stub
		 try {           
		        
		        /* Check if Bucket with given name is present or not */
		        if(!amazonS3.doesBucketExistV2("senior-design-3")) {
		             
		            /* Create an Object of CreateBucketRequest */
		            CreateBucketRequest request = new CreateBucketRequest("senior-design-3");
		             
		            /* Set Canned ACL as PublicRead */
		            request.setCannedAcl(CannedAccessControlList.PublicRead);
		             
		            /* Send Create Bucket Request */
		            Bucket result = amazonS3.createBucket(request);
		             
		            System.out.println("Bucket Name : " + result.getName());
		             
		            System.out.println("Creation Date : " + result.getCreationDate());
		             
		        } else {
		             
		            System.out.println("Bucket with given name is already present");
		             
		        }
		    } catch (AmazonServiceException e) {
		         
		        System.out.println(e.getErrorMessage());
		         
		    } finally {
		         
		        if(amazonS3 != null) {
		        	amazonS3.shutdown();
		        }           
		    }
	}

	@Override
	public void deleteS3Bucket() {
		// TODO Auto-generated method stub
			String bucket_name = "senior-design-3";
		 try {
	          
	            // Delete all objects from the bucket. This is sufficient
	            // for unversioned buckets. For versioned buckets, when you attempt to delete objects, Amazon S3 inserts
	            // delete markers for all objects, but doesn't delete the object versions.
	            // To delete objects from versioned buckets, delete all of the object versions before deleting
	            // the bucket (see below for an example).
	            ObjectListing objectListing = amazonS3.listObjects(bucket_name);
	            while (true) {
	                Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
	                while (objIter.hasNext()) {
	                	amazonS3.deleteObject(bucket_name, objIter.next().getKey());
	                }

	                // If the bucket contains many objects, the listObjects() call
	                // might not return all of the objects in the first listing. Check to
	                // see whether the listing was truncated. If so, retrieve the next page of objects 
	                // and delete them.
	                if (objectListing.isTruncated()) {
	                    objectListing = amazonS3.listNextBatchOfObjects(objectListing);
	                } else {
	                    break;
	                }
	            }

	            // Delete all object versions (required for versioned buckets).
	            VersionListing versionList = amazonS3.listVersions(new ListVersionsRequest().withBucketName(bucket_name));
	            while (true) {
	                Iterator<S3VersionSummary> versionIter = versionList.getVersionSummaries().iterator();
	                while (versionIter.hasNext()) {
	                    S3VersionSummary vs = versionIter.next();
	                    amazonS3.deleteVersion(bucketName, vs.getKey(), vs.getVersionId());
	                }

	                if (versionList.isTruncated()) {
	                    versionList = amazonS3.listNextBatchOfVersions(versionList);
	                } else {
	                    break;
	                }
	            }

	            // After all objects and object versions are deleted, delete the bucket.
	            amazonS3.deleteBucket(bucket_name);
	        } catch (AmazonServiceException e) {
	            // The call was transmitted successfully, but Amazon S3 couldn't process 
	            // it, so it returned an error response.
	            e.printStackTrace();
	        } catch (SdkClientException e) {
	            // Amazon S3 couldn't be contacted for a response, or the client couldn't
	            // parse the response from Amazon S3.
	            e.printStackTrace();
	        }
	}
	
	
	@Override
	public void emptyBucket() {
		// TODO Auto-generated method stub
		String bucket_name = "senior-design-2";
	 try {
          
            // Delete all objects from the bucket. This is sufficient
            // for unversioned buckets. For versioned buckets, when you attempt to delete objects, Amazon S3 inserts
            // delete markers for all objects, but doesn't delete the object versions.
            // To delete objects from versioned buckets, delete all of the object versions before deleting
            // the bucket (see below for an example).
            ObjectListing objectListing = amazonS3.listObjects(bucket_name);
            while (true) {
                Iterator<S3ObjectSummary> objIter = objectListing.getObjectSummaries().iterator();
                while (objIter.hasNext()) {
                	amazonS3.deleteObject(bucket_name, objIter.next().getKey());
                }

                // If the bucket contains many objects, the listObjects() call
                // might not return all of the objects in the first listing. Check to
                // see whether the listing was truncated. If so, retrieve the next page of objects 
                // and delete them.
                if (objectListing.isTruncated()) {
                    objectListing = amazonS3.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }

            // Delete all object versions (required for versioned buckets).
            VersionListing versionList = amazonS3.listVersions(new ListVersionsRequest().withBucketName(bucket_name));
            while (true) {
                Iterator<S3VersionSummary> versionIter = versionList.getVersionSummaries().iterator();
                while (versionIter.hasNext()) {
                    S3VersionSummary vs = versionIter.next();
                    amazonS3.deleteVersion(bucketName, vs.getKey(), vs.getVersionId());
                }

                if (versionList.isTruncated()) {
                    versionList = amazonS3.listNextBatchOfVersions(versionList);
                } else {
                    break;
                }
            }

            // After all objects and object versions are deleted, delete the bucket.
//            amazonS3.deleteBucket(bucket_name);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process 
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client couldn't
            // parse the response from Amazon S3.
            e.printStackTrace();
        } 
	}
	
}