package de.davelee.trams.server.model;

import lombok.*;
import org.bson.types.ObjectId;

/**
 * Class to represent customers in TraMS Server.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    /**
     * A unique id for this customer.
     */
    private ObjectId id;

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
