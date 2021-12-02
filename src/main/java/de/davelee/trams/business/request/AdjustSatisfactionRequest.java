package de.davelee.trams.business.request;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a request to adjust the satisfaction rate
 * by either increasing or decreasing the satisfaction rate for a particular company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdjustSatisfactionRequest {

    /**
     * The name of the company to adjust the satisfaction rate.
     */
    private String company;

    /**
     * The value to either subtract (minus) or add (plus) to the satisfaction rate.
     */
    private double satisfactionRate;

}
