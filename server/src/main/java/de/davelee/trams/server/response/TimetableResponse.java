package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response with
 * a timetable for a particular route and company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TimetableResponse {

    /**
     * The name of this timetable.
     */
    private String name;

    /**
     * The company that this timetable belongs to.
     */
    private String company;

    /**
     * The route number that this timetable belongs to.
     */
    private String routeNumber;

    /**
     * The date that this timetable is valid from in format dd-MM-yyyy HH:mm
     */
    private String validFromDate;

    /**
     * The date that this timetable is valid to in format dd-MM-yyyy HH:mm
     */
    private String validToDate;

    /**
     * The frequency patterns belonging to this timetable.
     */
    private FrequencyPatternResponse[] frequencyPatterns;

}