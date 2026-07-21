package de.davelee.trams.server.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This class tests the builder, getter and setter methods of the <code>LoginResponse</code> class.
 */
public class LoginResponseTest {

    @Test
    public void testGoodLogin( ) {
        LoginResponse loginResponse = new LoginResponse("", "dlee-ghgkg");
        assertEquals("dlee-ghgkg", loginResponse.getToken());
        assertNull(loginResponse.getErrorMessage());
    }

    @Test
    public void testBadLogin( ) {
        LoginResponse loginResponse = new LoginResponse("Password was incorrect!", "");
        assertEquals("Password was incorrect!", loginResponse.getErrorMessage());
        assertNull(loginResponse.getToken());
    }

    @Test
    public void testGoodLoginWithSetters( ) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("dlee-ghgkg");
        assertEquals("dlee-ghgkg", loginResponse.getToken());
        assertNull(loginResponse.getErrorMessage());
    }

    @Test
    public void testBadLoginWithSetters( ) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setErrorMessage("Password was incorrect!");
        assertEquals("Password was incorrect!", loginResponse.getErrorMessage());
        assertNull(loginResponse.getToken());
    }

}
