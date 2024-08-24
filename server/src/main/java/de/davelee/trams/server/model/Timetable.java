package de.davelee.trams.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * This class represents a timetable.
 * A timetable contains a set of frequency patterns.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class Timetable {

    /**
     * The id of the timetable in the database.
     */
    private String id;

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
     * The date that this timetable is valid from.
     */
    private LocalDateTime validFromDate;

    /**
     * The date that this timetable is valid to.
     */
    private LocalDateTime validToDate;

    /**
     * The frequency patterns belonging to this timetable.
     */
    private FrequencyPattern[] frequencyPatterns;

}
