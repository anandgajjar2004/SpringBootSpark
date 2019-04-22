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
import com.springboot.app.generator.appgenerator.entity.Field;
import com.springboot.app.generator.appgenerator.logic.ClassGenerator;

@RestController
@RequestMapping("/rest/generate")
public class FileController {

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/octet-stream")
	public String  generateFile(@RequestBody Application application) {
		
		String entityName = application.getEntity().getName();
		entityName  = entityName.substring(0, 1).toUpperCase().concat(entityName.substring(1, entityName.length()));
		String entityNameInLowerCase = entityName.toLowerCase();
		String entityNameInPluralForm = null;
		if(entityNameInLowerCase.substring(entityNameInLowerCase.length()-1).equals("y")) {
			entityNameInPluralForm = entityNameInLowerCase.substring(0, entityNameInLowerCase.length()-1)+"ies";
		} else {
			entityNameInPluralForm = entityNameInLowerCase+"s";
		}
		
		for(Field field : application.getEntity().getListOfFields()) {
				String fieldName = field.getFieldName();
				String camelCaseString = "";
				if(fieldName.contains("_")) {
					if(fieldName.split("_").length > 1) {
						for(String  s: fieldName.split("_")) {
							camelCaseString = camelCaseString + s.substring(0, 1).toUpperCase() +s.substring(1).toLowerCase();
						}
						fieldName = camelCaseString.substring(0, 1).toLowerCase().concat(camelCaseString.substring(1, camelCaseString.length()));
					} else {					
						fieldName = fieldName.substring(0, 1).toLowerCase().concat(fieldName.substring(1, fieldName.length()));
					}					
				} else if(fieldName.contains(" ")) {
					if(fieldName.split(" ").length > 1) {
						for(String  s: fieldName.split(" ")) {
							camelCaseString = camelCaseString + s.substring(0, 1).toUpperCase() +s.substring(1).toLowerCase();
						}
						fieldName = camelCaseString.substring(0, 1).toLowerCase().concat(camelCaseString.substring(1, camelCaseString.length()));
					} else {					
						fieldName = fieldName.substring(0, 1).toLowerCase().concat(fieldName.substring(1, fieldName.length()));
					}
				} else {
					fieldName = fieldName.substring(0, 1).toLowerCase().concat(fieldName.substring(1, fieldName.length()));
				}
				field.setFieldName(fieldName);
				String type = field.getType();								
				type = type.substring(0, 1).toUpperCase() + type.substring(1, type.length());
				String displayName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()).replaceAll("(.)([A-Z])", "$1 $2");
				String setMethodName =  "set"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
				String getMethodName =  "get"+fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
				
				field.setDisplayName(displayName);
				field.setSetMethodName(setMethodName);
				field.setGetMethodName(getMethodName);				
			}
		application.getEntity().setEntityNameInLowerCase(entityNameInLowerCase);
		application.getEntity().setEntityNameInPluralForm(entityNameInPluralForm);
		
		ClassGenerator generator = new ClassGenerator(application);
		generator.generate();
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