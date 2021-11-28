package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface class for database operations on message - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface MessageRepository extends MongoRepository<Message, Long> {

    /**
     * Find all messages belonging to a company.
     * @param company a <code>String</code> with the company to retrieve messages for.
     * @return a <code>List</code> of <code>Message</code> objects representing the messages belonging to this company. Returns null if no matching messages.
     */
    List<Message> findByCompany (@Param("company") final String company );

}
