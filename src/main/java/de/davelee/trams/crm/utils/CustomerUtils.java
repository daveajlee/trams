package de.davelee.trams.crm.utils;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.request.CustomerRequest;
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
        return Customer.builder()
                .id(new ObjectId())
                .title(customerRequest.getTitle())
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .emailAddress(customerRequest.getEmailAddress())
                .telephoneNumber(customerRequest.getTelephoneNumber())
                .address(customerRequest.getAddress())
                .build();
    }

}
