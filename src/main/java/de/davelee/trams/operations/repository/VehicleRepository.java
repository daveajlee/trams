package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the vehicle objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    List<Vehicle> findByCompanyAndFleetNumberStartsWith (final String company, final String fleetNumber);

    List<Vehicle> findByCompany (final String company);

    List<Vehicle> findByCompanyAndAllocatedTour ( final String company, final String allocatedTour );

}
