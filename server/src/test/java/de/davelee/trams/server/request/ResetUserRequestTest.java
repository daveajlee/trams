package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>ResetUserRequest</code> class.
 */
public class ResetUserRequestTest {

    @Test
    /**
     * Test the constructor and ensure variables are set together using the getter methods.
     */
    public void testConstructor() {
        ResetUserRequest resetUserRequest = new ResetUserRequest("MyCompany", "dlee", "test123", "dlee-ghgkg");
        assertEquals("MyCompany", resetUserRequest.getCompany());
        assertEquals("dlee", resetUserRequest.getUsername());
        assertEquals("test123", resetUserRequest.getPassword());
        assertEquals("dlee-ghgkg", resetUserRequest.getToken());
    }

    @Test
    /**
     * Test the builder and ensure variables are set together using the getter methods.
     */
    public void testBuilder() {
        ResetUserRequest resetUserRequest = new ResetUserRequest();
        resetUserRequest.setCompany("MyCompany");
        resetUserRequest.setUsername("dlee");
        resetUserRequest.setPassword("test123");
        resetUserRequest.setToken("dlee-ghgkg");
        assertEquals("MyCompany", resetUserRequest.getCompany());
        assertEquals("dlee", resetUserRequest.getUsername());
        assertEquals("test123", resetUserRequest.getPassword());
        assertEquals("dlee-ghgkg", resetUserRequest.getToken());
    }

    @Test
    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    public void testSettersAndGetters() {
        ResetUserRequest resetUserRequest = new ResetUserRequest();
        resetUserRequest.setCompany("MyCompany");
        assertEquals("MyCompany", resetUserRequest.getCompany());
        resetUserRequest.setUsername("dlee");
        assertEquals("dlee", resetUserRequest.getUsername());
        resetUserRequest.setToken("dlee-ghgkg");
        assertEquals("dlee-ghgkg", resetUserRequest.getToken());
        resetUserRequest.setPassword("test123");
        assertEquals("test123", resetUserRequest.getPassword());
    }

}
