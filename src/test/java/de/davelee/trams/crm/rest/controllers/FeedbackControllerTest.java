package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.request.CustomerRequest;
import de.davelee.trams.crm.request.FeedbackRequest;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
