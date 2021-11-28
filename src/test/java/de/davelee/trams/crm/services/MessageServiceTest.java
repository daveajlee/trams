package de.davelee.trams.crm.services;

import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for the MessageService class - the MessageRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    /**
     * Test case: save a new message.
     * Expected Result: true.
     */
    @Test
    public void testSaveMessage() {
        //Test data
        Message message = generateValidMessage();
        //Mock important method in repository.
        Mockito.when(messageRepository.save(message)).thenReturn(message);
        //do actual test.
        assertTrue(messageService.save(message));
    }

    /**
     * Test case: get all messages for this company.
     * Expected result: messages returned.
     */
    @Test
    public void testGetAllMessages() {
        //Mock important method in repository.
        Mockito.when(messageRepository.findByCompany("Mustermann GmbH")).thenReturn(List.of(generateValidMessage()));
        //Do actual test.
        assertEquals(1, messageService.getMessagesByCompany("Mustermann GmbH").size());
    }

    /**
     * Test case: delete all messages for this company.
     */
    @Test
    public void testDeleteAllMessages() {
        messageService.deleteMessage(generateValidMessage());
    }

    /**
     * Private helper method to generate a valid message.
     * @return a <code>Message</code> object containing valid test data.
     */
    private Message generateValidMessage( ) {
        Message message = new Message();
        message.setCompany("Mustermann GmbH");
        message.setFolder("INBOX");
        message.setDateTime(LocalDateTime.of(2020,12,28,14,22));
        message.setSender("Local Authority");
        message.setSubject("Test message");
        message.setText("My Test Message");
        return message;
    }

}
