package de.davelee.trams.crm.response;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.utils.CustomerUtils;
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
        feedbackResponses[0] = FeedbackResponse.builder()
                .customerResponse(CustomerUtils.convertCustomerToCustomerResponse(generateValidCustomer()))
                .message("Great transport company")
                .extraInfos(Map.of("Punctuality", "10")).build();
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
        return Customer.builder()
                .title("Mr")
                .firstName("Max")
                .lastName("Mustermann")
                .emailAddress("max@mustermann.de")
                .telephoneNumber("01234 567890")
                .address("1 Max Way, Musterdorf")
                .company("Mustermann GmbH")
                .build();
    }

}
