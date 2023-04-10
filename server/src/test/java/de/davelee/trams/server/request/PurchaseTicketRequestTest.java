package de.davelee.trams.server.request;

import de.davelee.trams.server.response.PurchaseTicketResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the builder, getter and setter methods of the <code>PurchaseTicketRequest</code> class.
 */
public class PurchaseTicketRequestTest {

    @Test
    /**
     * Test the builder and ensure variables are set together using the getter methods.
     */
    public void testBuilder ( ) {
        PurchaseTicketRequest purchaseTicketRequest = PurchaseTicketRequest.builder()
                .company("Mustermann GmbH")
                .creditCardExpiryDate("01/2021")
                .creditCardNumber("123456780910")
                .creditCardType("VISA")
                .creditCardSecurityCode("12")
                .price(0.80)
                .quantity(1)
                .ticketTargetGroup("Adult")
                .ticketType("Single")
                .build();
        assertEquals("Mustermann GmbH", purchaseTicketRequest.getCompany());
        assertEquals("01/2021", purchaseTicketRequest.getCreditCardExpiryDate());
        assertEquals("123456780910", purchaseTicketRequest.getCreditCardNumber());
        assertEquals("VISA", purchaseTicketRequest.getCreditCardType());
        assertEquals("12", purchaseTicketRequest.getCreditCardSecurityCode());
        assertEquals(0.80, purchaseTicketRequest.getPrice());
        assertEquals(1, purchaseTicketRequest.getQuantity());
        assertEquals("Adult", purchaseTicketRequest.getTicketTargetGroup());
        assertEquals("Single", purchaseTicketRequest.getTicketType());
    }

    @Test
    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    public void testSettersAndGetters() {
        PurchaseTicketRequest purchaseTicketRequest = new PurchaseTicketRequest();
        purchaseTicketRequest.setCompany("Mustermann GmbH");
        purchaseTicketRequest.setCreditCardExpiryDate("01/2021");
        purchaseTicketRequest.setCreditCardNumber("123456780910");
        purchaseTicketRequest.setCreditCardType("VISA");
        purchaseTicketRequest.setCreditCardSecurityCode("12");
        purchaseTicketRequest.setPrice(0.80);
        purchaseTicketRequest.setQuantity(1);
        purchaseTicketRequest.setTicketTargetGroup("Adult");
        purchaseTicketRequest.setTicketType("Single");
        assertEquals("Mustermann GmbH", purchaseTicketRequest.getCompany());
        assertEquals("01/2021", purchaseTicketRequest.getCreditCardExpiryDate());
        assertEquals("123456780910", purchaseTicketRequest.getCreditCardNumber());
        assertEquals("VISA", purchaseTicketRequest.getCreditCardType());
        assertEquals("12", purchaseTicketRequest.getCreditCardSecurityCode());
        assertEquals(0.80, purchaseTicketRequest.getPrice());
        assertEquals(1, purchaseTicketRequest.getQuantity());
        assertEquals("Adult", purchaseTicketRequest.getTicketTargetGroup());
        assertEquals("Single", purchaseTicketRequest.getTicketType());
    }

}
