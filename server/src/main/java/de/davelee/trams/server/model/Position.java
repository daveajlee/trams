package de.davelee.trams.server.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents a position where a vehicle / tour is currently at.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@ToString
public class Position {

    /**
     * The company running the tour.
     */
    private String company;

    /**
     * The current stop that the tour is at.
     */
    private String stop;

    /**
     * The destination that the tour is heading to.
     */
    private String destination;

    /**
     * The current delay of the vehicle.
     */
    private int delay;

    /**
     * The service that is running this journey.
     */
    private ServiceTrip service;

}