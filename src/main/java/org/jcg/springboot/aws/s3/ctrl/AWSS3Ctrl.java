package org.jcg.springboot.aws.s3.ctrl;

import java.util.HashMap;

import java.util.Map;

import org.jcg.springboot.aws.s3.serv.AWSS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value= "/s3")
public class AWSS3Ctrl {

	@Autowired
	private AWSS3Service service;
	
//	@GetMapping(value= "/upload")
//	public ResponseEntity<String> uploadFile()) {
//		service.uploadFile(multipartFile);
//		final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}
//	@GetMapping("upload")
	
	@RequestMapping(method = RequestMethod.GET, value = "/uploadDisplay")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/uploadtos3");
        return modelAndView;
    }
	
	@PostMapping(value= "/upload")
	public ResponseEntity<String> uploadFile(@RequestPart(value= "file") final MultipartFile multipartFile) {
		System.out.println("uploading....");
		service.uploadFile(multipartFile);
		final String response = "[" + multipartFile.getOriginalFilename() + "] uploaded successfully.";
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	  @DeleteMapping(value="/deleteFile")
	    public Map<String, String> deleteFile(@RequestParam("file_name") String fileName)
	    {
	        this.service.deleteFileFromS3Bucket(fileName);
		  	
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

	        return response;
	    }
	  
	  @GetMapping(value= "/list")
		public String listFiles() {
			this.service.listFilesFromBucket();
			final String response = "Listed all files successfully.";
			return response;
		}
	  
	  @PostMapping(value= "/createBucket")
		public Map<String,String> create_Bucket() {
			this.service.createBucket();
			 Map<String, String> response = new HashMap<>();
		        response.put("message", "Bucket Created Successfully.");
			return response;
		}
	  
	  	@DeleteMapping(value="/deleteBucket")
	    public Map<String, String> deleteBucket(){
	        this.service.deleteS3Bucket();
		  	
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Bucket removed request submitted successfully.");

	        return response;
	    }
	  	
	 	@DeleteMapping(value="/emptyBucket")
	    public Map<String, String> emptyBucket(){
	        this.service.emptyBucket();
		  	
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Empty Bucket request submitted successfully.");

	        return response;
	    }  
	 	
		@GetMapping(value= "/deploy")
		public String home() {
			return "Welcome to AWS First Deployment..!!";
		}
		
}
