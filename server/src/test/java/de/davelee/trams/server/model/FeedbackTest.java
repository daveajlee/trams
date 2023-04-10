package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Feedback</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class FeedbackTest {

    /**
     * Test case: build a <code>Feedback</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testBuilderToString() {
        Feedback feedback = Feedback.builder()
                .id(ObjectId.get())
                .customer(generateValidCustomer())
                .company("Mustermann GmbH")
                .message("Best transport company ever.")
                .extraInfos(Map.of("Punctuality", "10"))
                .answer("Thanks for the feedback")
                .emailAddress("max@mustermann.de")
                .build();
        assertNotNull(feedback.getId());
        assertNotNull(feedback.getCustomer());
        assertEquals("max@mustermann.de", feedback.getCustomer().getEmailAddress());
        assertEquals("max@mustermann.de", feedback.getEmailAddress());
        assertEquals("Best transport company ever.", feedback.getMessage());
        assertEquals(1, feedback.getExtraInfos().size());
        assertEquals("Thanks for the feedback", feedback.getAnswer());
        assertEquals("Mustermann GmbH", feedback.getCompany());
    }

    /**
     * Test case: construct an empty <code>Feedback</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSettersToString() {
        Feedback feedback = new Feedback();
        feedback.setId(ObjectId.get());
        feedback.setCustomer(generateValidCustomer());
        feedback.setMessage("Best transport company ever.");
        feedback.setExtraInfos(Map.of("Punctuality", "10"));
        feedback.setAnswer("Thanks for the feedback");
        feedback.setCompany("Mustermann GmbH");
        feedback.setEmailAddress("max@mustermann.de");
        assertNotNull(feedback.getId());
        assertNotNull(feedback.getCustomer());
        assertEquals("max@mustermann.de", feedback.getCustomer().getEmailAddress());
        assertEquals("max@mustermann.de", feedback.getEmailAddress());
        assertEquals("Best transport company ever.", feedback.getMessage());
        assertEquals(1, feedback.getExtraInfos().size());
        assertEquals("Thanks for the feedback", feedback.getAnswer());
        assertEquals("Mustermann GmbH", feedback.getCompany());
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
