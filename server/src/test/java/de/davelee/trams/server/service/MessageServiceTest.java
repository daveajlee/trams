package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Message;
import de.davelee.trams.server.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        assertEquals(1, messageService.getMessagesByCompany("Mustermann GmbH", Optional.empty(), Optional.empty(), Optional.empty()).size());
    }

    /**
     * Test case: delete all messages for this company.
     */
    @Test
    public void testDeleteAllMessages() {
        messageService.deleteMessage(generateValidMessage());
    }

    /**
     * Test case: retrieve messages for this company.
     */
    @Test
    public void testRetrieveMessages() {
        //Company, folder, sender and DateTime
        Mockito.when(messageRepository.findByCompanyAndFolderAndSenderAndDateTime("Mustermann GmbH", "INBOX", "Local Authority", LocalDateTime.of(2020,12,18,12,22))).thenReturn(List.of(generateValidMessage()));
        assertEquals(1, messageService.getMessagesByCompany("Mustermann GmbH", Optional.of("INBOX"), Optional.of("Local Authority"), Optional.of("18-12-2020 12:22")).size());
        //Company, folder and sender
        Mockito.when(messageRepository.findByCompanyAndFolderAndSender("Mustermann GmbH", "INBOX", "Local Authority")).thenReturn(List.of(generateValidMessage(), generateValidMessage()));
        assertEquals(2, messageService.getMessagesByCompany("Mustermann GmbH", Optional.of("INBOX"), Optional.of("Local Authority"), Optional.empty()).size());
        //Company, folder and DateTime
        Mockito.when(messageRepository.findByCompanyAndFolderAndDateTime("Mustermann GmbH", "INBOX", LocalDateTime.of(2020,12,18,12,22))).thenReturn(List.of(generateValidMessage(), generateValidMessage(), generateValidMessage()));
        assertEquals(3, messageService.getMessagesByCompany("Mustermann GmbH", Optional.of("INBOX"), Optional.empty(), Optional.of("18-12-2020 12:22")).size());
        //Company and folder
        Mockito.when(messageRepository.findByCompanyAndFolder("Mustermann GmbH", "INBOX")).thenReturn(List.of(generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage()));
        assertEquals(4, messageService.getMessagesByCompany("Mustermann GmbH", Optional.of("INBOX"), Optional.empty(), Optional.empty()).size());
        //Company and sender and dateTime
        Mockito.when(messageRepository.findByCompanyAndSenderAndDateTime("Mustermann GmbH", "Local Authority", LocalDateTime.of(2020,12,18,12,22))).thenReturn(List.of(generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage()));
        assertEquals(5, messageService.getMessagesByCompany("Mustermann GmbH", Optional.empty(), Optional.of("Local Authority"), Optional.of("18-12-2020 12:22")).size());
        //Company and sender
        Mockito.when(messageRepository.findByCompanyAndSender("Mustermann GmbH", "Local Authority")).thenReturn(List.of(generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage()));
        assertEquals(6, messageService.getMessagesByCompany("Mustermann GmbH", Optional.empty(), Optional.of("Local Authority"), Optional.empty()).size());
        //Company and DateTime
        Mockito.when(messageRepository.findByCompanyAndDateTime("Mustermann GmbH", LocalDateTime.of(2020,12,18,12,22))).thenReturn(List.of(generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage(), generateValidMessage()));
        assertEquals(7, messageService.getMessagesByCompany("Mustermann GmbH", Optional.empty(), Optional.empty(), Optional.of("18-12-2020 12:22")).size());
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
