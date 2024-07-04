package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.FrequencyPattern;
import de.davelee.trams.server.model.OperatingDays;
import de.davelee.trams.server.request.FrequencyPatternRequest;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides utility methods for processing related to frequency patterns.
 * @author Dave Lee
 */
public class FrequencyPatternUtils {

    /**
     * This method converts a FrequencyPatternRequest object to a FrequencyPattern object. If the conversion is not
     * successful then return null.
     * @param frequencyPatternRequests an array of <code>FrequencyPatternRequest</code> objects
     * @return a <code>FrequencyPattern</code> array with the converted objects or null if no conversion is possible.
     */
    public static FrequencyPattern[] convertFrequencyPatternRequestsToFrequencyPatterns (final FrequencyPatternRequest[] frequencyPatternRequests) {
        FrequencyPattern[] frequencyPatterns = new FrequencyPattern[frequencyPatternRequests.length];
        for ( int i = 0; i < frequencyPatternRequests.length; i++ ) {
            frequencyPatterns[i] = FrequencyPattern.builder()
                    .frequencyInMinutes(frequencyPatternRequests[i].getFrequencyInMinutes())
                    .startStop(frequencyPatternRequests[i].getStartStop())
                    .startTime(DateUtils.convertTimeToLocalTime(frequencyPatternRequests[i].getStartTime()))
                    .endStop(frequencyPatternRequests[i].getEndStop())
                    .endTime(DateUtils.convertTimeToLocalTime(frequencyPatternRequests[i].getEndTime()))
                    .daysOfOperation(FrequencyPatternUtils.convertDaysOfOperation(frequencyPatternRequests[i].getDaysOfOperation()))
                    .name(frequencyPatternRequests[i].getName())
                    .numTours(frequencyPatternRequests[i].getNumTours())
                    .build();
        }
        return frequencyPatterns;
    }

    /**
     * Convert the days of operation array as a String into an array of a OperatingDays object.
     * @param daysOfOperation a <code>String</code> array to be converted.
     * @return a <code>OperatingDays</code> object with the converted array.
     */
    public static OperatingDays convertDaysOfOperation(String[] daysOfOperation) {
        // Process the list of weekdays that the service should operate.
        List<DayOfWeek> dayOfWeekList = new ArrayList<>();
        for (int i = 0; i < daysOfOperation.length; i++) {
            dayOfWeekList.add(DayOfWeek.valueOf(daysOfOperation[i]));
        }
        // Return the operating days.
        return OperatingDays
                .builder()
                .operatingDays(dayOfWeekList)
                .build();

    }

}
