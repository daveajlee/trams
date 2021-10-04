package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.request.AnswerRequest;
import de.davelee.trams.crm.request.CustomerRequest;
import de.davelee.trams.crm.request.FeedbackRequest;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import de.davelee.trams.crm.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the Feedback endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class FeedbackControllerTest {

    @InjectMocks
    private FeedbackController feedbackController;

    @Mock
    private CustomerService customerService;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    /**
     * Test case: add a feedback to the system based on a valid feedback request.
     * Expected Result: feedback added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in customer & feedback service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de")).thenReturn(generateValidCustomer());
        Mockito.when(feedbackService.save(any())).thenReturn(true);
        //Add feedback so that test is successfully.
        FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                .emailAddress("max@mustermann.de")
                .company("Mustermann GmbH")
                .message("Great transport company")
                .extraInfos(Map.of("Punctuality","10"))
                .build();
        ResponseEntity<Void> responseEntity = feedbackController.addFeedback(feedbackRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: add a feedback to the system based on an invalid feedback request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Mock important methods in customer & feedback service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de")).thenReturn(null);
        //Add feedback so that test is successfully.
        FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                .emailAddress("")
                .company("Mustermann GmbH")
                .message("Great transport company")
                .extraInfos(Map.of("Punctuality","10"))
                .build();
        ResponseEntity<Void> responseEntity = feedbackController.addFeedback(feedbackRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        feedbackRequest.setEmailAddress("max@mustermann.de");
        ResponseEntity<Void> responseEntity2 = feedbackController.addFeedback(feedbackRequest);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: add an answer to a feedback to the system based on a valid answer request.
     * Expected Result: answer added successfully.
     */
    @Test
    public void testValidAddAnswer() {
        //Mock important methods in customer & feedback service.
        Mockito.when(feedbackService.addAnswerToFeedback("Thanks for the feedback", "63645gjg4t996")).thenReturn(true);
        Mockito.when(feedbackService.save(any())).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        //Add answer so that test is successfully.
        AnswerRequest answerRequest = AnswerRequest.builder()
                .answer("Thanks for the feedback")
                .objectId("63645gjg4t996")
                .token("mmustermann-ghgkg").build();
        ResponseEntity<Void> responseEntity = feedbackController.addAnswer(answerRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: add an answer to a feedback to the system based on an invalid answer request.
     * Expected Result: either bad request or 204.
     */
    @Test
    public void testInvalidAddAnswer() {
        //Mock important methods in customer & feedback service.
        Mockito.when(feedbackService.addAnswerToFeedback("Thanks for the feedback", "63645gjg4t996")).thenReturn(false);
        Mockito.when(feedbackService.save(any())).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-djkf")).thenReturn(false);
        //Add answer so that test is successfully.
        AnswerRequest answerRequest = AnswerRequest.builder()
                .answer("Thanks for the feedback")
                .objectId("")
                .token("mmustermann-ghgkg").build();
        ResponseEntity<Void> responseEntity = feedbackController.addAnswer(answerRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Set object id to test 204.
        answerRequest.setObjectId("63645gjg4t996");
        ResponseEntity<Void> responseEntity2 = feedbackController.addAnswer(answerRequest);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
        //Use a different token to ensure forbidden.
        answerRequest.setToken("mmustermann-djkf");
        ResponseEntity<Void> responseEntity3 = feedbackController.addAnswer(answerRequest);
        assertTrue(responseEntity3.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
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
