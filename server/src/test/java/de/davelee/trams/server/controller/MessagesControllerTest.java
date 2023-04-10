package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Message;
import de.davelee.trams.server.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for the Messages endpoints in the TraMS Server REST API.
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
        Mockito.when(messageService.getMessagesByCompany("Mustermann GmbH", Optional.empty(), Optional.empty(), Optional.empty())).thenReturn(List.of(
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

    /**
     * Test case: retrieve all messages for a company matching supplied criteria.
     * Expected Result: ok.
     */
    @Test
    public void testRetrieveMessages() {
        //Add mock data.
        Mockito.when(messageService.getMessagesByCompany("Mustermann GmbH", Optional.of("INBOX"), Optional.of("Local Authority"), Optional.of("28-12-2020 14:22"))).thenReturn(List.of(
                Message.builder()
                        .company("Mustermann GmbH")
                        .folder("INBOX")
                        .dateTime(LocalDateTime.of(2020,12,28,14,22))
                        .sender("Local Authority")
                        .subject("Test message")
                        .text("My Test Message")
                        .build(),
                Message.builder()
                        .company("Mustermann GmbH")
                        .folder("INBOX")
                        .dateTime(null)
                        .sender("Local Authority")
                        .subject("Test message")
                        .text("My Test Message")
                        .build()
        ));
        //Do actual test.
        assertEquals(HttpStatus.OK, messagesController.getMessages("Mustermann GmbH",  Optional.of("INBOX"), Optional.of("Local Authority"), Optional.of("28-12-2020 14:22")).getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT, messagesController.getMessages("Lee Transport", Optional.empty(), Optional.empty(), Optional.empty()).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, messagesController.getMessages("", Optional.empty(), Optional.empty(), Optional.empty()).getStatusCode());
    }

}
