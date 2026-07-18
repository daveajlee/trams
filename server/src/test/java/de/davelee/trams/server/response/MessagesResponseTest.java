package de.davelee.trams.server.response;

import de.davelee.trams.server.utils.CustomerUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the constructor, getter and setter methods of the <code>MessagesResponse</code> class.
 */
public class MessagesResponseTest {

    /**
     * Test the setter methods and ensure variables are set together using the getter methods.
     */
    @Test
    public void testGettersAndSetters() {
        MessageResponse[] messageResponses = new MessageResponse[1];
        messageResponses[0] = new MessageResponse();
        messageResponses[0].setCompany("Mustermann GmbH");
        messageResponses[0].setFolder("INBOX");
        messageResponses[0].setSender("Local Authority");
        messageResponses[0].setText("This is a test message");
        messageResponses[0].setSubject("Test");
        messageResponses[0].setDateTime("28-12-2020 12:22");
        MessagesResponse messagesResponse = new MessagesResponse();
        messagesResponse.setCount(1L);
        messagesResponse.setMessageResponses(messageResponses);
        assertEquals(1L, messagesResponse.getCount());
        assertEquals(1, messagesResponse.getMessageResponses().length);
    }

}
