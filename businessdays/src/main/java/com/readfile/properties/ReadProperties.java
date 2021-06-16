package com.readfile.properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
	public static Properties readProperties(String pathToProperties) throws IOException {
			
			FileReader reader=new FileReader(pathToProperties);  
			
		    Properties p=new Properties(); 
		    p.load(reader);  
		    
			return p; 
		}
}
