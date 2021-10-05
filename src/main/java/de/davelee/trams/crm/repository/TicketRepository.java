package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface class for database operations on ticket - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface TicketRepository extends MongoRepository<Ticket, Long> {



}
