package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.User;
import de.davelee.trams.crm.model.UserAccountStatus;
import de.davelee.trams.crm.request.*;
import de.davelee.trams.crm.response.LoginResponse;
import de.davelee.trams.crm.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
 * Test cases for the User endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
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
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: attempt to add a user to the system with no first name.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingFirstName() {
        RegisterUserRequest validUserRequest = RegisterUserRequest.builder()
                .surname("Lee")
                .username("dlee")
                .company("MyCompany")
                .build();
        assertNull(validUserRequest.getFirstName());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no last name.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingSurname() {
        RegisterUserRequest validUserRequest = RegisterUserRequest.builder()
                .firstName("David")
                .username("dlee")
                .company("MyCompany")
                .build();
        assertNull(validUserRequest.getSurname());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no username.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingUsername() {
        RegisterUserRequest validUserRequest = RegisterUserRequest.builder()
                .firstName("David")
                .surname("Lee")
                .company("MyCompany")
                .build();
        assertNull(validUserRequest.getUsername());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Test case: attempt to add a user to the system with no company.
     * Expected Result: bad request.
     */
    @Test
    public void testUserMissingCompany() {
        RegisterUserRequest validUserRequest = RegisterUserRequest.builder()
                .firstName("David")
                .surname("Lee")
                .username("dlee")
                .build();
        assertNull(validUserRequest.getCompany());
        ResponseEntity<Void> responseEntity = userController.addUser(validUserRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
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
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
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
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
    }

    /**
     * Test case: attempt to delete a user without specifying a username.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidDeleteUser() {
        ResponseEntity<Void> responseEntity = userController.deleteUser(null, null, null);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
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
        ResponseEntity<Void> responseEntity = userController.changePassword(ChangePasswordRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .currentPassword("test123")
                .newPassword("123test")
                .token("max.mustermann-ghgkg")
                .build());
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        //Perform tests - invalid token
        ResponseEntity<Void> responseEntity2 = userController.changePassword(ChangePasswordRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .currentPassword("test123")
                .newPassword("123test")
                .token("max.mustermann-ghgkf")
                .build());
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
        //Perform tests - no user
        ResponseEntity<Void> responseEntity3 = userController.changePassword(ChangePasswordRequest.builder()
                .company("Example Company")
                .username("max.a.mustermann")
                .currentPassword("test123")
                .newPassword("123test")
                .token("max.mustermann-ghgkg")
                .build());
        assertTrue(responseEntity3.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
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
        Mockito.doNothing().when(userService).deactivate(any());
        Mockito.when(userService.changePassword("Example Company", "max.a.mustermann", "test123", "123test")).thenReturn(false);
        Mockito.when(userService.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(generateValidUser());
        //Perform tests - valid request
        ResponseEntity<Void> responseEntity = userController.deactivateUser(DeactivateUserRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .token("max.mustermann-ghgkg")
                .build());
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        //Perform tests - invalid token
        ResponseEntity<Void> responseEntity2 = userController.deactivateUser(DeactivateUserRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .token("max.mustermann-ghgkf")
                .build());
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
        //Perform tests - no user
        ResponseEntity<Void> responseEntity3 = userController.deactivateUser(DeactivateUserRequest.builder()
                .company("Example Company")
                .username("max.a.mustermann")
                .token("max.mustermann-ghgkg")
                .build());
        assertTrue(responseEntity3.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
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
        LoginRequest validLoginRequest = LoginRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .password("test")
                .build();
        ResponseEntity<LoginResponse> responseEntity = userController.login(validLoginRequest);
        assertTrue( responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        //Test with incorrect password
        LoginRequest invalidLoginRequest = LoginRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .password("123test")
                .build();
        ResponseEntity<LoginResponse> responseEntity2 = userController.login(invalidLoginRequest);
        assertTrue( responseEntity2.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
        //Test with invalid username
        LoginRequest invalidLoginRequest2 = LoginRequest.builder()
                .company("Example Company")
                .username("max.a.mustermann")
                .password("123test")
                .build();
        ResponseEntity<LoginResponse> responseEntity3 = userController.login(invalidLoginRequest2);
        assertTrue( responseEntity3.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Test case: logout.
     * Expected Result: ok.
     */
    @Test
    public void testLogout() {
        //Do actual test
        ResponseEntity<Void> responseEntity = userController.logout(LogoutRequest.builder()
                .token("max.mustermann-ghgkg")
                .build());
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
    }


    /**
     * Test case: reset user which exists or does not exist.
     * Expected Result: not found or ok depending on request.
     */
    @Test
    public void testReset() {
        //Mock the important methods in user service.
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkg")).thenReturn(true);
        Mockito.when(userService.checkAuthToken("max.mustermann-ghgkf")).thenReturn(false);
        Mockito.when(userService.resetUserPassword("Example Company", "max.mustermann", "test")).thenReturn(true);
        Mockito.when(userService.resetUserPassword("Example Company", "max.a.mustermann", "test")).thenReturn(false);
        //Test with valid user
        ResetUserRequest resetUserRequest = ResetUserRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .password("test")
                .token("max.mustermann-ghgkg")
                .build();
        ResponseEntity<Void> responseEntity = userController.resetUser(resetUserRequest);
        assertTrue( responseEntity.getStatusCodeValue() == HttpStatus.OK.value());
        //Test with invalid username
        ResetUserRequest resetUserRequest2 = ResetUserRequest.builder()
                .company("Example Company")
                .username("max.a.mustermann")
                .password("test")
                .token("max.mustermann-ghgkg")
                .build();
        ResponseEntity<Void> responseEntity2 = userController.resetUser(resetUserRequest2);
        assertTrue( responseEntity2.getStatusCodeValue() == HttpStatus.NOT_FOUND.value());
        //Test with invalid token
        ResetUserRequest resetUserRequest3 = ResetUserRequest.builder()
                .company("Example Company")
                .username("max.mustermann")
                .password("test")
                .token("max.mustermann-ghgkf")
                .build();
        ResponseEntity<Void> responseEntity3 = userController.resetUser(resetUserRequest3);
        assertTrue( responseEntity3.getStatusCodeValue() == HttpStatus.FORBIDDEN.value());
    }

    /**
     * Private helper method to generate a valid register user request.
     * @return a <code>RegisterUserRequest</code> object containing valid test data.
     */
    private RegisterUserRequest generateValidUserRequest( ) {
        return RegisterUserRequest.builder()
                .company("Example Company")
                .firstName("Max")
                .surname("Mustermann")
                .username("max.mustermann")
                .password("test")
                .role("Employee")
                .build();
    }

    /**
     * Private helper method to generate a valid user.
     * @return a <code>User</code> object containing valid test data.
     */
    private User generateValidUser( ) {
        return User.builder()
                .company("Example Company")
                .firstName("Max")
                .lastName("Mustermann")
                .userName("max.mustermann")
                .password("test")
                .role("Employee")
                .accountStatus(UserAccountStatus.ACTIVE)
                .build();
    }

}
