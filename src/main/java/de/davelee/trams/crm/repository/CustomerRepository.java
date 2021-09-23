package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Interface class for database operations on customers - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    /**
     * Find a customer according to their email address and company they are registered for.
     * @Param company a <code>String</code> with the name of the company that the user has registered with.
     * @param emailAddress a <code>String</code> with the email address belonging to the customer who should be retrieved.
     * @return a <code>Customer</code> representing the customer which has this email address and company. Returns null if no matching customer.
     */
    Customer findByCompanyAndEmailAddress (@Param("company") final String company, @Param("emailAddress") final String emailAddress);

}
