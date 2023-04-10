package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface class for database operations on ticket - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface TicketRepository extends MongoRepository<Ticket, Long> {

    /**
     * Find all tickets belonging to a company.
     * @param company a <code>String</code> with the company to retrieve tickets for.
     * @return a <code>List</code> of <code>Feedback</code> objects representing the tickets belonging to this company. Returns null if no matching tickets.
     */
    List<Ticket> findByCompany (@Param("company") final String company );

    /**
     * Find all tickets matching a type and a company - usually this should only be 1.
     * @param company a <code>String</code> with the company to retrieve tickets for.
     * @param type a <code>String</code> with the type of ticket to retrieve.
     * @return a <code>List</code> of <code>Feedback</code> objects representing the tickets belonging to this company. Returns null if no matching tickets.
     */
    List<Ticket> findByCompanyAndType (@Param("company") final String company, @Param("type") final String type);

}
