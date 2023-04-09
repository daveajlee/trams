package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.request.TicketRequest;
import de.davelee.trams.crm.services.TicketService;
import de.davelee.trams.crm.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the Ticket endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    /**
     * Test case: add a ticket to the system based on a valid ticket request.
     * Expected Result: ticket added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods
        Mockito.when(ticketService.save(any())).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        //Add ticket so that test is successfully.
        TicketRequest ticket = TicketRequest.builder()
                .shortId("single")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .token("mmustermann-ghgkg")
                .type("Single Ticket")
                .sortOrder(1)
                .priceList(Map.of("adult", 0.80))
                .build();
        ResponseEntity<Void> responseEntity = ticketController.addTicket(ticket);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: add a ticket to the system based on an invalid ticket request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Mock important methods in user service.
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkf")).thenReturn(false);
        //Add ticket so that test is successfully.
        TicketRequest ticket = TicketRequest.builder()
                .shortId("")
                .company("Mustermann GmbH")
                .description("Valid for 1 hour")
                .type("Single Ticket")
                .token("mmustermann-ghgkf")
                .sortOrder(1)
                .priceList(Map.of("adult", 0.80))
                .build();
        ResponseEntity<Void> responseEntity = ticketController.addTicket(ticket);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        ticket.setShortId("single");
        ResponseEntity<Void> responseEntity2 = ticketController.addTicket(ticket);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
    }

}
