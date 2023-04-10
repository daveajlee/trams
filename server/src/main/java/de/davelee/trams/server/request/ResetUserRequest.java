package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to reset the password of a particular user.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
