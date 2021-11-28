package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface class for database operations on message - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface MessageRepository extends MongoRepository<Message, Long> {
}
