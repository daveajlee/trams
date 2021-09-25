package de.davelee.trams.crm.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>CustomerResponse</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class CustomerResponseTest {

    /**
     * Test case: build a <code>CustomerResponse</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .title("Mr")
                .firstName("Max")
                .lastName("Mustermann")
                .address("1 Max Way, Musterdorf")
                .telephoneNumber("01234 567890")
                .emailAddress("max@mustermann.de")
                .company("Mustermann GmbH").build();
        assertEquals("Mr", customerResponse.getTitle());
        assertEquals("Max", customerResponse.getFirstName());
        assertEquals("Mustermann", customerResponse.getLastName());
        assertEquals("1 Max Way, Musterdorf", customerResponse.getAddress());
        assertEquals("01234 567890", customerResponse.getTelephoneNumber());
        assertEquals("max@mustermann.de", customerResponse.getEmailAddress());
        assertEquals("Mustermann GmbH", customerResponse.getCompany());
        assertEquals("CustomerResponse(title=Mr, firstName=Max, lastName=Mustermann, emailAddress=max@mustermann.de, telephoneNumber=01234 567890, address=1 Max Way, Musterdorf, company=Mustermann GmbH)", customerResponse.toString());
    }

    /**
     * Test case: construct an empty <code>CustomerResponse</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setTitle("Mr");
        customerResponse.setFirstName("Max");
        customerResponse.setLastName("Mustermann");
        customerResponse.setAddress("1 Max Way, Musterdorf");
        customerResponse.setTelephoneNumber("01234 567890");
        customerResponse.setEmailAddress("max@mustermann.de");
        assertEquals("Mr", customerResponse.getTitle());
        assertEquals("Max", customerResponse.getFirstName());
        assertEquals("Mustermann", customerResponse.getLastName());
        assertEquals("1 Max Way, Musterdorf", customerResponse.getAddress());
        assertEquals("01234 567890", customerResponse.getTelephoneNumber());
        assertEquals("max@mustermann.de", customerResponse.getEmailAddress());
    }

}
