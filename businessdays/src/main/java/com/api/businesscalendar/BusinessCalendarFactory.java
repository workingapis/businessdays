package com.api.businesscalendar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Factory for BusinessCalendar singletons
 * @author profile
 *
 */
public final class BusinessCalendarFactory {

	// Storage for BusinessCalendar object
	// Key is the configuration file path
    private static Map<String, BusinessCalendar> store = new HashMap<String, BusinessCalendar>();

    public static BusinessCalendar getInstance(String configFilePath) throws IOException {
        synchronized (store) {
        	BusinessCalendar result = store.get(configFilePath);
            if (result == null) {
                result = new BusinessCalendar(configFilePath);
                store.put(configFilePath, result);
            }
	        
            return result;
        }
    }
}