package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testSaveCustomer() {
        //Test data
        Feedback feedback = generateValidFeedback();
        //Mock important method in repository.
        Mockito.when(feedbackRepository.save(feedback)).thenReturn(feedback);
        //do actual test.
        assertTrue(feedbackService.save(feedback));
    }

    /**
     * Private helper method to generate a valid feedback.
     * @return a <code>Feedback</code> object containing valid test data.
     */
    private Feedback generateValidFeedback( ) {
        return Feedback.builder()
                .customer(generateValidCustomer())
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
