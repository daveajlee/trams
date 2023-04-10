package de.davelee.trams.server.response;

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
        customerResponses[0] = CustomerResponse.builder()
                .address("1 Max Way, Musterdorf")
                .company("Mustermann GmbH")
                .emailAddress("max@mustermann.de")
                .firstName("Max")
                .lastName("Mustermann")
                .title("Mr")
                .telephoneNumber("01234 567890")
                .build();
        CustomersResponse customersResponse = new CustomersResponse();
        customersResponse.setCount(1L);
        customersResponse.setCustomerResponses(customerResponses);
        assertEquals(1L, customersResponse.getCount());
        assertEquals(1, customerResponses.length);
        assertEquals("Mustermann GmbH", customersResponse.getCustomerResponses()[0].getCompany());
    }

}
