package de.davelee.trams.server.response;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.utils.CustomerUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>FeedbackResponse</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class FeedbackResponseTest {

    /**
     * Test case: build a <code>FeedbackResponse</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        FeedbackResponse feedbackResponse = FeedbackResponse.builder()
                .id("63645gjg4t996")
                .customerResponse(CustomerUtils.convertCustomerToCustomerResponse(generateValidCustomer()))
                .message("Great transport company")
                .extraInfos(Map.of("Punctuality", "10")).build();
        assertNotNull(feedbackResponse.getCustomerResponse());
        assertEquals("63645gjg4t996", feedbackResponse.getId());
        assertEquals("Great transport company", feedbackResponse.getMessage());
        assertEquals(1, feedbackResponse.getExtraInfos().size());
        assertEquals("FeedbackResponse(id=63645gjg4t996, customerResponse=CustomerResponse(title=Mr, firstName=Max, lastName=Mustermann, emailAddress=max@mustermann.de, telephoneNumber=01234 567890, address=1 Max Way, Musterdorf, company=Mustermann GmbH), message=Great transport company, extraInfos={Punctuality=10})", feedbackResponse.toString());
    }

    /**
     * Test case: construct an empty <code>FeedbackResponse</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setId("63645gjg4t996");
        feedbackResponse.setCustomerResponse(CustomerUtils.convertCustomerToCustomerResponse(generateValidCustomer()));
        feedbackResponse.setMessage("Great transport company");
        feedbackResponse.setExtraInfos(Map.of("Punctuality", "10"));
        assertNotNull(feedbackResponse.getCustomerResponse());
        assertEquals("Great transport company", feedbackResponse.getMessage());
        assertEquals(1, feedbackResponse.getExtraInfos().size());
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
