package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Ticket;
import de.davelee.trams.server.request.TicketRequest;
import de.davelee.trams.server.service.TicketService;
import de.davelee.trams.server.service.UserService;
import de.davelee.trams.server.utils.TicketUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class defines the endpoints for the REST API which manipulate tickets and delegates the actions to the TicketService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/ticket")
@RequestMapping(value="/api/ticket")
public class TicketController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    /**
     * Add a ticket to the system. Only logged in users may add tickets to the system.
     * @param ticketRequest a <code>TicketRequest</code> object representing the ticket to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a ticket", description="Add a ticket to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created ticket")})
    public ResponseEntity<Void> addTicket (@RequestBody final TicketRequest ticketRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(ticketRequest.getShortId()) || StringUtils.isBlank(ticketRequest.getType())
                || StringUtils.isBlank(ticketRequest.getDescription()) || StringUtils.isBlank(ticketRequest.getCompany())
                || StringUtils.isBlank(ticketRequest.getToken()) || ticketRequest.getPriceList() == null) {
            return ResponseEntity.badRequest().build();
        }
        //Check that the user has logged in, otherwise forbidden.
        if ( !userService.checkAuthToken(ticketRequest.getToken()) ) {
            return ResponseEntity.status(403).build();
        }
        //Now create ticket object and save to ticket service. Return 201 if saved successfully.
        return ticketService.save(Ticket.builder()
                .shortId(ticketRequest.getShortId())
                .type(ticketRequest.getType())
                .company(ticketRequest.getCompany())
                .description(ticketRequest.getDescription())
                .sortOrder(ticketRequest.getSortOrder())
                .priceList(TicketUtils.convertPriceListToBigDecimal(ticketRequest.getPriceList()))
                .company(ticketRequest.getCompany())
                .build()) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

}
