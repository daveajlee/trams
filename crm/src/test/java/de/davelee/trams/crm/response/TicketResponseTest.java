package de.davelee.trams.crm.response;

import de.davelee.trams.crm.request.TicketRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>TicketResponse</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class TicketResponseTest {

    /**
     * Test case: build a <code>TicketResponse</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        TicketResponse ticket = TicketResponse.builder()
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
        assertEquals("TicketResponse(shortId=single, type=Single Ticket, description=Valid for 1 hour, sortOrder=1, priceList={adult=0.8}, company=Mustermann GmbH)", ticket.toString());
    }

    /**
     * Test case: construct an empty <code>TicketResponse</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        TicketResponse ticket = new TicketResponse();
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
