package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request for a login with username and password.
 * @author Dave Lee
 */
public class LoginRequest {

    /**
     * The company that the user is using to login.
     */
    private String company;

    /**
     * The username who wants to login
     */
    private String username;

    /**
     * The password used for login
     */
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String company, String username, String password) {
        this.company = company;
        this.username = username;
        this.password = password;
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
}
