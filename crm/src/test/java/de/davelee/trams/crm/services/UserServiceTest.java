package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.User;
import de.davelee.trams.crm.repository.UserRepository;
import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the UserService class - the UserRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest(properties = { "logout.minutes=30","token.length=10"})
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    /**
     * Initialise the spring properties which otherwise with Mockito would not be set
     * @throws Exception if the fields cannot be set
     */
    @BeforeEach
    public void setSpringProperties() throws Exception {
        FieldUtils.writeField(userService, "timeoutInMinutes", 30, true);
        FieldUtils.writeField(userService, "tokenLength", 10, true);
    }

    /**
     * Test case: save a new user.
     * Expected Result: true.
     */
    @Test
    public void testSaveUser() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //do actual test.
        assertTrue(userService.save(user));
    }

    /**
     * Test case: find a user by company and username.
     * Expected Result: user is not null.
     */
    @Test
    public void testFindUserByCompanyAndUserName() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.when(userRepository.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(user);
        //do actual test.
        assertNotNull(userService.findByCompanyAndUserName("Example Company", "max.mustermann"));
    }

    /**
     * Test case: delete a user.
     * Currently void so no validation possible.
     */
    @Test
    public void testDeleteUser() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.doNothing().when(userRepository).delete(user);
        //do actual test.
        userService.delete(user);
    }

    /**
     * Test case: generate a token.
     * Expected result: string is not null.
     */
    @Test
    public void testAuthTokens() {
        //do actual test.
        String token = userService.generateAuthToken("max.mustermann");
        assertNotNull(token);
        assertTrue(userService.checkAuthToken(token));
        userService.removeAuthToken(token);
    }

    /**
     * Test case: reset user psssword
     * Expected result: true if user could be found or false if not.
     */
    @Test
    public void testResetUserPassword() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.when(userRepository.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(user);
        Mockito.when(userRepository.findByCompanyAndUserName("Example Company 2", "max.mustermann")).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //do actual test.
        assertTrue(userService.resetUserPassword("Example Company", "max.mustermann", "123test"));
        assertFalse(userService.resetUserPassword("Example Company 2", "max.mustermann", "123test"));
    }

    /**
     * Test case: change user psssword
     * Expected result: true if user could be found or false if not.
     */
    @Test
    public void testChangeUserPassword() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.when(userRepository.findByCompanyAndUserName("Example Company", "max.mustermann")).thenReturn(user);
        Mockito.when(userRepository.findByCompanyAndUserName("Example Company 2", "max.mustermann")).thenReturn(null);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //do actual test.
        assertTrue(userService.changePassword("Example Company", "max.mustermann", user.getPassword(), "123test"));
        assertFalse(userService.changePassword("Example Company 2", "max.mustermann", user.getPassword(), "123test"));
    }

    /**
     * Test case: deactivate a user.
     * Expected result: 13 as the number of days that the user can take this year.
     */
    @Test
    public void testDeactivateUser() {
        //Test data
        User user = generateValidUser();
        //Mock important method in repository.
        Mockito.when(userRepository.save(user)).thenReturn(user);
        //do actual test.
        userService.deactivate(user);
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
                .build();
    }

}
