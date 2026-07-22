package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.Customer;
import de.davelee.trams.server.request.CustomerRequest;
import de.davelee.trams.server.response.CustomerResponse;
import org.bson.types.ObjectId;

/**
 * This class provides utility methods for processing related to /customer endpoints in the CustomerController.
 * @author Dave Lee
 */
public class CustomerUtils {

    /**
     * This method converts a CustomerRequest object into a Customer object which can be saved in the database.
     * @param customerRequest a <code>CustomerRequest</code> object to convert
     * @return a <code>Customer</code> object.
     */
    public static Customer convertCustomerRequestToCustomer (final CustomerRequest customerRequest ) {
        return new Customer(new ObjectId(), customerRequest.getTitle(), customerRequest.getFirstName(),
                customerRequest.getLastName(), customerRequest.getEmailAddress(), customerRequest.getTelephoneNumber(),
                customerRequest.getAddress(), customerRequest.getCompany());
    }

    /**
     * This method converts a Customer object into a CustomerResponse object which can be saved in the database.
     * @param customer a <code>Customer</code> object to convert
     * @return a <code>CustomerResponse</code> object.
     */
    public static CustomerResponse convertCustomerToCustomerResponse (final Customer customer ) {
        return new CustomerResponse(customer.getTitle(), customer.getFirstName(),
                customer.getLastName(), customer.getEmailAddress(), customer.getTelephoneNumber(),
                customer.getAddress(), customer.getCompany());
    }

}
