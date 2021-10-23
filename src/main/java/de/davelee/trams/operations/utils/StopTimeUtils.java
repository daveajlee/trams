package de.davelee.trams.operations.utils;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for processing related to stop times in TraMS Operations.
 * @author Dave Lee
 */
public class StopTimeUtils {

    /**
     * This helper method converts a list of operating days into a string list for the API.
     * @param operatingDaysList a <code>List</code> of <code>DayOfWeek</code> objects to convert.
     * @return a <code>List</code> of <code>String</code> objects which have been converted into a String with name of day.
     */
    public static List<String> convertOperatingDays (final List<DayOfWeek> operatingDaysList ) {
        //Create empty list
        List<String> operatingDaysStrList = new ArrayList<>();
        //Go through list and convert.
        for ( DayOfWeek operatingDay : operatingDaysList ) {
            operatingDaysStrList.add(operatingDay.name());
        }
        //Return the list.
        return operatingDaysStrList;
    }

}
