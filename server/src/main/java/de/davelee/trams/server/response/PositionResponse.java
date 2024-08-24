package de.davelee.trams.server.response;

import de.davelee.trams.server.model.ServiceTrip;
import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * the current position returned from the server.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PositionResponse {

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
    private ServiceTripResponse service;

}
