package de.davelee.trams.operations.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * This class represents a stop. A stop can contain an id, a name, a latitude and a longitude.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class Stop {

    /**
     * The id of the stop.
     */
    private String id;

    /**
     * The name of the stop.
     */
    private String name;

    /**
     * The name of the company serving this stop.
     */
    private String company;

    /**
     * The waiting time at the stop for a vehicle.
     */
    private int waitingTime;

    /**
     * The distances between this stop and other stops as key/value pair with stop name and distance in minutes.
     */
    private Map<String, Integer> distances;

    /**
     * The latitude location of the stop which should be in a valid format for a latitude e.g. 50.0200004
     */
    private double latitude;

    /**
     * The longitude location of the stop which should be in a valid format for a longitude e.g. 50.0200004
     */
    private double longitude;

}
