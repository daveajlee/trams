package de.davelee.trams.operations.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleHoursResponse {

    /**
     * The number of hours that the vehicle has been in service for the specified date.
     */
    private int numberOfHoursSoFar;

    /**
     * The number of hours that the vehicle may still serve for the specified date.
     */
    private int numberOfHoursAvailable;

    /**
     * Whether or not the maximum number of hours has already been reached.
     */
    private boolean maximumHoursReached;

}
