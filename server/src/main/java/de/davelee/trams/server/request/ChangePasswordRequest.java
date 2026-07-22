package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to change the password of a particular user.
 * @author Dave Lee
 */
public class ChangePasswordRequest {

    /**
     * Company that the user works for
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

    /**
     * Current password for this user
     */
    private String currentPassword;

    /**
     * New password to set for this user.
     */
    private String newPassword;

    public ChangePasswordRequest() {
    }

    public ChangePasswordRequest(String company, String username, String token, String currentPassword, String newPassword) {
        this.company = company;
        this.username = username;
        this.token = token;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
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

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
