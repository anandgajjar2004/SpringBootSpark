package com.springboot.app.generator.appgenerator.logic;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

import com.springboot.app.generator.appgenerator.entity.Entity;
import com.springboot.app.generator.appgenerator.entity.Field;
import com.springboot.app.generator.appgenerator.ui.Util;

public class ClassGenerator {
	public void generate(String applicationName, String packgeName, List<Entity> entityList) {
		
		try
		{			
		FileOutputStream fos = new FileOutputStream(applicationName+".zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
		
		for(Entity entity : entityList)
		{
		List<Field> fields = entity.getListOfFields();
		String entityName = entity.getName();
		String packageName = packgeName;
		List<String> list = Util.getFileStructure(packageName);
		
		for(String s : list)
		{
			File dir = new File(s);
			dir.mkdir();
		}							
	            			    

		ClassGenerator todayClass = new ClassGenerator();
		String fileNane = todayClass.createEntityClass(packageName,  list.get(list.size()-1), entityName, fields);
		Util.addToZipFile(fileNane, zos);	
		fileNane = todayClass.createRepositoryInterface(packageName,list.get(list.size()-1),  entityName, fields);
		Util.addToZipFile(fileNane, zos);
		fileNane = todayClass.createController(packageName,list.get(list.size()-1),  entityName, fields);
		Util.addToZipFile(fileNane, zos);
			
		}
		zos.close();
		fos.close();
		}
		catch(Exception se){
			se.printStackTrace();
	    }	
	}			
	
	public String createEntityClass(String packageStructure, String filePath, String entityName, List<Field> fields)
	{
		String fileName = filePath+"/domain/"+entityName + ".java";
		
		try {
			File dir = new File(filePath+"/domain");			
			dir.mkdir();

			FileWriter aWriter = new FileWriter(fileName);
			aWriter.write("package "+packageStructure+".domain;\nimport javax.persistence.*;\nimport java.io.Serializable;\n");
			aWriter.write("@Entity\npublic class " + entityName + " implements Serializable {\n");
			aWriter.write("\n@Id\n@GeneratedValue\nprivate Long id;\n");
			for (Field f : fields) {				
				aWriter.write("private " + f.getType() +" "+Util.getFieldName(f.getFieldName())+";\n");				
			}
			
			aWriter.write("\npublic "+entityName+"() {}\n\n");
			
			aWriter.write("public Long getId() { \nreturn id;\n}\n\npublic void setId(Long id) {\nthis.id = id;}\n");
			
			for (Field f : fields) {	
				aWriter.write("public "+f.getType()+" "+Util.getGetMethodName(f.getFieldName())+"() {\n\t"
						          + "return "+Util.getFieldName(f.getFieldName())+";\n}\n\n"
						          + "public void "+Util.getSetMethodName(f.getFieldName())+"("+f.getType()+" "+Util.getFieldName(f.getFieldName())+") {\n \t"
						          		+ "this."+Util.getFieldName(f.getFieldName())+" = "+Util.getFieldName(f.getFieldName())+";\n}\n");				
			}
			
			aWriter.write("}");
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}
	
	public String createRepositoryInterface(String packageStructure, String filePath, String entityName, List<Field> fields)
	{
		String fileName = filePath+"/service/"+entityName + "Repository.java";
		try {						
			File dir = new File(filePath+"/service");			
			dir.mkdir();
			
			FileWriter aWriter = new FileWriter(fileName);
			aWriter.write("package com."+packageStructure+".springboot.service;\nimport java.util.List;\nimport org.springframework.data.repository.CrudRepository;\n\n");
			aWriter.write("public interface "+entityName+"Repository extends CrudRepository<"+entityName+", Long> {\n}");
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) { 
			e.printStackTrace();
		}		
		return fileName;
	}
	
	public String createController(String packageStructure, String filePath, String entityName, List<Field> fields)
	{		
		String fileName = filePath+"/web/"+entityName + "Controller.java";
		try {
			File dir = new File(filePath+"/web");			
			dir.mkdir();
			
			FileWriter aWriter = new FileWriter(fileName);			
			aWriter.write("package com."+packageStructure+".springboot.web;\n@RestController\n@RequestMapping(/\""+entityName.toLowerCase()+"\")\npublic class "+entityName+"Controller {\n\n");
			
			//fields
			aWriter.write("\t@Autowiredprivate "+entityName+"Repository "+entityName.toLowerCase()+"Repository;\n\n");
			
			//get
			aWriter.write("\t@RequestMapping(\"/\")\n\tpublic List<"+entityName+"> get"+entityName+"() {\n\tList<"+entityName+"> list = "+entityName.toLowerCase()+"Repository.findAll();\n\treturn list;\n\t}\n\n");
			aWriter.write("\tpublic "+entityName+" get"+entityName+"(@PathVariable(\"id\") String id) \n\t{"
					+ "\t"+entityName+" "+entityName.toLowerCase()+" = "+entityName.toLowerCase()+"Repository.findById(Long.parseLong(id));"	
					+ "\n\treturn customer;\n \t}\n\n");
			
			aWriter.write("\t@RequestMapping(value = \"/{id}\", method = RequestMethod.DELETE)\n"
					+"\tpublic void delete"+entityName+"(@PathVariable String id) {\n"
					+"\t"+entityName+" "+entityName.toLowerCase()+" = "+entityName.toLowerCase()+"Repository.findOne(Long.parseLong(id));\n"		
					+"\t"+entityName.toLowerCase()+"Repository.save("+entityName.toLowerCase()+");\n"
					+"\t}");						
					aWriter.write("\n\t}");
			aWriter.flush();
			aWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}