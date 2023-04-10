package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Ticket;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TicketsResponse> getFeedbacksByCompany(@RequestParam("company") final String company) {
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

}
