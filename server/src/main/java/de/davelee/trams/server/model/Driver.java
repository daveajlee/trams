package de.davelee.trams.server.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * This class represents a driver. A driver can have a name, contracted hours and a start date.
 * More advanced features for driver are only available via PersonalMan integration.
 * @author Dave Lee
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Driver {

    /**
     * The id of the driver in the database.
     */
    private String id;

    /**
     * The name of the driver.
     */
    private String name;

    /**
     * The company that this driver works for.
     */
    private String company;

    /**
     * The contracted hours of the driver.
     */
    private int contractedHours;

    /**
     * The date that the driver started.
     */
    private LocalDateTime startDate;

}
