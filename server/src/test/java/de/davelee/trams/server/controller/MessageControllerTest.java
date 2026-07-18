package de.davelee.trams.server.controller;

import de.davelee.trams.server.request.MessageRequest;
import de.davelee.trams.server.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the Message endpoints in the TraMS Server REST API.
 * @author Dave Lee
 */
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;

    @Mock
    private MessageService messageService;

    /**
     * Test case: add a message to the system based on a valid message request.
     * Expected Result: message added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in message service.
        Mockito.when(messageService.save(any())).thenReturn(true);
        //Add message so that test is successfully.
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setCompany("Mustermann GmbH");
        messageRequest.setText("Great transport company");
        messageRequest.setSender("Local Authority");
        messageRequest.setDateTime("28-11-2020 15:16");
        messageRequest.setFolder("INBOX");
        messageRequest.setSubject("Feedback");
        assertEquals("MessageRequest(company=Mustermann GmbH, subject=Feedback, text=Great transport company, sender=Local Authority, folder=INBOX, dateTime=28-11-2020 15:16)", messageRequest.toString());
        ResponseEntity<Void> responseEntity = messageController.addMessage(messageRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: add a message to the system based on an invalid message request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Add message so that test is successfully.
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setText("Great transport company");
        messageRequest.setSender("Local Authority");
        messageRequest.setDateTime("28-11-2020 15:16");
        messageRequest.setFolder("INBOX");
        messageRequest.setSubject("Feedback");
        ResponseEntity<Void> responseEntity = messageController.addMessage(messageRequest);
        assertTrue(responseEntity.getStatusCode().value() == HttpStatus.BAD_REQUEST.value());
        messageRequest.setCompany("Mustermann GmbH");
        messageRequest.setDateTime("01-15-2021 56:43");
        ResponseEntity<Void> responseEntity2 = messageController.addMessage(messageRequest);
        assertTrue(responseEntity2.getStatusCode().value() == HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

}
