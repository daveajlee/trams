package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current satisfaction rate.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SatisfactionRateResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The satisfaction rate of the company.
     */
    private double satisfactionRate;


}
