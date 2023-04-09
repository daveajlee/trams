package de.davelee.trams.business.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Company</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class CompanyTest {

    /**
     * Test case: construct an empty <code>Company</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSetters() {
        Company company = new Company();
        company.setName("Mustermann GmbH");
        company.setBalance(BigDecimal.valueOf(10000.0));
        company.setPlayerName("Max Mustermann");
        company.setSatisfactionRate(BigDecimal.valueOf(100.0));
        company.setTime(LocalDateTime.of(2020,12,28,14,22));
        company.setId(ObjectId.get());
        assertEquals("Mustermann GmbH", company.getName());
        assertEquals(10000.0, company.getBalance().doubleValue());
        assertEquals("Max Mustermann", company.getPlayerName());
        assertEquals(100.0, company.getSatisfactionRate().doubleValue());
        assertNotNull(company.getTime());
        assertNotNull(company.getId());
    }

}
