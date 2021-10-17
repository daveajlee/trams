package de.davelee.trams.operations.model;

import lombok.*;
import java.time.LocalDate;
import java.util.Map;

/**
 * This class represents a vehicle. A vehicle can contain a fleet number and company,
 * a delivery date, an inspection date, a seating capacity, a standing capacity, a model name,
 * a livery and a status.
 * @author Dave Lee
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class Vehicle {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The date that the vehicle was delivered to its current company.
     */
    private LocalDate deliveryDate;

    /**
     * The date that the vehicle last went through an inspection.
     */
    private LocalDate inspectionDate;

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
     * The current status of the vehicle.
     */
    private VehicleStatus vehicleStatus;

    /**
     * The allocated tour for this vehicle.
     */
    private String allocatedTour;

    /**
     * The type of this vehicle.
     */
    private VehicleType vehicleType;

    /**
     * Map of type specific infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> typeSpecificInfos;

}
