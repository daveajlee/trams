package de.davelee.trams.crm.request;

import lombok.*;

/**
 * This class is part of the TraMS CRM REST API. It represents a request for a registration of a new user.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
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

}
