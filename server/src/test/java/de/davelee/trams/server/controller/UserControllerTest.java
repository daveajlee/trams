package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.User;
import de.davelee.trams.server.model.UserAccountStatus;
import de.davelee.trams.server.request.*;
import de.davelee.trams.server.response.LoginResponse;
import de.davelee.trams.server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Test cases for the User endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    /**
     * Test case: add a user to the system based on a valid user request.
     * Expected Result: user added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in user service.
        Mockito.when(userService.save(any())).thenReturn(true);
        //Add user so that test is successfully.
        RegisterUserRequest validUserRequest = generateValidUserRequest();
        assertEquals("Max", validUserRequest.getFirstName());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: attempt to add a user to the system with no first name.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingFirstName() {
        RegisterUserRequest validUserRequest = new RegisterUserRequest();
        validUserRequest.setSurname("Lee");
        validUserRequest.setUsername("dlee");
        validUserRequest.setCompany("MyCompany");
        assertNull(validUserRequest.getFirstName());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no last name.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingSurname() {
        RegisterUserRequest validUserRequest = new RegisterUserRequest();
        validUserRequest.setFirstName("David");
        validUserRequest.setUsername("dlee");
        validUserRequest.setCompany("MyCompany");
        assertNull(validUserRequest.getSurname());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no username.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingUsername() {
        RegisterUserRequest validUserRequest = new RegisterUserRequest();
        validUserRequest.setFirstName("David");
        validUserRequest.setSurname("Lee");
        validUserRequest.setCompany("MyCompany");
        assertNull(validUserRequest.getUsername());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no company.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingCompany() {
        RegisterUserRequest validUserRequest = new RegisterUserRequest();
        validUserRequest.setFirstName("David");
        validUserRequest.setSurname("Lee");
        validUserRequest.setUsername("dlee");
        assertNull(validUserRequest.getCompany());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to delete a user which exist.
     * Expected Result: no content.
     */
    @Test
    public void testValidDeleteUser() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken(anyString())).thenReturn(true);
        Mockito.when(userService.findByCompanyAndUserName("MyCompany", "mlee")).thenReturn(generateValidUser());
        //Perform tests
        ResponseEntity<Void> responseEntity = userController.deleteUser("MyCompany", "mlee", "dlee-fgtgogg");
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
    }

    /**
     * Test case: attempt to delete a user which does not exist.
     * Expected Result: no content.
     */
    @Test
    public void testValidDeleteUserNotFound() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken(anyString())).thenReturn(true);
        //Perform tests
        ResponseEntity<Void> responseEntity = userController.deleteUser("MyCompany", "mlee", "dlee-fgtgogg");
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: attempt to delete a user without specifying a username.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidDeleteUser() {
        ResponseEntity<Void> responseEntity = userController.deleteUser(null, null, null);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: change password for a user who exists and then one who does not exist.
     * Expected Result: forbidden or no content or ok depending on request.
     */
    @Test
    public void testChangePasswordForUser() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkf")).thenReturn(false);
        Mockito.when(userService.changePassword("Example Company", "max.mustermann", "test123", "123test")).thenReturn(true);
        Mockito.when(userService.changePassword("Example Company", "max.a.mustermann", "test123", "123test")).thenReturn(false);
        //Perform tests - valid request
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCompany("Example Company");
        changePasswordRequest.setUsername("max.mustermann");
        changePasswordRequest.setCurrentPassword("test123");
        changePasswordRequest.setNewPassword("123test");
        changePasswordRequest.setToken("max.mustermann-ghgkg");
        ResponseEntity<Void> responseEntity = userController.changePassword(changePasswordRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
        //Perform tests - invalid token
        ChangePasswordRequest changePasswordRequest2 = new ChangePasswordRequest();
        changePasswordRequest2.setCompany("Example Company");
        changePasswordRequest2.setUsername("max.mustermann");
        changePasswordRequest2.setCurrentPassword("test123");
        changePasswordRequest2.setNewPassword("123test");
        changePasswordRequest2.setToken("max.mustermann-ghgkf");
        ResponseEntity<Void> responseEntity2 = userController.changePassword(changePasswordRequest2);
        assertTrue(responseEntity2.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
        //Perform tests - no user
        changePasswordRequest.setUsername("max.a.mustermann");
        ResponseEntity<Void> responseEntity3 = userController.changePassword(changePasswordRequest);
        assertTrue(responseEntity3.getStatusCode().value() == HttpStatus.NOT_FOUND.value());
    }

    /**
     * Test case: deactivate for a user who exists and then one who does not exist.
     * Expected Result: forbidden or no content or ok depending on request.
     */
    @Test
    public void testDeactivateForUser() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkf")).thenReturn(false);
        Mockito.when(userService.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(generateValidUser());
        //Perform tests - valid request
        ResponseEntity<Void> responseEntity = userController.deactivateUser(new DeactivateUserRequest("Example Company", "max.mustermann", "max.mustermann-ghgkg"));
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
        //Perform tests - invalid token
        ResponseEntity<Void> responseEntity2 = userController.deactivateUser(new DeactivateUserRequest("Example Company", "max.mustermann", "max.mustermann-ghgkf"));
        assertTrue(responseEntity2.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
        //Perform tests - no user
        ResponseEntity<Void> responseEntity3 = userController.deactivateUser(new DeactivateUserRequest("Example Company", "max.a.mustermann", "max.mustermann-ghgkg"));
        assertTrue(responseEntity3.getStatusCode().value() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: login with a valid user/password and invalidation combinations.
     * Expected Result: forbidden or ok depending on request.
     */
    @Test
    public void testLogin() {
        //Mock the important methods in user service.
        Mockito.when(userService.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(generateValidUser());
        //Test with valid login
        LoginRequest validLoginRequest = new LoginRequest("Example Company", "max.mustermann", "test");
        ResponseEntity<LoginResponse> responseEntity = userController.login(validLoginRequest);
        assertTrue( responseEntity.getStatusCode().value() == HttpStatus.OK.value());
        //Test with incorrect password
        LoginRequest invalidLoginRequest = new LoginRequest("Example Company", "max.mustermann", "123test");
        ResponseEntity<LoginResponse> responseEntity2 = userController.login(invalidLoginRequest);
        assertTrue( responseEntity2.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
        //Test with invalid username
        LoginRequest invalidLoginRequest2 = new LoginRequest("Example Company", "max.a.mustermann", "123test");
        ResponseEntity<LoginResponse> responseEntity3 = userController.login(invalidLoginRequest2);
        assertTrue( responseEntity3.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Test case: logout.
     * Expected Result: ok.
     */
    @Test
    public void testLogout() {
        //Do actual test
        ResponseEntity<Void> responseEntity = userController.logout(new LogoutRequest("max.mustermann-ghgkg"));
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
    }


    /**
     * Test case: reset user which exists or does not exist.
     * Expected Result: not found or ok depending on request.
     */
    @Test
    public void testReset() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.resetUserPassword("Example Company", "max.mustermann", "test")).thenReturn(true);
        //Test with valid user
        ResetUserRequest resetUserRequest = new ResetUserRequest();
        resetUserRequest.setCompany("Example Company");
        resetUserRequest.setUsername("max.mustermann");
        resetUserRequest.setPassword("test");
        resetUserRequest.setToken("max.mustermann-ghgkg");
        ResponseEntity<Void> responseEntity = userController.resetUser(resetUserRequest);
        assertTrue( responseEntity.getStatusCode().value() == HttpStatus.OK.value());
        //Test with invalid username
        ResetUserRequest resetUserRequest2 = new ResetUserRequest();
        resetUserRequest.setCompany("Example Company");
        resetUserRequest.setUsername("max.a.mustermann");
        resetUserRequest.setPassword("test");
        resetUserRequest.setToken("max.mustermann-ghgkg");
        ResponseEntity<Void> responseEntity2 = userController.resetUser(resetUserRequest2);
        System.out.println(responseEntity2.getStatusCode().value());
        assertTrue( responseEntity2.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
        //Test with invalid token
        ResetUserRequest resetUserRequest3 = new ResetUserRequest();
        resetUserRequest.setCompany("Example Company");
        resetUserRequest.setUsername("max.mustermann");
        resetUserRequest.setPassword("test");
        resetUserRequest.setToken("max.mustermann-ghgkf");
        ResponseEntity<Void> responseEntity3 = userController.resetUser(resetUserRequest3);
        assertTrue( responseEntity3.getStatusCode().value() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Private helper method to generate a valid register user request.
     * @return a <code>RegisterUserRequest</code> object containing valid test data.
     */
    private RegisterUserRequest generateValidUserRequest( ) {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setCompany("Example Company");
        registerUserRequest.setFirstName("Max");
        registerUserRequest.setSurname("Mustermann");
        registerUserRequest.setUsername("max.mustermann");
        registerUserRequest.setPassword("test");
        return registerUserRequest;
    }

    /**
     * Private helper method to generate a valid user.
     * @return a <code>User</code> object containing valid test data.
     */
    private User generateValidUser( ) {
        User user = new User();
        user.setCompany("Example Company");
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setUserName("max.mustermann");
        user.setPassword("test");
        user.setRole("Employee");
        user.setAccountStatus(UserAccountStatus.ACTIVE);
        return user;
    }

}
