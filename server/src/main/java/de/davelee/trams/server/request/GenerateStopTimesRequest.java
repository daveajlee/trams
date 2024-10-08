package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to generate stop times automatically.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class GenerateStopTimesRequest {

    /**
     * The company that should run these stop times.
     */
    private String company;

    /**
     * The stops that should be served in an array in the order that they should be served.
     */
    private String[] stopNames;

    /**
     * The route number serving these stops.
     */
    private String routeNumber;

    /**
     * The start time from when stop times should be generated in the format HH:mm.
     */
    private String startTime;

    /**
     * The end time until when stop times should be generated in the format HH:mm.
     */
    private String endTime;

    /**
     * The start stop to generate stop times from.
     */
    private String startStop;

    /**
     * The end stop to generate stop times until.
     */
    private String endStop;

    /**
     * The frequency in which stops times should be generated in minutes. Minimum value is 1.
     */
    private int frequency;

    /**
     * The number of tours that should be generated.
     */
    private int numTours;

    /**
     * The valid from date with the date from which stop times are valid. The date is inclusive.
     */
    private String validFromDate;

    /**
     * The valid to date until when stop times are valid. The date is inclusive.
     */
    private String validToDate;

    /**
     * The days when these stop times run.
     */
    private String operatingDays;

    /**
     * The distances between stops in the format stopName:distance,distance,distance per stop as one entry.
     */
    private String[] stopDistances;

}
