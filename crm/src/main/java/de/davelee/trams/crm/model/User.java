package de.davelee.trams.crm.model;

import lombok.*;
import org.bson.types.ObjectId;

/**
 * Class to represent users who are allowed to view and answer feedbacks in TraMS CRM.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
     * The role that the user has in TraMS CRM for this company.
     */
    private String role;

    /**
     * The status of this user's account.
     */
    private UserAccountStatus accountStatus;

}
