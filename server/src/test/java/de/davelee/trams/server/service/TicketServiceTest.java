package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Ticket;
import de.davelee.trams.server.repository.TicketRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the TicketService class - the TicketRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
public class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private TicketRepository ticketRepository;

    /**
     * Test case: save a new ticket.
     * Expected Result: true.
     */
    @Test
    public void testSaveTicket() {
        //Test data
        Ticket ticket = generateValidTicket();
        //Mock important method in repository.
        Mockito.when(ticketRepository.save(ticket)).thenReturn(ticket);
        //do actual test.
        assertTrue(ticketService.save(ticket));
    }

    /**
     * Test case: retrieve a ticket.
     * Expected Result: list of tickets.
     */
    @Test
    public void testGetTicket() {
        //Mock important methods.
        Mockito.when(ticketRepository.findByCompany("Mustermann GmbH")).thenReturn(
                List.of(generateValidTicket())
        );
        //do actual test.
        assertEquals(1, ticketService.findByCompany("Mustermann GmbH").size());
    }

    /**
     * Test case: retrieve a ticket based on type.
     * Expected Result: list of tickets.
     */
    @Test
    public void testGetTicketByType() {
        //Mock important methods.
        Mockito.when(ticketRepository.findByCompanyAndType("Mustermann GmbH", "Single Ticket")).thenReturn(
                List.of(generateValidTicket())
        );
        //do actual test.
        assertEquals(1, ticketService.findByCompanyAndType("Mustermann GmbH", "Single Ticket").size());
    }

    /**
     * Private helper method to generate a valid ticket.
     * @return a <code>Ticket</code> object containing valid test data.
     */
    private Ticket generateValidTicket( ) {
        return Ticket.builder()
                .id(ObjectId.get())
                .shortId("single")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .type("Single Ticket")
                .sortOrder(1)
                .priceList(Map.of("adult", new BigDecimal("0.80")))
                .build();
    }

}
