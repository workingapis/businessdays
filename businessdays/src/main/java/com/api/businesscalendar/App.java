package com.api.businesscalendar;

import java.io.IOException; 

public class App 
{
	
	
    public static void main( String[] args ) throws IOException{
    	
    	
    	BusinessCalendar business  =BusinessCalendarFactory.getInstance("src/main/resources/templatexmlfile/templateBusinessCalendar.xml");
    	System.out.println(business);	
		 
    }
}
