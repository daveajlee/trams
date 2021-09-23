package de.davelee.trams.crm.model;

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

    @Test
    /**
     * Test case: build a <code>Customer</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    public void testBuilderToString() {
        Customer customer = Customer.builder()
                .id(ObjectId.get())
                .title("Mr")
                .firstName("Max")
                .lastName("Mustermann")
                .address("1 Max Way, Musterdorf")
                .telephoneNumber("01234 567890")
                .emailAddress("max@mustermann.de").build();
        assertNotNull(customer.getId());
        assertEquals("Mr", customer.getTitle());
        assertEquals("Max", customer.getFirstName());
        assertEquals("Mustermann", customer.getLastName());
        assertEquals("1 Max Way, Musterdorf", customer.getAddress());
        assertEquals("01234 567890", customer.getTelephoneNumber());
        assertEquals("max@mustermann.de", customer.getEmailAddress());
    }

    @Test
    /**
     * Test case: construct an empty <code>Customer</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    public void testSettersToString() {
        Customer customer = new Customer();
        customer.setId(ObjectId.get());
        customer.setTitle("Mr");
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setAddress("1 Max Way, Musterdorf");
        customer.setTelephoneNumber("01234 567890");
        customer.setEmailAddress("max@mustermann.de");
        assertNotNull(customer.getId());
        assertEquals("Mr", customer.getTitle());
        assertEquals("Max", customer.getFirstName());
        assertEquals("Mustermann", customer.getLastName());
        assertEquals("1 Max Way, Musterdorf", customer.getAddress());
        assertEquals("01234 567890", customer.getTelephoneNumber());
        assertEquals("max@mustermann.de", customer.getEmailAddress());
    }

}
