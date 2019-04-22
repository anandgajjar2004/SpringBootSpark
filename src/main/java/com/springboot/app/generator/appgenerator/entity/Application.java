package com.springboot.app.generator.appgenerator.entity;

public class Application {
	private String applicationName;
	private String packageName;	
	private Entity entity;
		
	public Application(String applicationName, String packageName, Entity entity) {
		this.applicationName = applicationName;
		this.packageName = packageName;
		this.entity = entity;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}	
}