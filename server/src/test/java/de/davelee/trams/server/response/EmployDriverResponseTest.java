package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests the EmployDriverResponse class and ensures that its works correctly.
 * @author Dave Lee
 */
public class EmployDriverResponseTest {

    /**
     * Ensure that a EmployDriverResponse class can be correctly instantiated.
     */
    @Test
    public void testCreateResponse() {
        EmployDriverResponse employDriverResponse = new EmployDriverResponse();
        employDriverResponse.setEmployed(true);
        employDriverResponse.setEmploymentCost(500);
        assertTrue(employDriverResponse.isEmployed());
        assertEquals(500, employDriverResponse.getEmploymentCost());
    }

}
