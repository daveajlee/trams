package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Ticket;
import de.davelee.trams.crm.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
