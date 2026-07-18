package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request for a logout with a token to invalidate.
 * @author Dave Lee
 */
public class LogoutRequest {

    /**
     * The token to invalidate
     */
    private String token;

    public LogoutRequest() {
    }

    public LogoutRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
