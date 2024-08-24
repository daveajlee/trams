package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.OperatingDays;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for processing related to stop times in TraMS Server.
 * @author Dave Lee
 */
public class StopTimeUtils {

    /**
     * This helper method converts the operating days into a string list for the API.
     * @param operatingDays a <code>OperatingDays</code> object to convert.
     * @return a <code>List</code> of <code>String</code> objects which have been converted into a String with name of day or date
     * in format dd-MM-yyyy.
     */
    public static List<String> convertOperatingDaysToString (final OperatingDays operatingDays ) {
        //Create empty list
        List<String> operatingDaysStrList = new ArrayList<>();
        //Go through list of days of weeks and convert.
        for ( DayOfWeek operatingDay : operatingDays.getOperatingDays() ) {
            operatingDaysStrList.add(operatingDay.name());
        }
        //Then add the individual dates of operation.
        if ( operatingDays.getSpecialOperatingDays() != null ) {
            for ( LocalDateTime operatingDate : operatingDays.getSpecialOperatingDays() ) {
                operatingDaysStrList.add(DateUtils.convertLocalDateTimeToDate(operatingDate));
            }
        }
        //Return the list.
        return operatingDaysStrList;
    }

    /**
     * This helper method converts a string list from the API to a list of operating days.
     * @param operatingDaysStr a <code>String</code> containing the operating days as a comma-separated string.
     * @return a <code>OperatingDays</code> object which have been converted.
     */
    public static OperatingDays convertOperatingDays ( final String operatingDaysStr ) {
        //Create empty OperatingDays object.
        List<DayOfWeek> operatingDaysList = new ArrayList<>();
        List<LocalDateTime> specialOperatingDaysList = new ArrayList<>();
        //Go through list and convert.
        for ( String operatingDayStr : operatingDaysStr.split(",") ) {
            if ( operatingDayStr.contains("-") ) {
                specialOperatingDaysList.add(DateUtils.convertDateToLocalDateTime(operatingDaysStr));
            } else {
                operatingDaysList.add(DayOfWeek.valueOf(operatingDayStr.toUpperCase()));
            }
        }
        //Return the OperatingDays object.
        return OperatingDays.builder().operatingDays(operatingDaysList).specialOperatingDays(specialOperatingDaysList).build();
    }

}
