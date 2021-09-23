package de.davelee.trams.crm.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>CustomerRequest</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class CustomerRequestTest {

    @Test
    /**
     * Test case: build a <code>CustomerRequest</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    public void testBuilderToString() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .title("Mr")
                .firstName("Max")
                .lastName("Mustermann")
                .address("1 Max Way, Musterdorf")
                .telephoneNumber("01234 567890")
                .emailAddress("max@mustermann.de").build();
        assertEquals("Mr", customerRequest.getTitle());
        assertEquals("Max", customerRequest.getFirstName());
        assertEquals("Mustermann", customerRequest.getLastName());
        assertEquals("1 Max Way, Musterdorf", customerRequest.getAddress());
        assertEquals("01234 567890", customerRequest.getTelephoneNumber());
        assertEquals("max@mustermann.de", customerRequest.getEmailAddress());
        assertEquals("CustomerRequest(title=Mr, firstName=Max, lastName=Mustermann, emailAddress=max@mustermann.de, telephoneNumber=01234 567890, address=1 Max Way, Musterdorf)", customerRequest.toString());
    }

    @Test
    /**
     * Test case: construct an empty <code>CustomerRequest</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    public void testSettersToString() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setTitle("Mr");
        customerRequest.setFirstName("Max");
        customerRequest.setLastName("Mustermann");
        customerRequest.setAddress("1 Max Way, Musterdorf");
        customerRequest.setTelephoneNumber("01234 567890");
        customerRequest.setEmailAddress("max@mustermann.de");
        assertEquals("Mr", customerRequest.getTitle());
        assertEquals("Max", customerRequest.getFirstName());
        assertEquals("Mustermann", customerRequest.getLastName());
        assertEquals("1 Max Way, Musterdorf", customerRequest.getAddress());
        assertEquals("01234 567890", customerRequest.getTelephoneNumber());
        assertEquals("max@mustermann.de", customerRequest.getEmailAddress());
    }

}
