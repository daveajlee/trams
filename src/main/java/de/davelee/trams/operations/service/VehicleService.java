package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.*;
import de.davelee.trams.operations.repository.VehicleRepository;
import de.davelee.trams.operations.response.VehicleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

}
