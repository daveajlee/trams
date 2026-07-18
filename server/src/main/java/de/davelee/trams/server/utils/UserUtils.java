package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.User;
import de.davelee.trams.server.model.UserAccountStatus;
import de.davelee.trams.server.request.RegisterUserRequest;
import org.bson.types.ObjectId;

/**
 * This class provides utility methods for processing related to /user endpoints in the PersonalManRestController.
 * @author Dave Lee
 */
public class UserUtils {

    /**
     * This method converts a RegisterUserRequest object into a User object which can be saved in the database.
     * @param registerUserRequest a <code>RegisterUserRequest</code> object to convert
     * @return a <code>User</code> object.
     */
    public static User convertRegisterUserRequestToUser (final RegisterUserRequest registerUserRequest ) {
        User user = new User();
        user.setId(new ObjectId());
        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getSurname());
        user.setUserName(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setCompany(registerUserRequest.getCompany());
        user.setRole(registerUserRequest.getRole());
        user.setAccountStatus(UserAccountStatus.ACTIVE);
        return user;
    }

}
