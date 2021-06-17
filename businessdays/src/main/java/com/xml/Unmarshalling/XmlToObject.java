package com.xml.Unmarshalling;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class XmlToObject {
	
	private static Logger logger =  Logger.getLogger(XmlToObject.class);
	
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
			logger.error("error while unmarshling for " +"["+ theClass +"]"+ e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
}
