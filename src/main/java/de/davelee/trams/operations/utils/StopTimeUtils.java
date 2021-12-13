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

    /**
     * This helper method converts a string list from the API to a list of operating days.
     * @param operatingDaysStr a <code>String</code> containing the operating days as a comma-separated string.
     * @return a <code>List</code> of <code>DayOfWeek</code> objects which have been converted.
     */
    public static List<DayOfWeek> convertOperatingDaysToDayOfWeek ( final String operatingDaysStr ) {
        //Create empty list.
        List<DayOfWeek> operatingDaysList = new ArrayList<>();
        //Go through list and convert.
        for ( String operatingDayStr : operatingDaysStr.split(",") ) {
            operatingDaysList.add(DayOfWeek.valueOf(operatingDayStr.toUpperCase()));
        }
        //Return the list.
        return operatingDaysList;
    }

}
