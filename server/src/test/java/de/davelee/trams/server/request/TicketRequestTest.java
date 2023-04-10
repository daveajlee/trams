package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>TicketRequest</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class TicketRequestTest {

    /**
     * Test case: build a <code>TicketRequest</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        TicketRequest ticket = TicketRequest.builder()
                .shortId("single")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .type("Single Ticket")
                .sortOrder(1)
                .priceList(Map.of("adult", 0.80))
                .build();
        assertEquals("single", ticket.getShortId());
        assertEquals("Mustermann GmbH", ticket.getCompany());
        assertEquals("Valid for 1 hour", ticket.getDescription());
        assertEquals("Single Ticket", ticket.getType());
        assertEquals(1, ticket.getSortOrder());
        assertEquals(1, ticket.getPriceList().size());
        assertEquals("TicketRequest(shortId=single, type=Single Ticket, description=Valid for 1 hour, sortOrder=1, priceList={adult=0.8}, company=Mustermann GmbH, token=null)", ticket.toString());
    }

    /**
     * Test case: construct an empty <code>TicketRequest</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        TicketRequest ticket = new TicketRequest();
        ticket.setShortId("single");
        ticket.setCompany("Mustermann GmbH");
        ticket.setDescription("Valid for 1 hour");
        ticket.setType("Single Ticket");
        ticket.setSortOrder(1);
        ticket.setPriceList(Map.of("adult", 0.80));
        assertEquals("single", ticket.getShortId());
        assertEquals("Mustermann GmbH", ticket.getCompany());
        assertEquals("Valid for 1 hour", ticket.getDescription());
        assertEquals("Single Ticket", ticket.getType());
        assertEquals(1, ticket.getSortOrder());
        assertEquals(1, ticket.getPriceList().size());
    }
}
