package de.davelee.trams.crm.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Order</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class OrderTest {

    /**
     * Test case: build a <code>Order</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        Order order = Order.builder()
                .id(ObjectId.get())
                .confirmationId("feko04o24")
                .paymentType("Credit Card")
                .quantity(1)
                .ticketTargetGroup("Adult")
                .ticketType("Single")
                .qrCodeText("Adult Single 10.10.2021 12:30")
                .build();
        assertEquals("feko04o24", order.getConfirmationId());
        assertEquals("Credit Card", order.getPaymentType());
        assertEquals(1, order.getQuantity());
        assertEquals("Adult", order.getTicketTargetGroup());
        assertEquals("Single", order.getTicketType());
        assertNotNull(order.getQrCodeText());
        assertNotNull(order.getId());
    }

    /**
     * Test case: construct an empty <code>Order</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        Order order = new Order();
        order.setId(ObjectId.get());
        order.setConfirmationId("feko04o24");
        order.setPaymentType("Credit Card");
        order.setQuantity(1);
        order.setTicketTargetGroup("Adult");
        order.setTicketType("Single");
        order.setQrCodeText("Adult Single 10.10.2021 12:30");
        assertEquals("feko04o24", order.getConfirmationId());
        assertEquals("Credit Card", order.getPaymentType());
        assertEquals(1, order.getQuantity());
        assertEquals("Adult", order.getTicketTargetGroup());
        assertEquals("Single", order.getTicketType());
        assertNotNull(order.getQrCodeText());
        assertNotNull(order.getId());
    }

}
