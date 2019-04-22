package com.springboot.app.generator.appgenerator.entity;

public class Field {
	
	private String type;
	private String fieldName;
	private String displayName;
	private String setMethodName;
	private String getMethodName;
	
	public Field(String fieldName, String type, String displayName, String setMethodName, String getMethodName) {
		this.fieldName = fieldName;
		this.type = type;
		this.displayName  = displayName;
		this.setMethodName = setMethodName;
		this.getMethodName = getMethodName;		
	}
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getSetMethodName() {
		return setMethodName;
	}
	public void setSetMethodName(String setMethodName) {
		this.setMethodName = setMethodName;
	}
	public String getGetMethodName() {
		return getMethodName;
	}

	public void setGetMethodName(String getMethodName) {
		this.getMethodName = getMethodName;
	}	
}
