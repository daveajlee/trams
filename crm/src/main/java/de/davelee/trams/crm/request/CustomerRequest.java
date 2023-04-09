package de.davelee.trams.crm.request;

import lombok.*;

/**
 * This class is part of the TraMS CRM REST API. It represents a request to add the following customer to the server
 * containing title, first name, last name, email address, telephone number, company and address.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRequest {

    /**
     * Title for this customer e.g. Mr, Mrs, Dr.
     */
    private String title;

    /**
     * First name of the customer e.g. Max
     */
    private String firstName;

    /**
     * The surname of this customer e.g. Mustermann.
     */
    private String lastName;

    /**
     * The email address of this customer e.g. max@mustermann.de
     */
    private String emailAddress;

    /**
     * The telephone number of this customer e.g. 01234 567890
     */
    private String telephoneNumber;

    /**
     * The postal address of this customer e.g. 1 Max Way, 12345 Musterdorf
     */
    private String address;

    /**
     * The company that this customer has registered with.
     */
    private String company;

}
