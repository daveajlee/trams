package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched tickets according to specified criteria. As well as containing details about the tickets in form of
 * an array of <code>TicketResponse</code> objects, the object also contains a simple count of the tickets.
 * @author Dave Lee
 */
public class TicketsResponse {

    //a count of the number of tickets which were found by the server.
    private Long count;

    //an array of all tickets found by the server.
    private TicketResponse[] ticketResponses;

    public TicketsResponse() {
    }

    public TicketsResponse(Long count, TicketResponse[] ticketResponses) {
        this.count = count;
        this.ticketResponses = ticketResponses;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public TicketResponse[] getTicketResponses() {
        return ticketResponses;
    }

    public void setTicketResponses(TicketResponse[] ticketResponses) {
        this.ticketResponses = ticketResponses;
    }
}
