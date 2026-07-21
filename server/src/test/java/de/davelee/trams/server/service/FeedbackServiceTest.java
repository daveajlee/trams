package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.model.Feedback;
import de.davelee.trams.server.repository.FeedbackRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the FeedbackService class - the FeedbackRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
        Feedback feedback = new Feedback();
        feedback.setCustomer(generateValidCustomer());
        feedback.setId(new ObjectId("615825196d0c882034e85965"));
        feedback.setMessage("Very good transport company");
        feedback.setCompany("Mustermann GmbH");
        feedback.setAnswer("Thanks for the feedback");
        feedback.setExtraInfos(Map.of("Punctuality","10"));
        return feedback;
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
