package de.davelee.trams.crm.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Ticket</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class TicketTest {

    /**
     * Test case: build a <code>Ticket</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        Ticket ticket = Ticket.builder()
                .id(ObjectId.get())
                .shortId("single")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .type("Single Ticket")
                .sortOrder(1)
                .priceList(Map.of("adult", new BigDecimal("0.80")))
                .build();
        assertNotNull(ticket.getId());
        assertEquals("single", ticket.getShortId());
        assertEquals("Mustermann GmbH", ticket.getCompany());
        assertEquals("Valid for 1 hour", ticket.getDescription());
        assertEquals("Single Ticket", ticket.getType());
        assertEquals(1, ticket.getSortOrder());
        assertEquals(1, ticket.getPriceList().size());

    }

    /**
     * Test case: construct an empty <code>Ticket</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        Ticket ticket = new Ticket();
        ticket.setId(ObjectId.get());
        ticket.setShortId("single");
        ticket.setCompany("Mustermann GmbH");
        ticket.setDescription("Valid for 1 hour");
        ticket.setType("Single Ticket");
        ticket.setSortOrder(1);
        ticket.setPriceList(Map.of("adult", new BigDecimal("0.80")));
        assertNotNull(ticket.getId());
        assertEquals("single", ticket.getShortId());
        assertEquals("Mustermann GmbH", ticket.getCompany());
        assertEquals("Valid for 1 hour", ticket.getDescription());
        assertEquals("Single Ticket", ticket.getType());
        assertEquals(1, ticket.getSortOrder());
        assertEquals(1, ticket.getPriceList().size());
    }
}
