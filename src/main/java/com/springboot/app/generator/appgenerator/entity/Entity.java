package com.springboot.app.generator.appgenerator.entity;
import java.util.List;

public class Entity {
	
	private String name;
	private List<Field> listOfFields;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Field> getListOfFields() {
		return listOfFields;
	}

	public void setListOfFields(List<Field> listOfFields) {
		this.listOfFields = listOfFields;
	}
	
	

}
