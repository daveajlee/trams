package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to reset the password of a particular user.
 * @author Dave Lee
 */
public class ResetUserRequest {

    /**
     * Company that the user is associated with
     */
    private String company;

    /**
     * Username who's password should be reset
     */
    private String username;

    /**
     * New password to set for this user
     */
    private String password;

    /**
     * The token of the user to verify that they are logged in
     */
    private String token;

    public ResetUserRequest() {
    }

    public ResetUserRequest(String company, String username, String password, String token) {
        this.company = company;
        this.username = username;
        this.password = password;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
