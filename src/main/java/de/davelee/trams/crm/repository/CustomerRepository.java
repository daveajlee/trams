package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface class for database operations on customers - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface CustomerRepository extends MongoRepository<Customer, Long> {

    /**
     * Find a customer according to their email address and company they are registered for.
     * @Param company a <code>String</code> with the name of the company that the customer has registered with.
     * @param emailAddress a <code>String</code> with the email address belonging to the customer who should be retrieved.
     * @return a <code>Customer</code> representing the customer which has this email address and company. Returns null if no matching customer.
     */
    Customer findByCompanyAndEmailAddress (@Param("company") final String company, @Param("emailAddress") final String emailAddress);

    /**
     * Find all customers belonging to a company.
     * @param company a <code>String</code> with the company to retrieve customers for.
     * @return a <code>List</code> of <code>Customer</code> objects representing the customers belonging to this company. Returns null if no matching customers.
     */
    List<Customer> findByCompany (@Param("company") final String company );

}
