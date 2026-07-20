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
        Feedback feedback = new Feedback();
        feedback.setId(ObjectId.get());
        feedback.setCustomer(generateValidCustomer());
        feedback.setCompany("Mustermann GmbH");
        feedback.setMessage("Best transport company ever.");
        feedback.setExtraInfos(Map.of("Punctuality", "10"));
        feedback.setAnswer("Thanks for the feedback");
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
