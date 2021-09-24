package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.response.CustomersResponse;
import de.davelee.trams.crm.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the customers endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class CustomersControllerTest {

    @InjectMocks
    private CustomersController customersController;

    @Mock
    private CustomerService customerService;

    /**
     * Test case: attempt to find customers for a company which has 1 customer.
     * Expected Result: ok.
     */
    @Test
    public void testValidFindCustomers() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompany("Mustermann GmbH")).thenReturn(List.of(generateValidCustomer()));
        //Perform tests
        ResponseEntity<CustomersResponse> responseEntity = customersController.getCustomers("Mustermann GmbH");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }

    /**
     * Test case: attempt to find customers for a company which has no customers.
     * Expected Result: no content.
     */
    @Test
    public void testValidFindCustomersNotFound() {
        //Mock the important methods in customer service.
        Mockito.when(customerService.findByCompany("Mustermann Gmb")).thenReturn(List.of());
        //Perform tests
        ResponseEntity<CustomersResponse> responseEntity = customersController.getCustomers("Mustermann Gmb");
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: attempt to find customers without specifying a company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidFindCustomers() {
        //Perform tests
        ResponseEntity<CustomersResponse> responseEntity = customersController.getCustomers(null);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
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