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

}
