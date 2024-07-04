package de.davelee.trams.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

/**
 * This class represents a frequency pattern.
 * A frequency pattern contains information about the frequency between stops on particular days.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class FrequencyPattern {

    /**
     * The id of the frequency pattern in the database.
     */
    private String id;

    /**
     * The name of this frequency pattern.
     */
    private String name;

    /**
     * The days that this frequency pattern operates.
     */
    private OperatingDays daysOfOperation;

    /**
     * The start stop of this frequency pattern.
     */
    private String startStop;

    /**
     * The end stop of this frequency pattern.
     */
    private String endStop;

    /**
     * The start time of this frequency pattern.
     */
    private LocalTime startTime;

    /**
     * The end time of this frequency pattern.
     */
    private LocalTime endTime;

    /**
     * The frequency in minutes.
     */
    private int frequencyInMinutes;

    /**
     * The number of tours required to run this frequency pattern.
     */
    private int numTours;

}
