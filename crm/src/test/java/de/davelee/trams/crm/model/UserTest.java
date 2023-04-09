package de.davelee.trams.crm.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test cases for the <class>User</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class UserTest {

    @Test
    /**
     * Test case: build a <code>User</code> object and return string of it.
     * Expected Result: valid values and string.
     */
    public void testBuilderToString() {
        User user = User.builder()
                .firstName("Max")
                .lastName("Mustermann")
                .company("Example Company")
                .userName("mmustermann")
                .password("test123")
                .role("Admin")
                .accountStatus(UserAccountStatus.ACTIVE)
                .build();
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("Example Company", user.getCompany());
        assertEquals("mmustermann", user.getUserName());
        assertEquals("test123", user.getPassword());
        assertEquals("Admin", user.getRole());
        assertEquals(UserAccountStatus.ACTIVE, user.getAccountStatus());
    }

    @Test
    /**
     * Test case: construct an empty <code>User</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    public void testSettersToString() {
        User user = new User();
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setCompany("Example Company");
        user.setUserName("mmustermann");
        user.setPassword("test123");
        user.setRole("Admin");
        user.setAccountStatus(UserAccountStatus.DEACTIVATED);
        assertNull(user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("Example Company", user.getCompany());
        assertEquals("mmustermann", user.getUserName());
        assertEquals("test123", user.getPassword());
        assertEquals("Admin", user.getRole());
        assertEquals(UserAccountStatus.DEACTIVATED, user.getAccountStatus());
    }

}
