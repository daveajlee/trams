package de.davelee.trams.server.model;

import org.bson.types.ObjectId;

/**
 * Class to represent users who are allowed to view and answer feedbacks in TraMS Server.
 * @author Dave Lee
 */
public class User {

    /**
     * A unique id for this user.
     */
    private ObjectId id;

    /**
     * The first name of this user.
     */
    private String firstName;

    /**
     * The surname of this user.
     */
    private String lastName;

    /**
     * The username for this user.
     */
    private String userName;

    /**
     * The password for this user.
     */
    private String password;

    /**
     * The company that the user works for.
     */
    private String company;

    /**
     * The role that the user has in TraMS Server for this company.
     */
    private String role;

    /**
     * The status of this user's account.
     */
    private UserAccountStatus accountStatus;

    public User() {
    }

    public User(ObjectId id, String firstName, String lastName, String userName, String password, String company, String role, UserAccountStatus accountStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.company = company;
        this.role = role;
        this.accountStatus = accountStatus;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(UserAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
