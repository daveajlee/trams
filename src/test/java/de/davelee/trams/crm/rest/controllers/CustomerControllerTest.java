package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.request.CustomerRequest;
import de.davelee.trams.crm.services.CustomerService;
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
 * Test cases for the Customer endpoints in the TraMS CRM REST API.
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
                .build();
    }

}
