package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Ticket;
import de.davelee.trams.server.request.FeedbackRequest;
import de.davelee.trams.server.request.PurchaseTicketRequest;
import de.davelee.trams.server.response.PurchaseTicketResponse;
import de.davelee.trams.server.service.OrderService;
import de.davelee.trams.server.service.TicketService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the Order endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private TicketService ticketService;

    /**
     * Test case: add an order to the system based on a valid purchase ticket request.
     * Expected Result: order added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in customer & feedback service.
        Mockito.when(ticketService.findByCompanyAndType("Mustermann GmbH", "Single")).thenReturn(
                List.of(Ticket.builder()
                        .id(ObjectId.get())
                        .shortId("single")
                        .company("Mustermann GmbH")
                        .description("Valid for 1 hour")
                        .type("Single Ticket")
                        .sortOrder(1)
                        .priceList(Map.of("adult", new BigDecimal("0.80")))
                        .build()));
        Mockito.when(orderService.save(any())).thenReturn(true);
        //Add order so that test is successfully.
        PurchaseTicketRequest purchaseTicketRequest = PurchaseTicketRequest.builder()
                .company("Mustermann GmbH")
                .creditCardExpiryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")))
                .creditCardNumber("123456780910")
                .creditCardType("VISA")
                .creditCardSecurityCode("12")
                .price(0.80)
                .quantity(1)
                .ticketTargetGroup("adult")
                .ticketType("Single")
                .build();
        ResponseEntity<PurchaseTicketResponse> responseEntity = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        assertTrue(responseEntity.getBody().isSuccess());
        assertNotNull(responseEntity.getBody().getQrCode());
    }

    /**
     * Test case: add an order to the system based on an invalid purchase ticket request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Add feedback so that test is successfully.
        PurchaseTicketRequest purchaseTicketRequest = PurchaseTicketRequest.builder()
                .company("Mustermann GmbH")
                .creditCardNumber("123456780910")
                .creditCardType("VISA")
                .creditCardSecurityCode("12")
                .price(0.80)
                .quantity(1)
                .ticketTargetGroup("adult")
                .ticketType("Single")
                .build();
        ResponseEntity<PurchaseTicketResponse> responseEntity = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Set credit card number too short.
        purchaseTicketRequest.setCreditCardExpiryDate("02/2021");
        purchaseTicketRequest.setCreditCardNumber("123456789");
        ResponseEntity<PurchaseTicketResponse> responseEntity2 = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Now set the expiry date to in the past.
        purchaseTicketRequest.setCreditCardNumber("123456780910");
        ResponseEntity<PurchaseTicketResponse> responseEntity3 = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity3.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Now with 0 tickets returned.
        Mockito.when(ticketService.findByCompanyAndType("Mustermann GmbH", "Single")).thenReturn(List.of());
        purchaseTicketRequest.setCreditCardExpiryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")));
        ResponseEntity<PurchaseTicketResponse> responseEntity4 = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity4.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Now with a different price than the user paid.
        Mockito.when(ticketService.findByCompanyAndType("Mustermann GmbH", "Single")).thenReturn(
                List.of(Ticket.builder()
                        .id(ObjectId.get())
                        .shortId("single")
                        .company("Mustermann GmbH")
                        .description("Valid for 1 hour")
                        .type("Single Ticket")
                        .sortOrder(1)
                        .priceList(Map.of("adult", new BigDecimal("0.90")))
                        .build()));
        ResponseEntity<PurchaseTicketResponse> responseEntity5 = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity5.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: add an order to the system based on a valid purchase ticket request
     * but the database is not available.
     * Expected Result: internal server error.
     */
    @Test
    public void testValidNoDatabase() {
        //Mock important methods in customer & feedback service.
        Mockito.when(ticketService.findByCompanyAndType("Mustermann GmbH", "Single")).thenReturn(
                List.of(Ticket.builder()
                        .id(ObjectId.get())
                        .shortId("single")
                        .company("Mustermann GmbH")
                        .description("Valid for 1 hour")
                        .type("Single Ticket")
                        .sortOrder(1)
                        .priceList(Map.of("adult", new BigDecimal("0.80")))
                        .build()));
        Mockito.when(orderService.save(any())).thenReturn(false);
        //Add order so that test is successfully.
        PurchaseTicketRequest purchaseTicketRequest = PurchaseTicketRequest.builder()
                .company("Mustermann GmbH")
                .creditCardExpiryDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy")))
                .creditCardNumber("123456780910")
                .creditCardType("VISA")
                .creditCardSecurityCode("12")
                .price(0.80)
                .quantity(1)
                .ticketTargetGroup("adult")
                .ticketType("Single")
                .build();
        ResponseEntity<PurchaseTicketResponse> responseEntity = orderController.orderTicket(purchaseTicketRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertFalse(responseEntity.getBody().isSuccess());
        assertNotNull(responseEntity.getBody().getErrorMessage());
    }

}
