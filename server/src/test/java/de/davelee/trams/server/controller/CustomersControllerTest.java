package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.response.CustomersResponse;
import de.davelee.trams.server.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the customers endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
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
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
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
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: attempt to find customers without specifying a company.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidFindCustomers() {
        //Perform tests
        ResponseEntity<CustomersResponse> responseEntity = customersController.getCustomers(null);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
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
