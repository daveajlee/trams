package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the BalanceResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class BalanceResponseTest {

    /**
     * Ensure that a BalanceResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
       BalanceResponse balanceResponse = new BalanceResponse();
       balanceResponse.setBalance(10000.0);
       balanceResponse.setCompany("Lee Transport");
       assertEquals(10000.0, balanceResponse.getBalance());
       assertEquals("Lee Transport", balanceResponse.getCompany());
       assertEquals("BalanceResponse(company=Lee Transport, balance=10000.0)", balanceResponse.toString());
    }


}
