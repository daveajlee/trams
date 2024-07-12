package de.davelee.trams.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class provides utility methods for processing related to dates.
 * @author Dave Lee
 */
public class DateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * This method converts a string date in the format dd-mm-yyyy HH:mm to a LocalDateTime object. If the conversion is not
     * successful then return null.
     * @param date a <code>String</code> in the form dd-mm-yyyy HH:mm
     * @return a <code>LocalDateTime</code> with the converted date or null if no conversion is possible.
     */
    public static LocalDateTime convertDateToLocalDateTime (final String date ) {
        try {
            return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
        } catch ( DateTimeParseException dateTimeParseException ) {
            LOG.error("Could not convert date: " + date);
            return null;
        }
    }

    /**
     * This method converts a string time in the format HH:mm to a LocalTime object. If the conversion is not
     * successful then return null.
     * @param time a <code>String</code> in the form HH:mm
     * @return a <code>LocalTime</code> with the converted time or null if no conversion is possible.
     */
    public static LocalTime convertTimeToLocalTime (final String time ) {
        try {
            return LocalTime.parse(time, TIME_FORMATTER);
        } catch ( DateTimeParseException dateTimeParseException ) {
            LOG.error("Could not convert time: " + time);
            return null;
        }
    }

    /**
     * This method converts a LocalDateTime object to a string date in the format dd-mm-yyyy. If the conversion is not
     * successful then return null.
     * @param date a <code>LocalDateTime</code> with the date to convert
     * @return a <code>String</code> with the converted String.
     */
    public static String convertLocalDateTimeToDate ( final LocalDateTime date ) {
        if ( date == null ) {
            return null;
        }
        return date.format(DATE_TIME_FORMATTER);
    }

    /**
     * This method converts a LocalTime object to a string time in the format HH:mm. If the conversion is not
     * successful then return null.
     * @param time a <code>LocalTime</code> with the time to convert
     * @return a <code>String</code> with the converted String.
     */
    public static String convertLocalTimeToTime (final LocalTime time ) {
        if ( time == null ) {
            return null;
        }
        return time.format(TIME_FORMATTER);
    }

}
