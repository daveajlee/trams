package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Ticket;
import de.davelee.trams.crm.response.TicketsResponse;
import de.davelee.trams.crm.services.TicketService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the Tickets endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
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
        //Mock important methods
        Mockito.when(ticketService.findByCompany("Mustermann GmbH")).thenReturn(
                List.of(Ticket.builder()
                        .id(ObjectId.get())
                        .shortId("single")
                        .company("Mustermann GmbH")
                        .description("Valid for 1 hour")
                        .type("Single Ticket")
                        .sortOrder(1)
                        .priceList(Map.of("adult", new BigDecimal("0.80")))
                        .build()));
        //Perform test
        ResponseEntity<TicketsResponse> responseEntity = ticketsController.getFeedbacksByCompany("Mustermann GmbH");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: retrieve the tickets for an empty company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidGetByCompany() {
        //Perform test
        ResponseEntity<TicketsResponse> responseEntity = ticketsController.getFeedbacksByCompany("");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

}
