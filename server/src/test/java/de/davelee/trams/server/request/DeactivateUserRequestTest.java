package de.davelee.trams.server.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>DeactivateUserRequest</code> class.
 */
public class DeactivateUserRequestTest {

    /**
     * Test the constructor and ensure variables are set together using the getter methods.
     */
    @Test
    public void testConstructor() {
        DeactivateUserRequest deactivateUserRequest = new DeactivateUserRequest("MyCompany", "dlee", "dlee-ghgkg");
        assertEquals("MyCompany", deactivateUserRequest.getCompany());
        assertEquals("dlee", deactivateUserRequest.getUsername());
        assertEquals("dlee-ghgkg", deactivateUserRequest.getToken());
    }

    /**
     * Test the builder and ensure variables are set together using the getter methods.
     */
    @Test
    public void testBuilder() {
        DeactivateUserRequest deactivateUserRequest = DeactivateUserRequest.builder()
                .company("MyCompany")
                .username("dlee")
                .token("dlee-ghgkg")
                .build();
        assertEquals("MyCompany", deactivateUserRequest.getCompany());
        assertEquals("dlee", deactivateUserRequest.getUsername());
        assertEquals("dlee-ghgkg", deactivateUserRequest.getToken());
    }

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testSettersAndGetters() {
        DeactivateUserRequest deactivateUserRequest = new DeactivateUserRequest();
        deactivateUserRequest.setCompany("MyCompany");
        assertEquals("MyCompany", deactivateUserRequest.getCompany());
        deactivateUserRequest.setUsername("dlee");
        assertEquals("dlee", deactivateUserRequest.getUsername());
        deactivateUserRequest.setToken("dlee-ghgkg");
        assertEquals("dlee-ghgkg", deactivateUserRequest.getToken());
    }

}
