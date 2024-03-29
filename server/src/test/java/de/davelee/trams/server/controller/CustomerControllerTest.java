package de.davelee.trams.server.controller;

import de.davelee.trams.server.request.CustomerRequest;
import de.davelee.trams.server.response.CustomerResponse;
import de.davelee.trams.server.service.CustomerService;
import de.davelee.trams.server.utils.CustomerUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the Customer endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    /**
     * Test case: add a customer to the system based on a valid customer request.
     * Expected Result: customer added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in customer service.
        Mockito.when(customerService.save(any())).thenReturn(true);
        //Add customer so that test is successfully.
        CustomerRequest validCustomerRequest = generateValidCustomer();
        assertEquals("Max", validCustomerRequest.getFirstName());
        ResponseEntity<Void> responseEntity = customerController.addCustomer(validCustomerRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: add a customer to the system based on an invalid customer request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Mock important methods in customer service.
        Mockito.when(customerService.save(any())).thenReturn(true);
        //Add customer so that test is successfully.
        CustomerRequest validCustomerRequest = generateValidCustomer();
        //Make change so that request is invalid.
        validCustomerRequest.setFirstName("");
        assertEquals("", validCustomerRequest.getFirstName());
        ResponseEntity<Void> responseEntity = customerController.addCustomer(validCustomerRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: customer exists with the specified company and email address.
     * Expected Result: ok.
     */
    @Test
    public void testValidFindCustomerFound() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de"))
                .thenReturn(CustomerUtils.convertCustomerRequestToCustomer(generateValidCustomer()));
        //Perform tests
        ResponseEntity<CustomerResponse> responseEntity = customerController.getCustomer("Mustermann GmbH", "max@mustermann.de");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: no customer exists with the specified company and email address.
     * Expected Result: no content.
     */
    @Test
    public void testValidFindCustomerNotFound() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "bob@mustermann.de")).thenReturn(null);
        //Perform tests
        ResponseEntity<CustomerResponse> responseEntity = customerController.getCustomer("Mustermann GmbH", "bob@mustermann.de");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
        //test with empty company.
        ResponseEntity<CustomerResponse> responseEntity2 = customerController.getCustomer("", "bob@mustermann.de");
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to delete a customer which exists.
     * Expected Result: ok.
     */
    @Test
    public void testValidDeleteCustomer() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de"))
                .thenReturn(CustomerUtils.convertCustomerRequestToCustomer(generateValidCustomer()));
        //Perform tests
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer("Mustermann GmbH", "max@mustermann.de");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: attempt to delete a customer which does not exist.
     * Expected Result: no content.
     */
    @Test
    public void testValidDeleteCustomerNotFound() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "bob@mustermann.de"))
                .thenReturn(null);
        //Perform tests
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer("Mustermann GmbH", "bob@mustermann.de");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: attempt to delete a customer without specifying an email address.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidDeleteCustomer() {
        ResponseEntity<Void> responseEntity = customerController.deleteCustomer(null, null);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Private helper method to generate a valid customer.
     * @return a <code>CustomerRequest</code> object containing valid test data.
     */
    private CustomerRequest generateValidCustomer( ) {
        return CustomerRequest.builder()
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
