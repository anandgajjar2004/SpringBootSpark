package com.springboot.app.generator.appgenerator.entity;

import java.util.List;



public class Application {
	
	private String packageName;
	private String applicationName;
	private List<Entity> listOfEntities;
	
	
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public List<Entity> getListOfEntities() {
		return listOfEntities;
	}
	public void setListOfEntities(List<Entity> listOfEntities) {
		this.listOfEntities = listOfEntities;
	}
	
	
	

}
