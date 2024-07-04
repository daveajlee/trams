package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Driver;
import de.davelee.trams.server.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for managing drivers in Trams Server.
 * @author Dave Lee
 */
@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    /**
     * Add the supplied driver to the database.
     *
     * @param driver a <code>Driver</code> object containing the information about the driver to be added.
     * @return a <code>boolean</code> which is true iff the driver was added successfully.
     */
    public boolean addDriver(final Driver driver) {
        //If the driver is valid, then attempt to add driver to db.
        return driverRepository.insert(driver) != null;
    }

    /**
     * Retrieve all drivers starting with the supplied company name and driver name from the database.
     * @param company a <code>String</code> with the name of the company to search for.
     * @param name a <code>String</code> with the driver name to search for.
     * @return a <code>List</code> of <code>Driver</code> objects matching the supplied criteria.
     */
    public List<Driver> retrieveDriversByCompanyAndName (final String company, final String name) {
        //Return all matching drivers
        return driverRepository.findByCompanyAndNameStartsWith(company, name);
    }

    /**
     * Retrieve all drivers for a particular company from the database for all types.
     * @param company a <code>String</code> with the company to search for.
     * @return a <code>List</code> of <code>Driver</code> objects.
     */
    public List<Driver> retrieveDriversByCompany ( final String company) {
        //Return the drivers found.
        return driverRepository.findByCompany(company);
    }

    /**
     * Delete all drivers currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to delete drivers for.
     */
    public void deleteDrivers(final String company) {
        List<Driver> drivers = retrieveDriversByCompany(company);
        drivers.forEach(driverRepository::delete);
    }
}

