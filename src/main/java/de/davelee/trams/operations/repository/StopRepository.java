package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the stop objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface StopRepository extends MongoRepository<Stop, String> {

    List<Stop> findByCompany (final String company);

    List<Stop> findByCompanyAndName (final String company, final String name);

}
