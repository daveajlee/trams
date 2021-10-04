package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.response.FeedbacksResponse;
import de.davelee.trams.crm.services.CustomerService;
import de.davelee.trams.crm.services.FeedbackService;
import de.davelee.trams.crm.services.UserService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Test cases for the feedbacks endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class FeedbacksControllerTest {

    @InjectMocks
    private FeedbacksController feedbacksController;

    @Mock
    private CustomerService customerService;

    @Mock
    private FeedbackService feedbackService;

    @Mock
    private UserService userService;

    /**
     * Test case: attempt to find feedbacks for a company and customer.
     * Expected Result: ok.
     */
    @Test
    public void testValidFindCustomers() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de")).thenReturn(generateValidCustomer());
        Mockito.when(feedbackService.findByCompanyAndCustomer(eq("Mustermann GmbH"), any())).thenReturn(List.of(generateValidFeedback()));
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        //Perform tests
        ResponseEntity<FeedbacksResponse> responseEntity = feedbacksController.getFeedbacksByCompanyAndEmail("Mustermann GmbH", "max@mustermann.de", "mmustermann-ghgkg");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: attempt to find customers without specifying a company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidFindCustomers() {
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-djkf")).thenReturn(false);
        //Perform tests
        ResponseEntity<FeedbacksResponse> responseEntity = feedbacksController.getFeedbacksByCompanyAndEmail("Mustermann GmbH", null, "mmustermann-ghgkg");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Perform test for forbidden.
        ResponseEntity<FeedbacksResponse> responseEntity2 = feedbacksController.getFeedbacksByCompanyAndEmail("Mustermann GmbH", "max@mustermann.de", "mmustermann-djkf");
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Test case: attempt to find feedbacks for a company.
     * Expected Result: ok.
     */
    @Test
    public void testValidFindCompany() {
        //Mock the important methods in customer service.
        Mockito.when(feedbackService.findByCompany("Mustermann GmbH")).thenReturn(List.of(generateValidFeedback()));
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        //Perform tests
        ResponseEntity<FeedbacksResponse> responseEntity = feedbacksController.getFeedbacksByCompany("Mustermann GmbH", "mmustermann-ghgkg");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: attempt to find feedbacks without specifying a company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidFindCompany() {
        Mockito.when(userService.checkAuthToken("mmustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("mmustermann-djkf")).thenReturn(false);
        //Perform tests
        ResponseEntity<FeedbacksResponse> responseEntity = feedbacksController.getFeedbacksByCompany(null, "mmustermann-ghgkg");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        //Perform test for forbidden.
        ResponseEntity<FeedbacksResponse> responseEntity2 = feedbacksController.getFeedbacksByCompany("Mustermann GmbH", "mmustermann-djkf");
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Private helper method to generate a valid feedback.
     * @return a <code>Feedback</code> object containing valid test data.
     */
    private Feedback generateValidFeedback( ) {
        return Feedback.builder()
                .id(new ObjectId("615824bd6d0c882034e85964"))
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
