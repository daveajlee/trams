package de.davelee.trams.server.response;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response for a single ticket from the server
 * containing type, description, sorting order, price list and company.
 * @author Dave Lee
 */
public class TicketResponse {

    /**
     * The short id for this ticket - the shortId is used as the hash code as well.
     */
    private String shortId;

    /**
     * The type of this ticket - the type is also the name of the ticket.
     */
    private String type;

    /**
     * The description of this ticket - the description is a short info about how this ticket is valid.
     */
    private String description;

    /**
     * The sort order of this ticket - the sort order defines the order if this ticket is stored in a map.
     */
    private int sortOrder;

    /**
     * The price list of this ticket as a map consisting of descriptions and prices which are valid for this ticket.
     */
    private Map<String, Double> priceList;

    /**
     * The company that offers this ticket.
     */
    private String company;

    public TicketResponse() {
    }

    public TicketResponse(String shortId, String type, String description, int sortOrder, Map<String, Double> priceList, String company) {
        this.shortId = shortId;
        this.type = type;
        this.description = description;
        this.sortOrder = sortOrder;
        this.priceList = priceList;
        this.company = company;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, Double> getPriceList() {
        return priceList;
    }

    public void setPriceList(Map<String, Double> priceList) {
        this.priceList = priceList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "TicketResponse{" +
                "shortId='" + shortId + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", sortOrder=" + sortOrder +
                ", priceList=" + priceList +
                ", company='" + company + '\'' +
                '}';
    }
}
