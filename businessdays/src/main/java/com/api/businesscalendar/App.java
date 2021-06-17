package com.api.businesscalendar;

import java.io.IOException; 
import java.time.LocalDate;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class App 
{
	private static Logger logger =  Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws IOException{
    	
    	// configure properties file with application
    	PropertyConfigurator.configure("src/main/resources/properties/log4j.properties");
    	
    	
    	BusinessCalendar business  =BusinessCalendarFactory.getInstance("src/main/resources/templatexmlfile/templateBusinessCalendar.xml");
    	logger.info(business.computeElapsedDays(LocalDate.now()));
		 
    }
}
