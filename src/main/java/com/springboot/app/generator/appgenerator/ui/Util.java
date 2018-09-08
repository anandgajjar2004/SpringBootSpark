package com.springboot.app.generator.appgenerator.ui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Util {
	
	public static String getFieldName(String fieldName)
	{
		String fieldTotoLowerCase = "";		
		int position = fieldName.indexOf("_");
		if(position > 0)
		{
			fieldTotoLowerCase = fieldName.substring(0, position).toLowerCase() + fieldName.substring(position+1, position+2).toUpperCase() + fieldName.substring(position+2, fieldName.length());			
		}
		else
		{
			fieldTotoLowerCase = fieldName.toLowerCase();			
		}
		return fieldTotoLowerCase;
	}
	
	public static String getGetMethodName(String fieldName)
	{		
		String fieldTotoUpperCase = "";
		int position = fieldName.indexOf("_");
		if(position > 0)
		{	
			fieldTotoUpperCase = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, position).toLowerCase() + fieldName.substring(position+1, position+2).toUpperCase() + fieldName.substring(position+2, fieldName.length());
		}
		else
		{		
			fieldTotoUpperCase = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		}		
		return "get"+fieldTotoUpperCase;
	}
	
	public static String getSetMethodName(String fieldName)
	{		
		String fieldTotoUpperCase = "";
		int position = fieldName.indexOf("_");
		if(position > 0)
		{	
			fieldTotoUpperCase = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, position).toLowerCase() + fieldName.substring(position+1, position+2).toUpperCase() + fieldName.substring(position+2, fieldName.length());
		}
		else
		{		
			fieldTotoUpperCase = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		}		
		return "set"+fieldTotoUpperCase;
	}
	
	public static List<String> getFileStructure(String packageName)
	{		
		List<String> listOfFiles = new ArrayList<String>();		
		String filePath = packageName.replace(".", "/");		
		for(int i=0;i<filePath.length();i++)
		{
			if(filePath.charAt(i) == '/')
			{	
				listOfFiles.add(filePath.substring(0, i));				
			}
		}		
		listOfFiles.add(filePath);		
		return listOfFiles;
	}
	
	public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {		
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		
		fis.close();
	}
}
