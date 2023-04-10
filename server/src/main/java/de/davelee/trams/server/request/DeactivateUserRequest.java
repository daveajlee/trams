package de.davelee.trams.server.request;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a request to deactivate a particular user.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
