package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.User;
import de.davelee.trams.crm.model.UserAccountStatus;
import de.davelee.trams.crm.request.*;
import de.davelee.trams.crm.response.LoginResponse;
import de.davelee.trams.crm.services.UserService;
import de.davelee.trams.crm.utils.UserUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class defines the endpoints for the REST API which manipulate users and delegates the actions to the UserService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/user")
@RequestMapping(value="/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Add a user to the system.
     * @param registerUserRequest a <code>RegisterUserRequest</code> object representing the user to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a user", description="Add a user to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created user")})
    public ResponseEntity<Void> addUser (@RequestBody final RegisterUserRequest registerUserRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(registerUserRequest.getFirstName()) || StringUtils.isBlank(registerUserRequest.getSurname())
                || StringUtils.isBlank(registerUserRequest.getCompany()) || StringUtils.isBlank(registerUserRequest.getUsername())
                || StringUtils.isBlank(registerUserRequest.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        //Now convert to user object.
        User user = UserUtils.convertRegisterUserRequestToUser(registerUserRequest);
        //Return 201 if saved successfully.
        return userService.save(user) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Delete a specific user from the database based on their username and company.
     * @param company a <code>String</code> containing the name of the company.
     * @param username a <code>String</code> containing the username.
     * @param token a <code>String</code> containing the token to verify that the user is logged in.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Delete a user", description="Delete a user from the system.")
    @DeleteMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully delete user"), @ApiResponse(responseCode="204",description="Successful but no user found")})
    public ResponseEntity<Void> deleteUser (@RequestParam("company") final String company, @RequestParam("username") final String username,
                                            @RequestParam("token") final String token) {
        //Check valid request including authentication
        HttpStatus status = validateAndAuthenticateRequest(company, username, token);
        //If the status is not null then produce response and return.
        if ( status != null ) {
            return new ResponseEntity<>(status);
        }
        //Now retrieve the user based on the username.
        User user = userService.findByCompanyAndUserName(company, username);
        //If user is null then return 204.
        if ( user == null ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Now delete the user based on the username.
        userService.delete(user);
        //Return 200.
        return ResponseEntity.status(200).build();
    }

    /**
     * Deactivate a specific user from the database based on their username and company.
     * @param deactivateUserRequest a <code>DeactivateUserRequest</code> object which contains the information on leaving.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Deactivate a user", description="Deactivate a user from the system.")
    @PatchMapping(value="/deactivate")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deactivated user"), @ApiResponse(responseCode="204",description="Successful but no user found")})
    public ResponseEntity<Void> deactivateUser (@RequestBody final DeactivateUserRequest deactivateUserRequest) {
        //Check valid request including authentication
        HttpStatus status = validateAndAuthenticateRequest(deactivateUserRequest.getCompany(), deactivateUserRequest.getUsername(), deactivateUserRequest.getToken());
        //If the status is not null then produce response and return.
        if ( status != null ) {
            return new ResponseEntity<>(status);
        }
        //Now retrieve the user based on the username.
        User user = userService.findByCompanyAndUserName(deactivateUserRequest.getCompany(), deactivateUserRequest.getUsername());
        //If user is null then return 204.
        if ( user == null ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Now deactivate the user based on the username and return the result.
        return ResponseEntity.ok()
                .build();
    }

    /**
     * Change the password of the supplied user. Return 200 if the password was changed successfully or 404 if the user was not found
     * or the password supplied did not match the current password of the user.
     * @param changePasswordRequest a <code>ChangePasswordRequest</code> object containing the company, username, old password and new password.
     * @return a <code>ResponseEntity</code> object with status 200 if password changed or 404 if user not found.
     */
    @Operation(summary="changePassword", description="Change password for a user")
    @PatchMapping(value="/password")
    @ApiResponses(@ApiResponse(responseCode="200",description="Successfully processed change password request"))
    public ResponseEntity<Void> changePassword (@RequestBody final ChangePasswordRequest changePasswordRequest) {
        //Check valid request including authentication
        HttpStatus status = validateAndAuthenticateRequest(changePasswordRequest.getCompany(), changePasswordRequest.getUsername(), changePasswordRequest.getToken());
        //If the status is not null then produce response and return.
        if ( status != null ) {
            return new ResponseEntity<>(status);
        }
        boolean result = userService.changePassword(changePasswordRequest.getCompany(), changePasswordRequest.getUsername(),
                changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());
        //If result is true, then return 200 otherwise return 404 to indicate user not found.
        return result ? ResponseEntity.status(200).build() : ResponseEntity.status(404).build();
    }

    /**
     * Take a LoginRequest and attempt to login in the user. If successful, return a token which can be used for this session.
     * @param loginRequest a <code>LoginRequest</code> containing the company, username and password information for this request.
     * @return a <code>ResponseEntity</code> with response status 200 indicating that it was successful.
     */
    @Operation(summary="Login", description="Login to the system")
    @PostMapping(value="/login")
    @ApiResponses(@ApiResponse(responseCode="200",description="Successfully processed login request"))
    public ResponseEntity<LoginResponse> login (@RequestBody final LoginRequest loginRequest) {
        User user = userService.findByCompanyAndUserName(loginRequest.getCompany(), loginRequest.getUsername());
        if ( user != null && user.getAccountStatus()== UserAccountStatus.ACTIVE && user.getPassword().contentEquals(loginRequest.getPassword()) ) {
            return ResponseEntity.ok().body(LoginResponse.builder().token(userService.generateAuthToken(loginRequest.getUsername())).build());
        } else if ( user != null ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(LoginResponse.builder().errorMessage("Password was incorrect!").build());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(LoginResponse.builder().errorMessage("User was not found").build());
    }

    /**
     * Take a token and attempt to log the user out. For security reasons, a 200 is returned even if logout was not successful.
     * @param logoutRequest a <code>LogoutRequest</code> containing the token to remove.
     * @return a <code>LoginResponse</code> object which contains a token and response code 200 if login was successful or an error message and response code 403 if login was not successful.
     */
    @Operation(summary="Logout", description="Logout from the system")
    @PostMapping(value="/logout")
    @ApiResponses(@ApiResponse(responseCode="200",description="Successfully processed logout request"))
    public ResponseEntity<Void> logout (@RequestBody final LogoutRequest logoutRequest) {
        //Remove the token from the authenticated tokens.
        userService.removeAuthToken(logoutRequest.getToken());
        //Return 200.
        return ResponseEntity.status(200).build();
    }

    /**
     * Reset the password of the supplied user. Return 200 if the password was changed successfully or 404 if the user was not found.
     * @param resetUserRequest a <code>ResetUserRequest</code> object containing the company, username and new password.
     * @return a <code>ResponseEntity</code> object with status 200 if password changed or 404 if user not found.
     */
    @Operation(summary="resetUser", description="Reset password for a user")
    @PatchMapping(value="/reset")
    @ApiResponses(@ApiResponse(responseCode="200",description="Successfully processed reset user request"))
    public ResponseEntity<Void> resetUser (@RequestBody final ResetUserRequest resetUserRequest) {
        //Verify that user is logged in.
        if ( resetUserRequest.getToken() == null || !userService.checkAuthToken(resetUserRequest.getToken()) ) {
            return ResponseEntity.status(403).build();
        }
        boolean result = userService.resetUserPassword(resetUserRequest.getCompany(), resetUserRequest.getUsername(), resetUserRequest.getPassword());
        //If result is true, then return 200 otherwise return 404 to indicate user not found.
        return result ? ResponseEntity.status(200).build() : ResponseEntity.status(404).build();
    }

    /**
     * Private helper method to verify that at least username, company and token are all supplied and valid.
     * @param company a <code>String</code> containing the name of the company.
     * @param username a <code>String</code> containing the username.
     * @param token a <code>String</code> containing the token to verify that the user is logged in.
     * @return a <code>HttpStatus</code> which is either filled if it was not authenticated or null if authenticated and valid.
     */
    private HttpStatus validateAndAuthenticateRequest ( final String company, final String username, final String token ) {
        //First of all, check if the username field is empty or null, then return bad request.
        if (StringUtils.isBlank(username) || StringUtils.isBlank(company)) {
            return HttpStatus.BAD_REQUEST;
        }
        //Verify that user is logged in.
        if ( token == null || !userService.checkAuthToken(token) ) {
            return HttpStatus.FORBIDDEN;
        }
        //If everything was ok then return null.
        return null;
    }

}
