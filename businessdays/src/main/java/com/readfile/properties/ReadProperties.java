package com.readfile.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ReadProperties {
	
	private static Logger logger =  Logger.getLogger(ReadProperties.class);
	
	public static Properties readProperties(String pathToProperties) {
			
			FileReader reader = null;
			try {
				reader = new FileReader(pathToProperties);
			} catch (FileNotFoundException e) {
			    logger.error("properties file not found, please put properties file at root folder level  "+ "src/main/resources/properties/log4j.properties" + e.getMessage());
				throw new RuntimeException(e.getMessage());
			}  
		    Properties p=new Properties(); 
		    try {
				p.load(reader);
			} catch (IOException e) {
				logger.error("Not able to interprate the properties file" + e.getMessage());
				throw new RuntimeException(e.getMessage());
			}  
		    
			return p; 
		}
}
