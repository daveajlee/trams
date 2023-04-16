package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Ticket;
import de.davelee.trams.server.request.TicketRequest;
import de.davelee.trams.server.response.TicketResponse;
import de.davelee.trams.server.response.TicketsResponse;
import de.davelee.trams.server.service.TicketService;
import de.davelee.trams.server.utils.TicketUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class defines the endpoints for the REST API which manipulate multiple tickets and delegates the actions to the TicketService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/tickets")
@RequestMapping(value="/api/tickets")
public class TicketsController {

    @Autowired
    private TicketService ticketService;

    /**
     * Retrieve all tickets available for a particular company. All users may retrieve tickets without being logged in.
     * @param company a <code>String</code> containing the name of the company.
     * @return a <code>ResponseEntity</code> containing the tickets for this company.
     */
    @Operation(summary = "Find all tickets for a company", description = "Find all tickets for a company in the system.")
    @GetMapping(value = "/")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully found ticket(s)"), @ApiResponse(responseCode = "204", description = "Successful but no tickets found")})
    public ResponseEntity<TicketsResponse> getTicketsByCompany(@RequestParam("company") final String company) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the ticket data.
        List<Ticket> tickets = ticketService.findByCompany(company);
        //Convert to TicketResponse object and return 200.
        TicketResponse[] ticketResponses = new TicketResponse[tickets.size()];;
        for (int i = 0; i < tickets.size(); i++) {
            ticketResponses[i] = TicketResponse.builder()
                    .shortId(tickets.get(i).getShortId())
                    .company(tickets.get(i).getCompany())
                    .description(tickets.get(i).getDescription())
                    .sortOrder(tickets.get(i).getSortOrder())
                    .type(tickets.get(i).getType())
                    .priceList(TicketUtils.convertPriceListToDouble(tickets.get(i).getPriceList()))
                    .build();
        }
        return ResponseEntity.ok(TicketsResponse.builder()
                .count((long) ticketResponses.length)
                .ticketResponses(ticketResponses)
                .build());
    }

    @Operation(summary = "Add a collection of available tickets", description="Method to add available tickets")
    @PostMapping(value="/availableTickets")
    @ApiResponses(value = {@ApiResponse(responseCode="201", description="All tickets successfully added")})
    /**
     * Save the supplied tickets into the system and return a 200 code to indicate that the tickets
     * were added successfully.
     * @param tickets a <code>Tickets</code> object containing the tickets to be added to the system.
     * @return a <code>ResponseEntity</code> object with the appropriate http status code.
     */
    public ResponseEntity<Void> addAvailableTickets (@RequestBody final List<TicketRequest> ticketRequests ) {
        // Go through the list of tickets.
        for ( TicketRequest ticketRequest : ticketRequests ) {
            // Save the ticket.
            boolean result = ticketService.save(Ticket.builder()
                    .shortId(ticketRequest.getShortId())
                    .type(ticketRequest.getType())
                    .company(ticketRequest.getCompany())
                    .description(ticketRequest.getDescription())
                    .sortOrder(ticketRequest.getSortOrder())
                    .priceList(TicketUtils.convertPriceListToBigDecimal(ticketRequest.getPriceList()))
                    .company(ticketRequest.getCompany())
                    .build());
            if ( !result ) {
                // Return 500 if ticket could not be saved.
                return ResponseEntity.status(500).build();
            }
        }
        // Return 201 if all tickets could be created.
        return ResponseEntity.status(201).build();
    }

}
