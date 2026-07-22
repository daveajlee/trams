package de.davelee.trams.server.model;

import org.bson.types.ObjectId;

/**
 * Class to represent customers in TraMS Server.
 * @author Dave Lee
 */
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

    public Customer() {
    }

    public Customer(ObjectId id, String title, String firstName, String lastName, String emailAddress, String telephoneNumber, String address, String company) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.company = company;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
