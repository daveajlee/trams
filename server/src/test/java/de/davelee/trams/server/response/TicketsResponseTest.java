package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>TicketsResponse</code> class.
 */
public class TicketsResponseTest {

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testGettersAndSetters() {
        TicketResponse[] ticketResponses = new TicketResponse[1];
        ticketResponses[0] = generateValidTicketResponse();
        TicketsResponse ticketsResponse = new TicketsResponse();
        ticketsResponse.setCount(1L);
        ticketsResponse.setTicketResponses(ticketResponses);
        assertEquals(1L, ticketsResponse.getCount());
        assertEquals(1, ticketResponses.length);
        assertEquals("Single Ticket", ticketsResponse.getTicketResponses()[0].getType());
    }

    /**
     * Private helper method to generate a valid ticket.
     * @return a <code>Ticket</code> object containing valid test data.
     */
    private TicketResponse generateValidTicketResponse( ) {
        return TicketResponse.builder()
                .shortId("single")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .type("Single Ticket")
                .sortOrder(1)
                .priceList(Map.of("adult", 0.80))
                .build();
    }

}
