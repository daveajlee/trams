package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.services.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the Messages endpoints in the TraMS CRM REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class MessagesControllerTest {

    @InjectMocks
    private MessagesController messagesController;

    @Mock
    private MessageService messageService;

    /**
     * Test case: delete all messages for a company.
     * Expected Result: ok.
     */
    @Test
    public void testDelete() {
        //Add mock data.
        Mockito.when(messageService.getMessagesByCompany("Mustermann GmbH")).thenReturn(List.of(
                Message.builder()
                .company("Mustermann GmbH")
                .folder("INBOX")
                .dateTime(LocalDateTime.of(2020,12,28,14,22))
                .sender("Local Authority")
                .subject("Test message")
                .text("My Test Message")
                .build()
        ));
        //Do actual test.
        assertEquals(HttpStatus.OK, messagesController.deleteMessagesByCompany("Mustermann GmbH").getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, messagesController.deleteMessagesByCompany("Lee Transport").getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, messagesController.deleteMessagesByCompany("").getStatusCode());
    }

}
