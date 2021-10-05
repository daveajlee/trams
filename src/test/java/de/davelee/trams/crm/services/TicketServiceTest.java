package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Ticket;
import de.davelee.trams.crm.repository.TicketRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

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
