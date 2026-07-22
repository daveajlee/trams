package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to deactivate a particular user.
 * @author Dave Lee
 */
public class DeactivateUserRequest {

    /**
     * Company that the user is associated with.
     */
    private String company;

    /**
     * Username who's password should be changed
     */
    private String username;

    /**
     * The token of the user to verify that they are logged in
     */
    private String token;

    public DeactivateUserRequest() {
    }

    public DeactivateUserRequest(String company, String username, String token) {
        this.company = company;
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
