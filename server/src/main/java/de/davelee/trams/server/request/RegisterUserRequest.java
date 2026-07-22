package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request for a registration of a new user.
 * @author Dave Lee
 */
public class RegisterUserRequest {

    /**
     * First name of the person making registration request
     */
    private String firstName;

    /**
     * Surname of the person making registration request
     */
    private String surname;

    /**
     * Company which the person works for
     */
    private String company;

    /**
     * The username which the user wants to use
     */
    private String username;

    /**
     * The password which the user wants to use
     */
    private String password;

    /**
     * The role which the user would like
     */
    private String role;

    public RegisterUserRequest() {
    }

    public RegisterUserRequest(String firstName, String surname, String company, String username, String password, String role) {
        this.firstName = firstName;
        this.surname = surname;
        this.company = company;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
