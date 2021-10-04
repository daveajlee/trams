package de.davelee.trams.crm.request;

import lombok.*;

/**
 * This class is part of the TraMS CRM REST API. It represents a request to change the password of a particular user.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
