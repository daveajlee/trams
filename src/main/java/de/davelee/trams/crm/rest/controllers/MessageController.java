package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Customer;
import de.davelee.trams.crm.model.Feedback;
import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.request.FeedbackRequest;
import de.davelee.trams.crm.request.MessageRequest;
import de.davelee.trams.crm.services.MessageService;
import de.davelee.trams.crm.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
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
@Api(value="/api/message")
@RequestMapping(value="/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Add a message to the system.
     * @param messageRequest a <code>MessageRequest</code> object representing the message to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @ApiOperation(value = "Add a message", notes="Add a message to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=201,message="Successfully created message")})
    public ResponseEntity<Void> addMessage (@RequestBody final MessageRequest messageRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(messageRequest.getCompany()) || StringUtils.isBlank(messageRequest.getDateTime())
                || StringUtils.isBlank(messageRequest.getFolder()) || StringUtils.isBlank(messageRequest.getSender())
                || StringUtils.isBlank(messageRequest.getSubject()) || StringUtils.isBlank(messageRequest.getText())) {
            return ResponseEntity.badRequest().build();
        }
        //Now create message object and save to message service. Return 201 if saved successfully.
        return messageService.save(Message.builder()
                .folder(messageRequest.getFolder())
                .dateTime(DateUtils.convertDateToLocalDateTime(messageRequest.getDateTime()))
                .sender(messageRequest.getSender())
                .subject(messageRequest.getSubject())
                .text(messageRequest.getText())
                .company(messageRequest.getCompany())
                .build()) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
