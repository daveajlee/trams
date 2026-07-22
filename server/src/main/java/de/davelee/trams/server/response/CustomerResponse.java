package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing
 * a single customer returned from the server containing title, first name,
 * last name, email address, telephone number, company and address.
 * @author Dave Lee
 */
public class CustomerResponse {

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

    public CustomerResponse() {
    }

    public CustomerResponse(String title, String firstName, String lastName, String emailAddress, String telephoneNumber, String address, String company) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.company = company;
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

    @Override
    public String toString() {
        return "CustomerResponse{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
