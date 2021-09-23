package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
