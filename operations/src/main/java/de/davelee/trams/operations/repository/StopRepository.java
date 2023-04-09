package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.Stop;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the stop objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface StopRepository extends MongoRepository<Stop, String> {

    /**
     * Return a list of stops served by this company.
     * @param company a <code>String</code> containing the name of the company to retrieve stops for.
     * @return a <code>List</code> of <code>Stop</code> objects containing the matching stops.
     */
    List<Stop> findByCompany (final String company);

    /**
     * Return a list of stops served by this company and matching the supplied stop name (usually 1).
     * @param company a <code>String</code> containing the name of the company to retrieve stops for.
     * @param name a <code>String</code> containing the name of the stop to retrieve
     * @return a <code>List</code> of <code>Stop</code> objects containing the matching stops (usually 1).
     */
    List<Stop> findByCompanyAndName (final String company, final String name);

}
