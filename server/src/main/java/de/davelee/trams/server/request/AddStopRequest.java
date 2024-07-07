package de.davelee.trams.server.request;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a stop.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddStopRequest {

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
     * A list of the other stop names since some programming languages do not support map/key values.
     */
    private List<String> otherStopNames;

    /**
     * The distances between this stop and the other stops. The distance matches the position in the stop names list.
     */
    private List<Integer> otherStopDistances;

    /**
     * The latitude location of the stop which should be in a valid format for a latitude e.g. 50.0200004
     */
    private double latitude;

    /**
     * The longitude location of the stop which should be in a valid format for a longitude e.g. 50.0200004
     */
    private double longitude;

}
