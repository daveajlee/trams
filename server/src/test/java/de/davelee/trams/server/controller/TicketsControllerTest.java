package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Ticket;
import de.davelee.trams.server.response.TicketsResponse;
import de.davelee.trams.server.service.TicketService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the Tickets endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TicketsControllerTest {

    @InjectMocks
    private TicketsController ticketsController;

    @Mock
    private TicketService ticketService;

    /**
     * Test case: retrieve the tickets for a particular company.
     * Expected Result: ticket list returned successfully.
     */
    @Test
    public void testValidGetByCompany() {
        Ticket ticket = new Ticket();
        ticket.setId(ObjectId.get());
        ticket.setShortId("single");
        ticket.setCompany("Mustermann GmbH");
        ticket.setDescription("Valid for 1 hour");
        ticket.setType("Single Ticket");
        ticket.setSortOrder(1);
        ticket.setPriceList(Map.of("adult", new BigDecimal("0.80")));
        //Mock important methods
        Mockito.when(ticketService.findByCompany("Mustermann GmbH")).thenReturn(
                List.of(ticket));
        //Perform test
        ResponseEntity<TicketsResponse> responseEntity = ticketsController.getTicketsByCompany("Mustermann GmbH");
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
    }

    /**
     * Test case: retrieve the tickets for an empty company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidGetByCompany() {
        //Perform test
        ResponseEntity<TicketsResponse> responseEntity = ticketsController.getTicketsByCompany("");
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

}
