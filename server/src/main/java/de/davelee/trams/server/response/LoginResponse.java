package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents the response to a login request with either a token
 * if login was successful or error message if login was not successful.
 * @author Dave Lee
 */
public class LoginResponse {

    /**
     * The error message to show the user if the login was not successful which can be null if login was successful.
     */
    private String errorMessage;

    /**
     * The authentication token which can be null if the login was not successful.
     */
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(String errorMessage, String token) {
        this.errorMessage = errorMessage;
        this.token = token;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
