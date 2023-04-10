package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the <class>FeedbackRequest</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class FeedbackRequestTest {

    /**
     * Test case: build a <code>FeedbackRequest</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                .company("Mustermann GmbH")
                .emailAddress("max@mustermann.de")
                .message("Best transport company ever.")
                .extraInfos(Map.of("Punctuality", "10"))
                .build();
        assertEquals("Best transport company ever.", feedbackRequest.getMessage());
        assertEquals(1, feedbackRequest.getExtraInfos().size());
        assertEquals("max@mustermann.de", feedbackRequest.getEmailAddress());
        assertEquals("Mustermann GmbH", feedbackRequest.getCompany());
        assertEquals("FeedbackRequest(emailAddress=max@mustermann.de, company=Mustermann GmbH, message=Best transport company ever., extraInfos={Punctuality=10})", feedbackRequest.toString());
    }

    /**
     * Test case: construct an empty <code>FeedbackRequest</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setMessage("Best transport company ever.");
        feedbackRequest.setExtraInfos(Map.of("Punctuality", "10"));
        feedbackRequest.setEmailAddress("max@mustermann.de");
        feedbackRequest.setCompany("Mustermann GmbH");
        assertEquals("Best transport company ever.", feedbackRequest.getMessage());
        assertEquals(1, feedbackRequest.getExtraInfos().size());
        assertEquals("max@mustermann.de", feedbackRequest.getEmailAddress());
        assertEquals("Mustermann GmbH", feedbackRequest.getCompany());
    }

}
