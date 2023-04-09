package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.User;
import de.davelee.trams.crm.model.UserAccountStatus;
import de.davelee.trams.crm.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Class to provide service operations for users in the TraMS CRM program.
 * @author Dave Lee
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${token.length}")
    private int tokenLength;

    @Value("${logout.minutes}")
    private int timeoutInMinutes;

    private final HashMap<String, LocalDateTime> loggedInTokens = new HashMap<>();

    /**
     * Save the specified user object in the database.
     * @param user a <code>User</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the user has been saved successfully.
     */
    public boolean save ( final User user ) {
        return userRepository.save(user) != null;
    }

    /**
     * Find a user according to their user name and company.
     * @param company a <code>String</code> with the company to retrieve user for.
     * @param userName a <code>String</code> with the user name.
     * @return a <code>User</code> representing the user which has this user name. Returns null if no matching user. User name is a unique field so no chance of more than one result!
     */
    public User findByCompanyAndUserName ( final String company, final String userName ) {
        return userRepository.findByCompanyAndUserName(company, userName);
    }

    /**
     * Delete the specified user object from the database.
     * @param user a <code>User</code> object to delete from the database.
     */
    public void delete ( final User user ) {
        userRepository.delete(user);
    }

    /**
     * Deactivate the specified user object from the database.
     * @param user a <code>User</code> object to delete from the database.
     */
    public void deactivate (final User user ) {
        //Deactivate user account.
        user.setAccountStatus(UserAccountStatus.DEACTIVATED);
    }

    /**
     * Generate a token for this user and add it to the list of logged in tokens. The token should expire after the specified amount of minutes in the config.
     * Return the token so that the client can also have access to it.
     * @param userName a <code>String</code> with the user name of the logged in user.
     * @return a <code>String</code> containing the token which is valid for a limited amount of time.
     */
    public String generateAuthToken ( final String userName ) {
        String token = userName + "-" + RandomStringUtils.randomAlphanumeric(tokenLength);
        loggedInTokens.put(token, LocalDateTime.now().plusMinutes(timeoutInMinutes));
        return token;
    }

    /**
     * Check if this is a valid token which is defined as a token that exists in the token storage and has not yet expired.
     * @param token a <code>String</code> containing the token to check.
     * @return a <code>boolean</code> which is true iff the token is valid.
     */
    public boolean checkAuthToken ( final String token ) {
        return loggedInTokens.containsKey(token) && (loggedInTokens.get(token).isEqual(LocalDateTime.now()) || loggedInTokens.get(token).isAfter(LocalDateTime.now()));
    }

    /**
     * Remove the supplied token from the list of logged in tokens.
     * @param token a <code>String</code> containing the token to remove from the list of logged in tokens.
     */
    public void removeAuthToken ( final String token ) {
        loggedInTokens.remove(token);
    }

    /**
     * Reset the password for a particular user.
     * @param company a <code>String</code> containing the company that the user is associated with.
     * @param username a <code>String</code> containing the username of the user who's password should be reset.
     * @param newPassword a <code>String</code> containing the new password that the user should receive.
     * @return a <code>boolean</code> which is true iff the password could be reset successfully.
     */
    public boolean resetUserPassword ( final String company, final String username, final String newPassword ) {
        User user = userRepository.findByCompanyAndUserName(company, username);
        if ( user != null ) {
            user.setPassword(newPassword);
            return userRepository.save(user) != null;
        }
        return false;
    }

    /**
     * Change the password for a particular user if the old password matches the current password in database.
     * @param company a <code>String</code> containing the company that the user is associated with.
     * @param username a <code>String</code> containing the username of the user who's password should be reset.
     * @param oldPassword a <code>String</code> containing the old password that the user had.
     * @param newPassword a <code>String</code> containing the new password that the user should receive.
     * @return a <code>boolean</code> which is true iff the password could be changed successfully.
     */
    public boolean changePassword ( final String company, final String username, final String oldPassword, final String newPassword ) {
        User user = userRepository.findByCompanyAndUserName(company, username);
        if ( user != null ) {
            if ( user.getPassword().contentEquals(oldPassword) ) {
                user.setPassword(newPassword);
                return userRepository.save(user) != null;
            }
        }
        return false;
    }

}
