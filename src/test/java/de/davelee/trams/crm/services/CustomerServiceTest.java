package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the CustomerService class - the UserRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
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
    public void testSaveUser() {
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
