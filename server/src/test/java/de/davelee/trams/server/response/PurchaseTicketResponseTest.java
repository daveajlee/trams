package de.davelee.trams.server.response;

import net.glxn.qrgen.javase.QRCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests the builder, getter and setter methods of the <code>PurchaseTicketResponse</code> class.
 */
public class PurchaseTicketResponseTest {

    @Test
    public void testGoodPurchase( ) {
        PurchaseTicketResponse purchaseTicketResponse = PurchaseTicketResponse.builder()
                .success(true)
                .qrCode("Adult Single 10.10.2021 12:30")
                .build();
        assertEquals(true, purchaseTicketResponse.isSuccess());
        assertNotNull(purchaseTicketResponse.getQrCode());
    }

    @Test
    public void testBadPurchase( ) {
        PurchaseTicketResponse purchaseTicketResponse = PurchaseTicketResponse.builder()
                .success(false)
                .errorMessage("Payment method was not valid")
                .build();
        assertEquals(false, purchaseTicketResponse.isSuccess());
        assertEquals("Payment method was not valid", purchaseTicketResponse.getErrorMessage());
    }

    @Test
    public void testGoodPurchaseWithSetters( ) {
        PurchaseTicketResponse purchaseTicketResponse = new PurchaseTicketResponse();
        purchaseTicketResponse.setSuccess(true);
        purchaseTicketResponse.setQrCode("Adult Single 10.10.2021 12:30");
        assertEquals(true, purchaseTicketResponse.isSuccess());
        assertNotNull(purchaseTicketResponse.getQrCode());
    }

    @Test
    public void testBadPurchaseWithSetters( ) {
        PurchaseTicketResponse purchaseTicketResponse = new PurchaseTicketResponse();
        purchaseTicketResponse.setSuccess(false);
        purchaseTicketResponse.setErrorMessage("Payment method was not valid");
    }

}
