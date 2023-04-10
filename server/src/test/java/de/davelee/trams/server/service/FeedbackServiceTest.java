package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.model.Feedback;
import de.davelee.trams.server.repository.FeedbackRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the FeedbackService class - the FeedbackRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    /**
     * Test case: save a new feedback.
     * Expected Result: true.
     */
    @Test
    public void testSaveFeedback() {
        //Test data
        Feedback feedback = generateValidFeedback();
        //Mock important method in repository.
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        //do actual test.
        assertTrue(feedbackService.save(feedback));
    }

    /**
     * Test case: get feedbacks for company and customer.
     * Expected Result: list with size 1.
     */
    @Test
    public void testGetFeedbackCompanyCustomer() {
        //Mock important method in repository.
        Mockito.when(feedbackRepository.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de")).thenReturn(List.of(generateValidFeedback()));
        //do actual test.
        assertEquals(1, feedbackService.findByCompanyAndCustomer("Mustermann GmbH", "max@mustermann.de").size());
    }

    /**
     * Test case: get feedbacks for company.
     * Expected Result: list with size 1.
     */
    @Test
    public void testGetFeedbackCompany() {
        //Mock important method in repository.
        Mockito.when(feedbackRepository.findByCompany("Mustermann GmbH")).thenReturn(List.of(generateValidFeedback()));
        //do actual test.
        assertEquals(1, feedbackService.findByCompany("Mustermann GmbH").size());
    }

    /**
     * Test case: add an answer to a feedback.
     * Expected Result: true.
     */
    @Test
    public void testSaveValidAnswer() {
        //Test data
        Feedback feedback = generateValidFeedback();
        //Mock important method in repository.
        Mockito.when(feedbackRepository.findById(new ObjectId("615825196d0c882034e85965"))).thenReturn(feedback);
        //do actual test.
        assertTrue(feedbackService.addAnswerToFeedback("Thanks for the answer", "615825196d0c882034e85965"));
    }

    /**
     * Test case: add an invalid answer to a feedback.
     * Expected Result: false.
     */
    @Test
    public void testSaveInvalidAnswer() {
        //Test data
        Feedback feedback = generateValidFeedback();
        //Mock important method in repository.
        Mockito.when(feedbackRepository.findById(new ObjectId("615825196d0c882034e85965"))).thenReturn(null);
        //do actual test.
        assertFalse(feedbackService.addAnswerToFeedback("Thanks for the answer", "615825196d0c882034e85965"));
    }

    /**
     * Private helper method to generate a valid feedback.
     * @return a <code>Feedback</code> object containing valid test data.
     */
    private Feedback generateValidFeedback( ) {
        return Feedback.builder()
                .customer(generateValidCustomer())
                .id(new ObjectId("615825196d0c882034e85965"))
                .message("Very good transport company")
                .company("Mustermann GmbH")
                .answer("Thanks for the feedback")
                .extraInfos(Map.of("Punctuality","10"))
                .build();
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
