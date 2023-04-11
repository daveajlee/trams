package de.davelee.trams.server.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single stop containing name, company and location information.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class StopResponse {

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
