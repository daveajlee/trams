package de.davelee.trams.server.response;

import de.davelee.trams.server.request.CustomerRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>CustomersResponse</code> class.
 */
public class CustomersResponseTest {

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testGettersAndSetters() {
        CustomerResponse[] customerResponses = new CustomerResponse[1];
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setTitle("Mr");
        customerResponse.setFirstName("Max");
        customerResponse.setLastName("Mustermann");
        customerResponse.setEmailAddress("max@mustermann.de");
        customerResponse.setTelephoneNumber("01234 567890");
        customerResponse.setAddress("1 Max Way, Musterdorf");
        customerResponse.setCompany("Mustermann GmbH");
        customerResponses[0] = customerResponse;
        CustomersResponse customersResponse = new CustomersResponse();
        customersResponse.setCount(1L);
        customersResponse.setCustomerResponses(customerResponses);
        assertEquals(1L, customersResponse.getCount());
        assertEquals(1, customerResponses.length);
        assertEquals("Mustermann GmbH", customersResponse.getCustomerResponses()[0].getCompany());
    }

}
