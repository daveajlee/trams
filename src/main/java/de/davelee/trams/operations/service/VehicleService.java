package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
