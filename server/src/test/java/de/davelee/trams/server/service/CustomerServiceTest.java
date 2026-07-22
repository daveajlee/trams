package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the CustomerService class - the CustomerRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    /**
     * Test case: save a new customer.
     * Expected Result: true.
     */
    @Test
    public void testSaveCustomer() {
        //Test data
        Customer customer = generateValidCustomer();
        //Mock important method in repository.
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        //do actual test.
        assertTrue(customerService.save(customer));
    }

    /**
     * Test case: find a customer by company and email address.
     * Expected Result: customer is not null.
     */
    @Test
    public void testFindCustomerByCompanyAndEmailAddress() {
        //Test data
        Customer customer = generateValidCustomer();
        //Mock important method in repository.
        Mockito.when(customerRepository.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de")).thenReturn(customer);
        //do actual test.
        assertNotNull(customerService.findByCompanyAndEmailAddress("Mustermann GmbH", "max@mustermann.de"));
    }

    /**
     * Test case: delete a customer.
     * Currently void so no validation possible.
     */
    @Test
    public void testDeleteCustomer() {
        //Test data
        Customer customer = generateValidCustomer();
        //Mock important method in repository.
        Mockito.doNothing().when(customerRepository).delete(customer);
        //do actual test.
        customerService.delete(customer);
    }

    /**
     * Test case: find all customers of a company.
     * Expected Result: list has size of 1.
     */
    @Test
    public void testFindCustomersByCompany() {
        //Test data
        Customer customer = generateValidCustomer();
        //Mock important method in repository.
        Mockito.when(customerRepository.findByCompany("Mustermann GmbH")).thenReturn(List.of(customer));
        //do actual test.
        assertEquals(customerService.findByCompany("Mustermann GmbH").size(), 1);
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
