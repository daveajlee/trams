package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class to provide service operations for customers in TraMS CRM.
 * @author Dave Lee
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Save the specified customer object in the database.
     * @param customer a <code>Customer</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the customer has been saved successfully.
     */
    public boolean save ( final Customer customer ) {
        return customerRepository.save(customer) != null;
    }

    /**
     * Find a customer according to their email address and company.
     * @param company a <code>String</code> with the company to retrieve customer for.
     * @param emailAddress a <code>String</code> with the email address of the company.
     * @return a <code>Customer</code> representing the customer which has this email address. Returns null if no matching customer.
     */
    public Customer findByCompanyAndEmailAddress ( final String company, final String emailAddress ) {
        return customerRepository.findByCompanyAndEmailAddress(company, emailAddress);
    }

    /**
     * Delete the specified customer object from the database.
     * @param customer a <code>Customer</code> object to delete from the database.
     */
    public void delete ( final Customer customer ) {
        customerRepository.delete(customer);
    }

    /**
     * Find all customers belonging to a company.
     * @param company a <code>String</code> with the company to retrieve customers for.
     * @return a <code>List</code> of <code>Customer</code> objects representing the Customers belonging to this company. Returns null if no matching customers.
     */
    public List<Customer> findByCompany (final String company ) {
        return customerRepository.findByCompany(company);
    }

}
