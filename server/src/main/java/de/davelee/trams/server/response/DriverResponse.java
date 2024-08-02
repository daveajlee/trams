package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response with
 * a driver for a particular company.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DriverResponse {

    /**
     * The name of this driver.
     */
    private String name;

    /**
     * The company that this driver works for.
     */
    private String company;

    /**
     * The contracted hours that the driver works.
     */
    private int contractedHours;

    /**
     * The date that this driver starts in format dd-MM-yyyy
     */
    private String startDate;

}