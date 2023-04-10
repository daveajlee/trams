package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test cases for the <class>Message</class> class which are not covered
 * by other tests.
 * @author Dave Lee
 */
public class MessageTest {

    /**
     * Test case: construct an empty <code>Feedback</code> object
     * fill it with values through setters and return string of it.
     * Expected Result: valid values and string.
     */
    @Test
    public void testSetters() {
        Message message = new Message();
        message.setCompany("Mustermann GmbH");
        message.setFolder("INBOX");
        message.setDateTime(LocalDateTime.of(2020,12,28,14,22));
        message.setSender("Local Authority");
        message.setSubject("Test message");
        message.setText("My Test Message");
        message.setId(ObjectId.get());
        assertEquals("Mustermann GmbH", message.getCompany());
        assertEquals("INBOX", message.getFolder());
        assertEquals("Local Authority", message.getSender());
        assertEquals("Test message", message.getSubject());
        assertEquals("My Test Message", message.getText());
        assertNotNull(message.getDateTime());
        assertNotNull(message.getId());
    }

}
