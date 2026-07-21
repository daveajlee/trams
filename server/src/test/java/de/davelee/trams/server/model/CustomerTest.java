package de.davelee.trams.server.model;

import de.davelee.trams.server.request.CustomerRequest;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Customer</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class CustomerTest {

    /**
     * Test case: build a <code>Customer</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        Customer customer = new Customer();
        customer.setTitle("Mr");
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmailAddress("max@mustermann.de");
        customer.setTelephoneNumber("01234 567890");
        customer.setAddress("1 Max Way, Musterdorf");
        customer.setCompany("Mustermann GmbH");
        assertNotNull(customer.getId());
        assertEquals("Mr", customer.getTitle());
        assertEquals("Max", customer.getFirstName());
        assertEquals("Mustermann", customer.getLastName());
        assertEquals("1 Max Way, Musterdorf", customer.getAddress());
        assertEquals("01234 567890", customer.getTelephoneNumber());
        assertEquals("max@mustermann.de", customer.getEmailAddress());
    }

    /**
     * Test case: construct an empty <code>Customer</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        Customer customer = new Customer();
        customer.setId(ObjectId.get());
        customer.setTitle("Mr");
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setAddress("1 Max Way, Musterdorf");
        customer.setTelephoneNumber("01234 567890");
        customer.setEmailAddress("max@mustermann.de");
        customer.setCompany("Mustermann GmbH");
        assertNotNull(customer.getId());
        assertEquals("Mr", customer.getTitle());
        assertEquals("Max", customer.getFirstName());
        assertEquals("Mustermann", customer.getLastName());
        assertEquals("1 Max Way, Musterdorf", customer.getAddress());
        assertEquals("01234 567890", customer.getTelephoneNumber());
        assertEquals("max@mustermann.de", customer.getEmailAddress());
        assertEquals("Mustermann GmbH", customer.getCompany());
    }

}
