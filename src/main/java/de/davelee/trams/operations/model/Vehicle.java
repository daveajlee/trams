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

    /**
     * The number of hours that a vehicle was in service on a particular day.
     */
    private Map<LocalDate, Integer> timesheet;

    /**
     * Add a number of hours for a particular day to the timesheet.
     * @param hours a <code>int</code> with the number of hours to add.
     * @param date a <code>LocalDate</code> object containing the day to add the hours to.
     */
    public void addHoursForDate ( final int hours, final LocalDate date ) {
        //If the date already exists then add the hours to the hours already there.
        if ( timesheet.get(date) != null ) {
            timesheet.put(date, timesheet.get(date).intValue() + hours);
        } else {
            //If no hours are present then just add it as first entry.
            timesheet.put(date, hours);
        }
    }

    /**
     * Retrieve the number of hours that the vehicle has been in service on a particular day.
     * @param date a <code>LocalDate</code> object containing the day to retrieve hours for.
     * @return a <code>int</code> with the number of hours.
     */
    public int getHoursForDate ( final LocalDate date ) {
        //If the date is null then return 0.
        if ( timesheet.get(date) == null ) {
            return 0;
        }
        //Otherwise return the number of hours.
        return timesheet.get(date);
    }

}
