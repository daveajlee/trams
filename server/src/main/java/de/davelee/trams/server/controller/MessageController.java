package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Message;
import de.davelee.trams.server.request.MessageRequest;
import de.davelee.trams.server.service.MessageService;
import de.davelee.trams.server.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class defines the endpoints for the REST API which manipulate a single message and delegates the actions to the MessageService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/message")
@RequestMapping(value="/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Add a message to the system.
     * @param messageRequest a <code>MessageRequest</code> object representing the message to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a message", description="Add a message to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created message")})
    public ResponseEntity<Void> addMessage (@RequestBody final MessageRequest messageRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (messageRequest.getCompany() == null || messageRequest.getDateTime() == null ||
                messageRequest.getFolder() == null || messageRequest.getSender() == null ||
                messageRequest.getSubject() == null || messageRequest.getText() == null ||
                messageRequest.getCompany().isBlank() || messageRequest.getDateTime().isBlank()
                || messageRequest.getFolder().isBlank() || messageRequest.getSender().isBlank()
                || messageRequest.getSubject().isBlank() || messageRequest.getText().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        //Now create message object and save to message service. Return 201 if saved successfully.
        Message message = new Message();
        message.setFolder(messageRequest.getFolder());
        message.setDateTime(DateUtils.convertDateToLocalDateTime(messageRequest.getDateTime()));
        message.setSender(messageRequest.getSender());
        message.setSubject(messageRequest.getSubject());
        message.setText(messageRequest.getText());
        message.setCompany(messageRequest.getCompany());
        return messageService.save(message) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
