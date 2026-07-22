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
        if (company.isBlank()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Now retrieve the ticket data.
        List<Ticket> tickets = ticketService.findByCompany(company);
        //Convert to TicketResponse object and return 200.
        TicketResponse[] ticketResponses = new TicketResponse[tickets.size()];;
        for (int i = 0; i < tickets.size(); i++) {
            ticketResponses[i] = new TicketResponse();
            ticketResponses[i].setShortId(tickets.get(i).getShortId());
            ticketResponses[i].setCompany(tickets.get(i).getCompany());
            ticketResponses[i].setDescription(tickets.get(i).getDescription());
            ticketResponses[i].setSortOrder(tickets.get(i).getSortOrder());
            ticketResponses[i].setType(tickets.get(i).getType());
            ticketResponses[i].setPriceList(TicketUtils.convertPriceListToDouble(tickets.get(i).getPriceList()));
        }
        return ResponseEntity.ok(new TicketsResponse((long) ticketResponses.length, ticketResponses));
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
            Ticket ticket = new Ticket();
            ticket.setShortId(ticketRequest.getShortId());
            ticket.setType(ticketRequest.getType());
            ticket.setCompany(ticketRequest.getCompany());
            ticket.setDescription(ticketRequest.getDescription());
            ticket.setSortOrder(ticketRequest.getSortOrder());
            ticket.setPriceList(TicketUtils.convertPriceListToBigDecimal(ticketRequest.getPriceList()));
            ticket.setCompany(ticketRequest.getCompany());
            boolean result = ticketService.save(ticket);
            if ( !result ) {
                // Return 500 if ticket could not be saved.
                return ResponseEntity.status(500).build();
            }
        }
        // Return 201 if all tickets could be created.
        return ResponseEntity.status(201).build();
    }

}
