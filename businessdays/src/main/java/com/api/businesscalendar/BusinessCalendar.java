package com.api.businesscalendar;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Predicate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.configurations.businessCalendarConfig.BusinessCalendarConfig;
import com.readfile.properties.ReadProperties;
import com.xml.Unmarshalling.XmlToObject;
import com.xml.validator.XMLValidation;

public final class BusinessCalendar {
    
	private static Logger logger =  Logger.getLogger(BusinessCalendar.class);
	
	static {
		// configure properties file with application
    	PropertyConfigurator.configure("src/main/resources/properties/log4j.properties");
    	logger.info("configure log4j propertis file with application place at  " + "src/main/resources/properties/log4j.properties");
	}
	
	
	private  BusinessCalendarConfig _businessCalendarConfig;
	private LocalDate _pivotDate;
	private ArrayList<LocalDate> _holidaysSet = new ArrayList<LocalDate>();
	private ArrayList<java.time.DayOfWeek> _workingDaySet = new ArrayList<java.time.DayOfWeek>();
	private long _workingSeconds=0;
	private long _startingSecond=0;
	
	// Constructor only visible to package level for factory 
	BusinessCalendar(String xmlConfigFilePath) throws IOException {
		try {	
			
			logger.info("read XSD file paths from properties file Properties property placed at  " + "src/main/resources/properties/paths.properties");
			// read paths from properties file Properties property
	        Properties  property = ReadProperties.readProperties("src/main/resources/properties/paths.properties");
	        
	    	final String shemaFile = property.getProperty("schemaFile");
	    	
	    	logger.info("validate XML file with XSD");
			// validate XML file with XSD
	    	boolean validate = XMLValidation.validate(xmlConfigFilePath , shemaFile);
	    	
	    	// Load XML configuration file 
			if(validate) { 
				logger.info("creating business calendar for further calculations");
				_businessCalendarConfig = XmlToObject.getConfigurationObject(xmlConfigFilePath,BusinessCalendarConfig.class); 
			}
						
			// Validate Configuration			
			// Compute localDate format to ease date manipulation
			this._pivotDate = LocalDate.of(
					_businessCalendarConfig.getPivotDate().getYear(), 
					_businessCalendarConfig.getPivotDate().getMonth(), 
					_businessCalendarConfig.getPivotDate().getDay());
			
			// Compute localDate format to ease date manipulation			 
			for (XMLGregorianCalendar gcDate : this._businessCalendarConfig.getHolidays().getHoliday()) {
				this._holidaysSet.add(LocalDate.of(
						gcDate.getYear(), 
						gcDate.getMonth(), 
						gcDate.getDay() ) ) ;
			}
			
			// Move to Java DayOfWeek format to ease date manipulation			 
			for (com.configurations.businessCalendarConfig.DayOfWeek dow : this._businessCalendarConfig.getBusinessDays().getBusinessDay()) {
				this._workingDaySet.add(java.time.DayOfWeek.valueOf(dow.toString()));				
			}
			
			// Working Hours check
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			// time difference between endWorkTime and startWorkTime in order to get working time per day
	  		this._workingSeconds =  LocalTime.parse(_businessCalendarConfig.getBusinessHours().getStartHourMinute(), formatter)
	  				   			.until(LocalTime.parse(_businessCalendarConfig.getBusinessHours().getEndHourMinute(), formatter), ChronoUnit.SECONDS);
	  		this._startingSecond = LocalTime.parse("00:00", formatter)
			   					.until(LocalTime.parse(_businessCalendarConfig.getBusinessHours().getStartHourMinute(), formatter), ChronoUnit.SECONDS);
			
		} catch (RuntimeException e) {
			// Something went wrong when reading configuration file 
			logger.error("Error when reading file [" + xmlConfigFilePath +"] : " + e.getMessage());
			throw(e);
		} 		
	}

	/**
	 * Compute the elapsed days between given date and pivot Date from config file
	 * 
	 * @param inputDate
	 * @return
	 */
	public long computeElapsedDays(LocalDate inputDate) {	 
		return getAllWorkingDays( this._pivotDate, inputDate, this._workingDaySet, this._holidaysSet); 								
	}
	
	/**
	 * Compute the elapsed seconds between given date and pivot Date from config file
	 * 
	 * @param inputDate
	 * @return
	 */
	
	public long computeElapsedSeconds(LocalDateTime inputDate) {	 
		return getWorkingSeconds(this._pivotDate,inputDate, this._workingDaySet, this._holidaysSet,this._workingSeconds, this._startingSecond); 								
	}
	
	/**
     *   provide nb of business seconds between two given dates 
     *   
	 * @param startDate			Start Date to consider (Pivot Date)
	 * @param endDate			End Date to consider
	 * @param bizDaysSet		Set of Days defined as Working Days
	 * @param holidaysSet		list of Holidays since Pivot date
	 * @param workingSeconds	Total Business Hours in second
	 * @param startingSeconds	Start Business Hour in second
	 * 
	 * @return totalWorkingSecond between the 2 dates
	 */
    private static long getWorkingSeconds(final LocalDate startDate,final LocalDateTime endDate, ArrayList<DayOfWeek> bizDaysSet, ArrayList<LocalDate> holidaysSet, long workingSeconds, long startingSeconds){ 
           // Total working days : all the working days between start date and end date (exclude holidays, weekends)
    	  long totalWorkingDays = getAllWorkingDays(startDate,  endDate.toLocalDate(),  bizDaysSet,  holidaysSet);
  		   
  		   //Evaluate timestamp of the given date into seconds
  		   long inputDateTimeToSecond = endDate.getHour()*3600 + endDate.getMinute()*60 + endDate.getSecond() - startingSeconds;
  		     		   
  		   // final calculation for the total working  seconds elapsed between given date and pivot Date
  		  return (totalWorkingDays*workingSeconds + java.lang.Math.min(workingSeconds, java.lang.Math.max(inputDateTimeToSecond, 0)));
  		   
	}

    /**
     *   provide nb of business days between two given dates 
     *   
	 * @param startDate			Start Date to consider (Pivot Date)
	 * @param endDate			End Date to consider
	 * @param bizDaysSet		Set of Days defined as Working Days
	 * @param holidaysSet		list of Holidays since Pivot date
     * 
     * @return nb working days between two dates 
     */
    private static long getAllWorkingDays(final LocalDate startDate, final LocalDate endDate, final ArrayList<DayOfWeek> bizDaysSet, final ArrayList<LocalDate> holidaysSet ){ 
 	   
    	// Define logic for Business Days acceptance 
	   Predicate<LocalDate> isBusinessDay = new Predicate<LocalDate>() 
       {
            public boolean test(LocalDate localDate) {
            	// Date has to be a Working Day and not be part of Holidays
                if (bizDaysSet.contains(localDate.getDayOfWeek()) && !holidaysSet.contains(localDate)) {
                    return true;
                } 
                return false;
            }
       };
       
       try {
   		   // Evaluate all dates between starting date (included) and end date (excluded)
    	   return startDate.datesUntil(endDate)
		   		// Filter using predefined predicate 
		   		.filter(isBusinessDay)
		   		.count();
    	   
    	   }catch(IllegalArgumentException  e) {    		   
    		   // Should only occurs if end Date is before/equals start Date
    		   // It means pivot Date is not compatible with the data set!    		   
    		   logger.error("Date provided [" + endDate.toString() + "] is before/equal start date [" + startDate.toString() + "] : " + e.getMessage());
    		   throw e;
    	   }
	    }
}

