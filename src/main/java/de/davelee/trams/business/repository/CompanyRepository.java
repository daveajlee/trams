package de.davelee.trams.business.repository;

import de.davelee.trams.business.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface class for database operations on company - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface CompanyRepository extends MongoRepository<Company, Long> {
}
