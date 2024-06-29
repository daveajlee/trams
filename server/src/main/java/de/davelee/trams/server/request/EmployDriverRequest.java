package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to employ a driver for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class EmployDriverRequest {

    /**
     * The name of the driver.
     */
    private String name;

    /**
     * The contracted hours of the driver.
     */
    private int contractedHours;

    /**
     * The start date of the driver.
     */
    private String startDate;

    /**
     * The company that the driver wants to work for.
     */
    private String company;

}
