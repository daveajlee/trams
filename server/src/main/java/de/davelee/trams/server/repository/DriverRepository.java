package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the driver objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface DriverRepository extends MongoRepository<Driver, String> {

    /**
     * Return a list of drivers owned by this company and have a name starting with the supplied name.
     * @param company a <code>String</code> containing the name of the company to retrieve drivers for.
     * @param name a <code>String</code> containing the name that the driver should contain as a minimum.
     * @return a <code>List</code> of <code>Driver</code> objects containing the matching drivers (usually one).
     */
    List<Driver> findByCompanyAndNameStartsWith (final String company, final String name);

    /**
     * Return a list of drivers owned by this company.
     * @param company a <code>String</code> containing the name of the company to retrieve drivers for.
     * @return a <code>List</code> of <code>Driver</code> objects containing the matching drivers.
     */
    List<Driver> findByCompany (final String company);

}
