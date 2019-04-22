package com.springboot.app.generator.appgenerator.entity;
import java.util.List;

public class Entity {
	
	private String name;
	private List<Field> listOfFields;
	private String entityNameInLowerCase;
	private String entityNameInPluralForm;


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

	public String getEntityNameInLowerCase() {
		return entityNameInLowerCase;
	}

	public void setEntityNameInLowerCase(String entityNameInLowerCase) {
		this.entityNameInLowerCase = entityNameInLowerCase;
	}

	public String getEntityNameInPluralForm() {
		return entityNameInPluralForm;
	}

	public void setEntityNameInPluralForm(String entityNameInPluralForm) {
		this.entityNameInPluralForm = entityNameInPluralForm;
	}
}
