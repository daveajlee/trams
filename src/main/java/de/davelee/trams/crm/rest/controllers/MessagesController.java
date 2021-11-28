package de.davelee.trams.crm.rest.controllers;

import de.davelee.trams.crm.model.Message;
import de.davelee.trams.crm.services.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class defines the endpoints for the REST API which manipulate messages and delegates the actions to the MessageService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-crm/messages")
@RequestMapping(value="/trams-crm/messages")
public class MessagesController {

    @Autowired
    private MessageService messageService;

    /**
     * Delete all messages for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to delete messages for.
     */
    @DeleteMapping("/")
    @ApiOperation(value = "Delete messages", notes="Delete all messages")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully deleted messages"),@ApiResponse(code=204,message="Successful but no messages found")})
    public ResponseEntity<Void> deleteMessagesByCompany (final String company ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Retrieve the messages for this company.
        List<Message> messages = messageService.getMessagesByCompany(company);
        //If messages is null or empty then return 204.
        if ( messages == null || messages.size() == 0 ) {
            return ResponseEntity.noContent().build();
        }
        //Otherwise delete all messages and return.
        for ( Message message : messages ) {
            messageService.deleteMessage(message);
        }
        return ResponseEntity.ok().build();
    }

}
