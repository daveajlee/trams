package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response to a request to employ a driver
 * and contains the costs of employing the driver.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class EmployDriverResponse {

    /**
     * Could the driver be employed successfully?
     */
    private boolean employed;

    /**
     * The cost of employing the driver which may be 0 if the driver could not be employed successfully.
     */
    private double employmentCost;

}

