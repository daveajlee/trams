package de.davelee.trams.server.constant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the available abbreviations which can be used to represent days that the service will operate.
 * These abbreviations can then be used in CSV files which are imported into TraMS Server.
 * @author Dave Lee
 */
public enum OperatingDaysAbbreviations {

    /**
     * Weekdays
     */
    WD {
        @Override
        public List<DayOfWeek> getOperatingDaysOfWeek() {
            return List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        }

        @Override
        public List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate) {
            return List.of();
        }

    },

    /**
     * Saturdays
     */
    SA {
        @Override
        public List<DayOfWeek> getOperatingDaysOfWeek() {
            return List.of(DayOfWeek.SATURDAY);
        }

        @Override
        public List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate) {
            return List.of();
        }

    },

    /**
     * Sundays
     */
    SU {
        @Override
        public List<DayOfWeek> getOperatingDaysOfWeek() {
            return List.of(DayOfWeek.SUNDAY);
        }

        @Override
        public List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate) {
            return List.of();
        }
    },

    /**
     * Christmas Day
     */
    XD {
        @Override
        public List<DayOfWeek> getOperatingDaysOfWeek() {
            return List.of();
        }

        @Override
        public List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate) {
            return OperatingDaysAbbreviations.getSpecialOperatingDaysForDayAndMonth(validFromDate, validToDate, 12, 25);
        }
    },

    /**
     * New Year's Day
     */
    NYD {
        @Override
        public List<DayOfWeek> getOperatingDaysOfWeek() {
            return List.of();
        }

        @Override
        public List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate) {
            return OperatingDaysAbbreviations.getSpecialOperatingDaysForDayAndMonth(validFromDate, validToDate, 1, 1);
        }
    };

    /**
     * Return those days of week (e.g. Monday, Tuesday) where this stop time will operate.
     * @return a <code>List</code> of <code>DayOfWeek</code> objects.
     */
    public abstract List<DayOfWeek> getOperatingDaysOfWeek();

    /**
     * Return all individual dates where this service will operate additionally.
     * @param validFromDate a <code>LocalDateTime</code> object containing the valid from date of this stop time.
     * @param validToDate a <code>LocalDateTime</code> object containing the valid to date of this stop time.
     * @return a <code>List</code> of <code>LocalDateTime</code> objects.
     */
    public abstract List<LocalDateTime> getSpecialOperatingDays(final LocalDateTime validFromDate, final LocalDateTime validToDate);

    /**
     * Return a list of dates where a specific date such as 25th December occurs each year between the valid from
     * and valid to dates.
     * @param validFromDate a <code>LocalDateTime</code> object containing the valid from date of this stop time.
     * @param validToDate a <code>LocalDateTime</code> object containing the valid to date of this stop time.
     * @param month a <code>int</code> containing the month in number format e.g. 12 for December
     * @param dayOfMonth a <code>int</code> containing the day of the month e.g. 25 for 25th
     * @return a <code>List</code> of <code>LocalDateTime</code> objects.
     */
    public static List<LocalDateTime> getSpecialOperatingDaysForDayAndMonth ( final LocalDateTime validFromDate, final LocalDateTime validToDate,
                                                                    final int month, final int dayOfMonth ) {
        List<LocalDateTime> specialOperatingDays = new ArrayList<>();
        LocalDateTime processDate = validFromDate;
        while ( processDate.isBefore(validToDate) ) {
            specialOperatingDays.add(LocalDateTime.of(processDate.getYear(), month, dayOfMonth,0,0));
            processDate = processDate.plusYears(1);
        }
        return specialOperatingDays;
    }

}
