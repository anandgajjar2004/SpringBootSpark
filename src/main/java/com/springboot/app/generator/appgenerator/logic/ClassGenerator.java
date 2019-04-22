package com.springboot.app.generator.appgenerator.logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

import com.springboot.app.generator.appgenerator.entity.Application;
import com.springboot.app.generator.appgenerator.entity.Entity;
import com.springboot.app.generator.appgenerator.entity.Field;
import com.springboot.app.generator.appgenerator.ui.Util;

public class ClassGenerator {
		
	Entity entity;
	String packageName;
	List<Field> fields;
	String filePath;
	String applicationName;
	
	public ClassGenerator(Application application) {
		this.entity = application.getEntity();
		this.packageName = application.getPackageName();
		this.fields = application.getEntity().getListOfFields();
		this.applicationName = application.getApplicationName();
	}
	
	public void generate() {
		
		try
		{
		//Delete Existing Zip File
		File file = new File(applicationName+".zip");
		file.delete();
			
		//Create New Zip File
		FileOutputStream fos = new FileOutputStream(applicationName+".zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
		
		//Get List of Folder and Sub Folder name from Package
		List<String> list = Util.getFileStructure(packageName);
		filePath = list.get(list.size()-1);
		
		
		//Create List of Folder and Sub Folder name from Package
		for(String s : list)
		{
			File dir = new File(s);
			dir.mkdir();
		}		
		
		String entityDirectory = filePath+"/domain";
		File dir = new File(entityDirectory);			
		dir.mkdir();
		String repositoryDirectory = filePath+"/repository";
		dir = new File(repositoryDirectory);			
		dir.mkdir();
		String controllerDirectory = filePath+"/controller";
		dir = new File(controllerDirectory);			
		dir.mkdir();
		String serviceInterfaceDirectory = filePath+"/service";
		dir = new File(serviceInterfaceDirectory);			
		dir.mkdir();
		String serviceImplementationDirectory = filePath+"/service/impl";
		dir = new File(serviceImplementationDirectory);			
		dir.mkdir();
		String templateDirectory = "templates";		
		dir = new File(templateDirectory);
		dir.mkdir();
		String templateEntityDirectory = templateDirectory+"/"+entity.getEntityNameInLowerCase();
		dir = new File(templateEntityDirectory);			
		dir.mkdir();
		
		
	            			    
		String fileNane = createEntityClass(entityDirectory);
		Util.addToZipFile(fileNane, zos);	
		fileNane = createRepositoryInterface(repositoryDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createController(controllerDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createServiceInterface(serviceInterfaceDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createServiceInterfaceImplementation(serviceImplementationDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createFormHtmlFile(templateEntityDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createAddHtmlFile(templateEntityDirectory);		
		Util.addToZipFile(fileNane, zos);
		fileNane = createEditHtmlFile(templateEntityDirectory);		
		Util.addToZipFile(fileNane, zos);		
		fileNane = createViewHtmlFile(templateEntityDirectory);
		Util.addToZipFile(fileNane, zos);
		fileNane = createListHtmlFile(templateEntityDirectory);
		Util.addToZipFile(fileNane, zos);		
		zos.close();
		fos.close();
		
		FileUtils.deleteDirectory(new File(list.get(0)));
		FileUtils.deleteDirectory(new File("templates"));
		}
		catch(Exception se){
			se.printStackTrace();
	    }	
	}			
	
	private String createEntityClass(String entityDirectory)
	{
		String fileName = entityDirectory+"/"+entity.getName()+ ".java";
		
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(System.lineSeparator());
			fileWriter.write("package "+packageName+".domain;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.io.Serializable;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import javax.persistence.Entity;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import javax.persistence.GeneratedValue;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import javax.persistence.Id;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("@Entity");			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("public class " + entity.getName() + " implements Serializable {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Id");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GeneratedValue");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tprivate Long id;");
			fileWriter.write(System.lineSeparator());
			
			for (Field f : fields) {				
				fileWriter.write("\tprivate " + f.getType() +" "+f.getFieldName()+";");
				fileWriter.write(System.lineSeparator());
			}
			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic "+entity.getName()+"() {}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			
			
			fileWriter.write("\tpublic Long getId() {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn id;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic void setId(Long id) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tthis.id = id;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			for (Field f : fields) {	
				fileWriter.write("\tpublic "+f.getType()+" "+f.getGetMethodName()+"() {");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\treturn "+f.getFieldName()+";");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t}");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\tpublic void "+f.getSetMethodName()+"("+f.getType()+" "+f.getFieldName()+") {");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\tthis."+f.getFieldName()+" = "+f.getFieldName()+";");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t}");
				fileWriter.write(System.lineSeparator());					
			}
			
			fileWriter.write("}");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createRepositoryInterface(String repositoryDirectory)
	{
		String fileName = repositoryDirectory+"/"+entity.getName() + "Repository.java";
		try {						
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write("package "+packageName+".repository;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.data.jpa.repository.JpaRepository;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".domain."+entity.getName()+";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("public interface "+entity.getName()+"Repository extends JpaRepository<"+entity.getName()+", Long> {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("}");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}		
		return fileName;
	}
	
	private String createServiceInterface(String serviceInterfaceDirectory)
	{
		String fileName = serviceInterfaceDirectory+"/"+entity.getName() + "Service.java";
		try {			
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write("package "+packageName+".service;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".domain."+entity.getName()+";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.util.List;");
			fileWriter.write("import java.util.Optional;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("public interface "+entity.getName()+"Service {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic "+entity.getName()+" save("+entity.getName()+" "+entity.getEntityNameInLowerCase()+");");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic List<"+entity.getName()+"> findAll();");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic Optional<"+entity.getName()+"> findById(Long id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic void deleteById(Long id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("}");
			
			
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}	
	
	private String createServiceInterfaceImplementation(String serviceImplementationDirectory)
	{
		String fileName = serviceImplementationDirectory+"/"+entity.getName() + "SerciceImpl.java";
		try {		
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write("package "+packageName+".service.impl;");
			fileWriter.write(System.lineSeparator());					
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.util.List;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.stereotype.Service;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.util.Optional;");					
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.beans.factory.annotation.Autowired;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".domain."+entity.getName()+";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".repository."+entity.getName()+"Repository;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".service."+entity.getName()+"Service;");			
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("@Service");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("public class "+entity.getName()+"SerciceImpl implements "+entity.getName()+"Service {");
			fileWriter.write(System.lineSeparator());			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Autowired");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t"+entity.getName()+"Repository "+entity.getEntityNameInLowerCase()+"Repository;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Override");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic "+entity.getName()+" save("+entity.getName()+" "+entity.getEntityNameInLowerCase()+") {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn "+entity.getEntityNameInLowerCase()+"Repository.save("+entity.getEntityNameInLowerCase()+");");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Override");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic List<"+entity.getName()+"> findAll() {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tList<"+entity.getName()+"> list = "+entity.getEntityNameInLowerCase()+"Repository.findAll();");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn list;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Override");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic void deleteById(Long id) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t"+entity.getEntityNameInLowerCase()+"Repository.deleteById(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Override");
			fileWriter.write("\tpublic Optional<"+entity.getName()+"> findById(Long id) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn "+entity.getEntityNameInLowerCase()+"Repository.findById(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());			
			fileWriter.write("}");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createController(String controllerDirectory)
	{		
		String fileName = controllerDirectory+"/"+entity.getName() + "Controller.java";
		try {
			FileWriter fileWriter = new FileWriter(fileName);			
			fileWriter.write("package "+packageName+".controller;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.util.List;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import java.util.Optional;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import javax.validation.Valid;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.beans.factory.annotation.Autowired;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.stereotype.Controller;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.ui.Model;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.validation.BindingResult;");						
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.web.bind.annotation.GetMapping;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.web.bind.annotation.ModelAttribute;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.web.bind.annotation.PathVariable;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import org.springframework.web.bind.annotation.PostMapping;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".domain."+entity.getName()+";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("import "+packageName+".service."+entity.getName()+"Service;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("@Controller");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("public class "+entity.getName()+"Controller {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@Autowired");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tprivate "+entity.getName()+"Service "+entity.getEntityNameInLowerCase()+"Service;");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GetMapping(value = {\"/"+entity.getEntityNameInPluralForm()+"\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String getListOf"+entity.getName()+"(Model model) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tList<"+entity.getName()+"> list = "+entity.getEntityNameInLowerCase()+"Service.findAll();");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tmodel.addAttribute(\""+entity.getEntityNameInPluralForm()+"\", list);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \""+entity.getEntityNameInLowerCase()+"/index\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GetMapping(value = {\"/"+entity.getEntityNameInLowerCase()+"/new\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String add"+entity.getName()+"("+entity.getName()+" "+entity.getEntityNameInLowerCase()+") {");
			fileWriter.write(System.lineSeparator());		
			fileWriter.write("\t\treturn \""+entity.getEntityNameInLowerCase()+"/new\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GetMapping(value = {\"/"+entity.getEntityNameInLowerCase()+"/show/{id}\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String get"+entity.getName()+"ById(@PathVariable Long id, Model model) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tOptional<"+entity.getName()+"> "+entity.getEntityNameInLowerCase()+" = "+entity.getEntityNameInLowerCase()+"Service.findById(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tmodel.addAttribute(\""+entity.getEntityNameInLowerCase()+"\", "+entity.getEntityNameInLowerCase()+".get());");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \""+entity.getEntityNameInLowerCase()+"/show\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@PostMapping(value = {\"/"+entity.getEntityNameInPluralForm()+"\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String save"+entity.getName()+"(@Valid "+entity.getName()+" "+entity.getEntityNameInLowerCase()+", BindingResult result) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tif (result.hasErrors()) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t\treturn \""+entity.getEntityNameInLowerCase()+"/new\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t"+entity.getEntityNameInLowerCase()+"Service.save("+entity.getEntityNameInLowerCase()+");");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \"redirect:/"+entity.getEntityNameInPluralForm()+"/\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GetMapping(value = {\"/"+entity.getEntityNameInLowerCase()+"/edit/{id}\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String edit"+entity.getName()+"ById(@PathVariable Long id, Model model) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tOptional<"+entity.getName()+"> "+entity.getEntityNameInLowerCase()+" = "+entity.getEntityNameInLowerCase()+"Service.findById(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tmodel.addAttribute(\""+entity.getEntityNameInLowerCase()+"\", "+entity.getEntityNameInLowerCase()+".get());");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \""+entity.getEntityNameInLowerCase()+"/edit\";");					
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@PostMapping(value = {\"/"+entity.getEntityNameInPluralForm()+"/{id}\"})");
			fileWriter.write(System.lineSeparator());			
			fileWriter.write("\tpublic String update"+entity.getName()+"(@PathVariable Long id, @Valid "+entity.getName()+" "+entity.getEntityNameInLowerCase()+", BindingResult result) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\tif (result.hasErrors()) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t\treturn \""+entity.getEntityNameInLowerCase()+"/edit\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t"+entity.getEntityNameInLowerCase()+".setId(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t"+entity.getEntityNameInLowerCase()+"Service.save("+entity.getEntityNameInLowerCase()+");");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \"redirect:/"+entity.getEntityNameInPluralForm()+"/\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t@GetMapping(value = {\"/"+entity.getEntityNameInLowerCase()+"/delete/{id}\"})");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\tpublic String delete"+entity.getName()+"(@PathVariable Long id) {");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t"+entity.getEntityNameInLowerCase()+"Service.deleteById(id);");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\treturn \"redirect:/"+entity.getEntityNameInPluralForm()+"/\";");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t}");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("}");
					
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	public void addStyleSheet(FileWriter fileWriter) {
		try {
		fileWriter.write("\t\t<style type=\"text/css\">");
		fileWriter.write(System.lineSeparator());
		fileWriter.write("\t\t\tbody  {  font-family: verdana, arial, helvetica, sans-serif; font-size: 13px; line-height: 18px; margin: 10px; }");
		fileWriter.write(System.lineSeparator());
		fileWriter.write("\t\t\tlabel { display: block; }");
		fileWriter.write(System.lineSeparator());
		fileWriter.write("\t\t\tth    { padding-bottom: 5px; }");
		fileWriter.write(System.lineSeparator());
		fileWriter.write("\t\t\ttd    { padding-bottom: 7px; padding-left: 5px; padding-right: 5px; }");
		fileWriter.write(System.lineSeparator());
		fileWriter.write("\t\t</style>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String createFormHtmlFile(String templateEntityDirectory) 
	{
		String fileName = templateEntityDirectory + "/_form.html";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			
			fileWriter.write("<div th:fragment=\""+entity.getEntityNameInLowerCase()+"Form\">");
			fileWriter.write(System.lineSeparator());
			for (Field f : fields) {
				fileWriter.write("\t<div class=\"form-group\">");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\t<label for=\""+f.getFieldName()+"\">"+f.getDisplayName()+"</label>");
				fileWriter.write(System.lineSeparator());

				if(f.getType().equals("String")) {
					fileWriter.write("\t\t<input type=\"text\" th:field=\"*{"+f.getFieldName()+"}\" id=\""+f.getFieldName()+"\" class=\"form-control\">");	
				}
				else if(f.getType().equals("Integer") || f.getType().equals("Long") ||  f.getType().equals("Double") || f.getType().equals("BigDecimal")) {
					fileWriter.write("\t\t<input type=\"number\" th:field=\"*{"+f.getFieldName()+"}\" id=\""+f.getFieldName()+"\" class=\"form-control\">");
				} else if(f.getType().equals("boolean")) {
					fileWriter.write("\t\t<input type=\"checkbox\" th:field=\"*{"+f.getFieldName()+"}\" id=\""+f.getFieldName()+"\" class=\"form-control\">");
				}
				
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\t<span th:if=\"${#fields.hasErrors('"+f.getFieldName()+"')}\" th:errors=\"*{"+f.getFieldName()+"}\"></span>");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t</div>");	
				fileWriter.write(System.lineSeparator());
			}			
			fileWriter.write("</div>");			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createAddHtmlFile(String templateEntityDirectory)
	{	
		File dir = new File("templates");
		dir.mkdir();
		
		String fileName = templateEntityDirectory + "/new.html";
		try {
			FileWriter fileWriter = new FileWriter(fileName);			
			fileWriter.write("<!DOCTYPE html>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<head>");			
			fileWriter.write(System.lineSeparator());
			addStyleSheet(fileWriter);
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t<title>Add "+entity.getName()+"</title>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t</head>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<form action=\"#\" th:action=\"@{/"+entity.getEntityNameInPluralForm()+"/}\" th:object=\"${"+entity.getEntityNameInLowerCase()+"}\" method=\"post\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<div th:replace=\""+entity.getEntityNameInLowerCase()+"/_form :: "+entity.getEntityNameInLowerCase()+"Form\">...</div>");			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<br/>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<input type=\"submit\" value=\"Add "+entity.getName()+"\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</form>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</html>");			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createEditHtmlFile(String templateEntityDirectory)
	{	
		File dir = new File("templates");
		dir.mkdir();
		
		String fileName = templateEntityDirectory + "/edit.html";
		try {			
			FileWriter fileWriter = new FileWriter(fileName);			
			fileWriter.write("<!DOCTYPE html>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<head>");			
			fileWriter.write(System.lineSeparator());
			addStyleSheet(fileWriter);
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t<title>Add "+entity.getName()+"</title>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t</head>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<form action=\"#\" th:action=\"@{/"+entity.getEntityNameInPluralForm()+"/{id}(id=${student.id})}\" th:object=\"${"+entity.getEntityNameInLowerCase()+"}\" method=\"post\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<div th:replace=\""+entity.getEntityNameInLowerCase()+"/_form :: "+entity.getEntityNameInLowerCase()+"Form\">...</div>");			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<br/>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<input type=\"hidden\" th:field=\"*{id}\" id=\"id\" class=\"form-control\">");			
			fileWriter.write("\t<input type=\"submit\" value=\"Update "+entity.getName()+"\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</form>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</html>");			
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createViewHtmlFile(String templateEntityDirectory)
	{		
		String fileName = templateEntityDirectory + "/show.html";
		try {
			FileWriter fileWriter = new FileWriter(fileName);			
			fileWriter.write("<!DOCTYPE html>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<head>");			
			fileWriter.write(System.lineSeparator());
			addStyleSheet(fileWriter);
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t<title>Show "+entity.getName()+"</title>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t</head>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<h5>"+entity.getName()+"</h5>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<table>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<thead>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t\t<tbody>");
			fileWriter.write(System.lineSeparator());
			for (Field f : fields) {
				fileWriter.write("\t\t\t<tr>");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\t\t\t<td>"+f.getDisplayName()+"</td><td th:text=\"${"+entity.getEntityNameInLowerCase()+"."+f.getFieldName()+"}\"></td>");
				fileWriter.write(System.lineSeparator());
				fileWriter.write("\t\t\t</tr>");
				fileWriter.write(System.lineSeparator());
			}
			fileWriter.write("\t\t</tbody>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t</table>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("\t<a th:href=\"@{/"+entity.getEntityNameInLowerCase()+"/edit/{id}(id=${"+entity.getEntityNameInLowerCase()+".id})}\">Edit</a>");			
			fileWriter.write("</body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</html>");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
	
	private String createListHtmlFile(String templateEntityDirectory)
	{		
		String fileName = templateEntityDirectory + "/index.html";
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write("<!DOCTYPE html>");			
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<head>");						
			fileWriter.write(System.lineSeparator());
			addStyleSheet(fileWriter);
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<title>List of "+entity.getName()+"</title>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</head>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<h2>"+entity.getEntityNameInPluralForm()+"</h2>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<table>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<thead>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<tr>");
			fileWriter.write(System.lineSeparator());
			for (Field f : fields) {				
				fileWriter.write("<th>"+f.getDisplayName()+"</th>");
				fileWriter.write(System.lineSeparator());
			}
			fileWriter.write("<th colspan=\"3\">");
			fileWriter.write(System.lineSeparator());
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</th>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</tr>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</thead>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<tbody>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<tr th:each=\""+entity.getEntityNameInLowerCase()+" : ${"+entity.getEntityNameInPluralForm()+"}\">");
			fileWriter.write(System.lineSeparator());			
			for (Field f : fields) {					
				fileWriter.write("<td><a th:text=\"${"+entity.getEntityNameInLowerCase()+"."+f.getFieldName()+"}\"></a></td>");
				fileWriter.write(System.lineSeparator());
			}
			fileWriter.write("<td><a th:href=\"@{/"+entity.getEntityNameInLowerCase()+"/show/{id}(id=${"+entity.getEntityNameInLowerCase()+".id})}\">Show</a></td><td><a th:href=\"@{/"+entity.getEntityNameInLowerCase()+"/edit/{id}(id=${"+entity.getEntityNameInLowerCase()+".id})}\">Edit</a></td><td><a th:href=\"@{/"+entity.getEntityNameInLowerCase()+"/delete/{id}(id=${"+entity.getEntityNameInLowerCase()+".id})}\">Delete</a></td>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</tr>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</tbody>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</table>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("<p><a href=\"/"+entity.getEntityNameInLowerCase()+"/new\">Add "+entity.getName()+"</a>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</body>");
			fileWriter.write(System.lineSeparator());
			fileWriter.write("</html>");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return fileName;
	}
}