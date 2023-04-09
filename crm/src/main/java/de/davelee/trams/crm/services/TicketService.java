package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Ticket;
import de.davelee.trams.crm.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to provide service operations for tickets in TraMS CRM.
 * @author Dave Lee
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    /**
     * Save the specified ticket object in the database.
     * @param ticket a <code>Ticket</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the ticket has been saved successfully.
     */
    public boolean save(final Ticket ticket) {
        return ticketRepository.save(ticket) != null;
    }

    /**
     * Find tickets according to their company.
     * @param company a <code>String</code> with the company to retrieve tickets for.
     * @return a <code>List</code> of <code>Ticket</code> representing the tickets matching the criteria. Returns null if no matching tickets.
     */
    public List<Ticket> findByCompany (final String company) {
        return ticketRepository.findByCompany(company);
    }

    /**
     * Find tickets according to their company and type.
     * @param company a <code>String</code> with the company to retrieve tickets  for.
     * @param type a <code>String</code> with the company to retrieve tickets for.
     * @return a <code>List</code> of <code>Ticket</code> representing the tickets matching the criteria. Returns null if no matching tickets.
     */
    public List<Ticket> findByCompanyAndType ( final String company, final String type) {
        return ticketRepository.findByCompanyAndType(company, type);
    }
}
