package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response with
 * a frequency pattern for a particular timetable.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FrequencyPatternResponse {

    /**
     * The name of this frequency pattern.
     */
    private String name;

    /**
     * The days that this frequency pattern operates.
     */
    private String[] daysOfOperation;

    /**
     * The start stop of this frequency pattern.
     */
    private String startStop;

    /**
     * The end stop of this frequency pattern.
     */
    private String endStop;

    /**
     * The start time of this frequency pattern in the format HH:mm
     */
    private String startTime;

    /**
     * The end time of this frequency pattern in the format HH:mm
     */
    private String endTime;

    /**
     * The frequency in minutes.
     */
    private int frequencyInMinutes;

    /**
     * The number of tours required to run this frequency pattern.
     */
    private int numTours;

}