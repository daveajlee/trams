package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the vehicle objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    /**
     * Return a list of vehicles owned by this company and have a fleet number starting with the supplied fleet number.
     * @param company a <code>String</code> containing the name of the company to retrieve vehicles for.
     * @param fleetNumber a <code>String</code> containing the fleet number that the company should contain as a minimum.
     * @return a <code>List</code> of <code>Vehicle</code> objects containing the matching vehicles (usually one).
     */
    List<Vehicle> findByCompanyAndFleetNumberStartsWith (final String company, final String fleetNumber);

    /**
     * Return a list of vehicles owned by this company.
     * @param company a <code>String</code> containing the name of the company to retrieve vehicles for.
     * @return a <code>List</code> of <code>Vehicle</code> objects containing the matching vehicles.
     */
    List<Vehicle> findByCompany (final String company);

    /**
     * Return a list of vehicles owned by this company and allocated to the supplied tour (usually one).
     * @param company a <code>String</code> containing the name of the company to retrieve vehicles for.
     * @param allocatedTour a <code>String</code> containing the tour that the vehicle should be allocated to.
     * @return a <code>List</code> of <code>Vehicle</code> objects containing the matching vehicles (usually one).
     */
    List<Vehicle> findByCompanyAndAllocatedTour ( final String company, final String allocatedTour );

}
