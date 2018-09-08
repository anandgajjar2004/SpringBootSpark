package com.springboot.app.generator.appgenerator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.generator.appgenerator.entity.Application;
import com.springboot.app.generator.appgenerator.logic.ClassGenerator;

@RestController
@RequestMapping("/rest/generate")
public class FileController {

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/octet-stream")
	public String  generateFile(@RequestBody Application application) {
		
		ClassGenerator generator = new ClassGenerator();
		generator.generate(application.getApplicationName(), application.getPackageName(), application.getListOfEntities());
		return "Success";
		
	}
	
	@RequestMapping(value = "/zip/{name}", method = RequestMethod.GET, produces = "application/pdf")
	public  ResponseEntity<InputStreamResource> generateFile1(@PathVariable("name") String name) {
		try 
		{		
		 String filePath = System.getProperty("user.dir");
		 File file = new File(filePath+"/"+name+".zip");

		 HttpHeaders respHeaders = new HttpHeaders();
		 respHeaders.setContentType(new MediaType("application", "octet-stream"));
		 //respHeaders.setContentLength(12345678);
		 respHeaders.setContentDispositionFormData("attachment", name+".zip");
         InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
         return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		 
		return null;
	}
}