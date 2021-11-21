package de.davelee.trams.operations.request;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the TraMS Operations REST API. It represents a request to load a single vehicle for a particular
 * company.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class LoadVehicleRequest {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The date that the vehicle was delivered to its current company in the format dd-MM-yyyy.
     */
    private String deliveryDate;

    /**
     * The date that the vehicle last went through an inspection in the format dd-MM-yyyy.
     */
    private String inspectionDate;

    /**
     * The type of this vehicle which is mapped from subclasses as appropriate.
     */
    private String vehicleType;

    /**
     * The current status of the vehicle which is mapped from the Enum.
     */
    private String vehicleStatus;

    /**
     * The number of seats that this vehicle has.
     */
    private int seatingCapacity;

    /**
     * The number of persons who are allowed to stand in this vehicle.
     */
    private int standingCapacity;

    /**
     * The name of the model of this vehicle.
     */
    private String modelName;

    /**
     * The livery that this vehicle has.
     */
    private String livery;

    /**
     * The allocated tour for this vehicle.
     */
    private String allocatedTour;

    /**
     * The additional parameters relevant to this vehicle type e.g. registration number for buses are stored as key/value pairs.
     */
    private Map<String, String> additionalTypeInformationMap;

    /**
     * The list of entries in the log history of this vehicle.
     */
    private List<VehicleHistoryRequest> userHistory;

    /**
     * The number of hours that a vehicle was in service on a particular day.
     */
    private Map<String, Integer> timesheet;

}
