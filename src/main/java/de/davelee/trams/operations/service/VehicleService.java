package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.repository.VehicleRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides a service for managing vehicles in Trams Operations.
 * @author Dave Lee
 */
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Add the supplied vehicle to the database.
     * @param vehicle a <code>Vehicle</code> object containing the information about the vehicle to be added.
     * @return a <code>boolean</code> which is true iff the vehicle was added successfully.
     */
    public boolean addVehicle ( final Vehicle vehicle) {
        //Validate vehicle according to current rules and return false if not successful.
        if ( !validateVehicle(vehicle) ) {
            return false;
        }
        //If the vehicle is valid, then attempt to add vehicle to db.
        return vehicleRepository.insert(vehicle) != null;
    }

    /**
     * Retrieve all vehicles starting with the supplied company name and fleet number from the database for all types.
     * @param company a <code>String</code> with the name of the company to search for.
     * @param fleetNumber a <code>String</code> with the fleet number to search for.
     * @return a <code>List</code> of <code>Vehicle</code> objects matching the supplied criteria.
     */
    public List<Vehicle> retrieveVehiclesByCompanyAndFleetNumber ( final String company, final String fleetNumber) {
        //List to store all vehicles
        List<Vehicle> vehicleList = vehicleRepository.findByCompanyAndFleetNumberStartsWith(company, fleetNumber);
        //Return the vehicle list
        return vehicleList;
    }

    /**
     * Retrieve all vehicles for a particular company from the database for all types.
     * @param company a <code>String</code> with the company to search for.
     * @return a <code>List</code> of <code>VehicleResponse</code> objects in a format suitable to be returned via API.
     */
    public List<Vehicle> retrieveVehiclesByCompany ( final String company) {
        //Return the vehicles found.
        return vehicleRepository.findByCompany(company);
    }

    /**
     * Add the number of hours for a particular date to the specified vehicle object.
     * @param vehicle a <code>Vehicle</code> object to set the hours for.
     * @param hours a <code>int</code> with the number of hours to add.
     * @param date a <code>LocalDate</code> object containing the day to add the hours to.
     * @return a <code>boolean</code> which is true iff the vehicle has been updated successfully.
     */
    public boolean addHoursForDate ( final Vehicle vehicle, final int hours, final LocalDate date ) {
        if ( vehicle.getTimesheet() == null ) {
            vehicle.setTimesheet(new HashMap<>());
        }
        vehicle.addHoursForDate(hours, date);
        return vehicleRepository.save(vehicle) != null;
    }

    /**
     * Add a new history entry to the list.
     * @param vehicle a <code>Vehicle</code> object to set the hours for.
     * @param date a <code>LocalDate</code> containing the date that the entry/event took place.
     * @param vehicleHistoryReason a <code>VehicleHistoryReason</code> containing the reason that the entry/event took place.
     * @param comment a <code>String</code> containing the comment about the entry/event.
     * @return a <code>boolean</code> which is true iff the vehicle has been updated successfully.
     */
    public boolean addVehicleHistoryEntry (final Vehicle vehicle, final LocalDate date, final VehicleHistoryReason vehicleHistoryReason, final String comment) {
        if ( vehicle.getVehicleHistoryEntryList() == null ) {
            vehicle.setVehicleHistoryEntryList(new ArrayList<>());
        }
        vehicle.addVehicleHistoryEntry(date, vehicleHistoryReason, comment);
        return vehicleRepository.save(vehicle) != null;
    }

    /**
     * Sell the supplied vehicle by adding a log entry to the vehicle and returning the price for which it was sold.
     * @param vehicle a <code>Vehicle</code> object which should be sold.
     * @return a <code>BigDecimal</code> object with the price that the vehicle was sold for which can be 0 if the vehicle could not be sold.
     */
    public BigDecimal sellVehicle (final Vehicle vehicle ) {
        vehicle.addVehicleHistoryEntry(LocalDate.now(), VehicleHistoryReason.SOLD, "Sold for " + vehicle.getVehicleType().getPurchasePrice());
        vehicle.setAllocatedTour(""); //Remove allocated tour.
        if ( vehicleRepository.save(vehicle) != null ) {
            return vehicle.getVehicleType().getPurchasePrice();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Inspect the supplied vehicle by adding a log entry to the vehicle, updating the inspection date and returning the price for the inspection.
     * @param vehicle a <code>Vehicle</code> object which should be inspected.
     * @return a <code>BigDecimal</code> object with the price of performing a inspection on the vehicle which can be 0 if the vehicle could not be inspected.
     */
    public BigDecimal inspectVehicle (final Vehicle vehicle ) {
        vehicle.addVehicleHistoryEntry(LocalDate.now(), VehicleHistoryReason.INSPECTED, "Inspected for " + vehicle.getVehicleType().getInspectionPrice());
        vehicle.setInspectionDate(LocalDate.now());
        if ( vehicleRepository.save(vehicle) != null ) {
            return vehicle.getVehicleType().getInspectionPrice();
        }
        return BigDecimal.ZERO;
    }

    /**
     * Allocate the supplied vehicle to the supplied tour and update the database accordingly.
     * @param vehicle a <code>Vehicle</code> object which should be allocated.
     * @param allocatedTour a <code>String</code> with the tour id that the vehicle should be allocated.
     * @return a <code>boolean</code> which is true iff the vehicle could be allocated successfully.
     */
    public boolean allocateTourToVehicle ( final Vehicle vehicle, final String allocatedTour ) {
        vehicle.setAllocatedTour(allocatedTour);
        return vehicleRepository.save(vehicle) != null;
    }

    /**
     * Private helper method to validate a vehicle based on the defined rules.
     * @param vehicle a <code>Vehicle</code> object to validate
     * @return a <code>boolean</code> which is true iff the vehicle fulfils all validation rules.
     */
    private boolean validateVehicle ( final Vehicle vehicle ) {
        //Vehicles always have a valid operator, delivery date and model.
        if (StringUtils.isBlank(vehicle.getCompany()) || StringUtils.isBlank(vehicle.getModelName()) || vehicle.getDeliveryDate() == null ) {
            return false;
        }
        //The seating and standing capacities of a vehicle must be greater than or equal to 0.
        if ( vehicle.getSeatingCapacity() < 0 || vehicle.getStandingCapacity() < 0 ) {
            return false;
        }
        //Buses always have a registration number.
        if ( vehicle.getVehicleType() == VehicleType.BUS && vehicle.getTypeSpecificInfos().get("Registration Number") == null ) {
            return false;
        }
        //Trains always have an operating mode.
        if ( vehicle.getVehicleType() == VehicleType.TRAIN && vehicle.getTypeSpecificInfos().get("Operating Mode") == null ) {
            return false;
        }
        //If all cases fulfilled then return true.
        return true;
    }

}
