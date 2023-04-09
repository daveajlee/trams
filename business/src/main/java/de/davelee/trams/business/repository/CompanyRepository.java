package de.davelee.trams.business.repository;

import de.davelee.trams.business.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Interface class for database operations on company - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface CompanyRepository extends MongoRepository<Company, Long> {

    /**
     * Return a list of companies matching the supplied name - usually this will only be one but it can theoretically
     * be more if there are duplicate companies with the same name.
     * @param name a <code>String</code> containing the name of the company to retrieve.
     * @return a <code>List</code> of <code>Company</code> objects containing the matching name.
     */
    List<Company> findByName (final String name);

    /**
     * Return a list of companies matching the supplied name and player name - usually this will only be one but it can
     * theoretically be more if there are duplicate companies with the same name and player name.
     * @param name a <code>String</code> containing the name of the company to retrieve.
     * @param playerName a <code>String</code> containing the player name to retrieve.
     * @return a <code>List</code> of <code>Company</code> objects containing the matching name and player name.
     */
    List<Company> findByNameAndPlayerName (final String name, final String playerName);

}
