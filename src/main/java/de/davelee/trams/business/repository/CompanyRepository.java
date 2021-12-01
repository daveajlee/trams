package de.davelee.trams.business.repository;

import de.davelee.trams.business.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface class for database operations on company - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface CompanyRepository extends MongoRepository<Company, Long> {

    List<Company> findByName (final String name);

}
