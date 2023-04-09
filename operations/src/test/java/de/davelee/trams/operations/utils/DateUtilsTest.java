package de.davelee.trams.operations.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class tests the DateUtils class and ensures that it works successfully. Mocks are used for the database layer.
 * @author Dave Lee
 */
public class DateUtilsTest {

    /**
     * Check that a null date is handled correctly.
     */
    @Test
    public void testNullDate() {
        assertNull(DateUtils.convertLocalDateToDate(null));
    }
}
