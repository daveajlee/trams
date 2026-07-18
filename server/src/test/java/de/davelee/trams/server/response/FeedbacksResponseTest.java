package de.davelee.trams.server.response;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.utils.CustomerUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>FeedbacksResponse</code> class.
 */
public class FeedbacksResponseTest {

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testGettersAndSetters() {
        FeedbackResponse[] feedbackResponses = new FeedbackResponse[1];
        feedbackResponses[0] = new FeedbackResponse();
        feedbackResponses[0].setCustomerResponse(CustomerUtils.convertCustomerToCustomerResponse(generateValidCustomer()));
        feedbackResponses[0].setMessage("Great transport company");
        feedbackResponses[0].setExtraInfos(Map.of("Punctuality", "10"));
        FeedbacksResponse feedbacksResponse = new FeedbacksResponse();
        feedbacksResponse.setCount(1L);
        feedbacksResponse.setFeedbackResponses(feedbackResponses);
        assertEquals(1L, feedbacksResponse.getCount());
        assertEquals(1, feedbackResponses.length);
        assertEquals("Great transport company", feedbacksResponse.getFeedbackResponses()[0].getMessage());
    }

    /**
     * Private helper method to generate a valid customer.
     * @return a <code>Customer</code> object containing valid test data.
     */
    private Customer generateValidCustomer( ) {
        Customer customer = new Customer();
        customer.setTitle("Mr");
        customer.setFirstName("Max");
        customer.setLastName("Mustermann");
        customer.setEmailAddress("max@mustermann.de");
        customer.setTelephoneNumber("01234 567890");
        customer.setAddress("1 Max Way, Musterdorf");
        customer.setCompany("Mustermann GmbH");
        return customer;
    }

}
