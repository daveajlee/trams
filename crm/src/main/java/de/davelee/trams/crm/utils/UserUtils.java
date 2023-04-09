package de.davelee.trams.crm.utils;

import de.davelee.trams.crm.model.User;
import de.davelee.trams.crm.model.UserAccountStatus;
import de.davelee.trams.crm.request.RegisterUserRequest;
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
        return User.builder()
                .id(new ObjectId())
                .firstName(registerUserRequest.getFirstName())
                .lastName(registerUserRequest.getSurname())
                .userName(registerUserRequest.getUsername())
                .password(registerUserRequest.getPassword())
                .company(registerUserRequest.getCompany())
                .role(registerUserRequest.getRole())
                .accountStatus(UserAccountStatus.ACTIVE)
                .build();
    }

}
