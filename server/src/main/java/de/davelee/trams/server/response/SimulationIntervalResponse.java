package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current simulation interval in minutes.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimulationIntervalResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The simulation interval in minutes.
     */
    private int simulationInterval;
}
