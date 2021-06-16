package com.xml.Unmarshalling;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;

public class XmlToObject {
	
	@SuppressWarnings("unchecked")
	public static <T>T getConfigurationObject(String configPath , Class<?> theClass){
		 
		    File file = new File(configPath);  
	      
	        Object jaxbElement = null;
			JAXBContext jc = null;
			Unmarshaller um = null;
			
		try {
			
			jc = JAXBContext.newInstance(new Class[] { theClass });
			um = jc.createUnmarshaller();
			jaxbElement =  um.unmarshal(file);
			
			T result = (T) JAXBIntrospector.getValue(jaxbElement); //(T) um.unmarshal(file);
			
			return result;
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
}
