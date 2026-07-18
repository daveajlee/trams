package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

/**
 * This class represents a ticket consisting of a type, description and a price list allowing multiple prices for same ticket.
 * Each ticket is assigned to a company.
 * @author Dave Lee
 */
@Document
public class Ticket {

    /**
     * A unique id for this ticket.
     */
    @Id
    private ObjectId id;

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
    private Map<String, BigDecimal> priceList;

    /**
     * The company that offers this ticket.
     */
    private String company;

    public Ticket() {
    }

    public Ticket(ObjectId id, String shortId, String type, String description, int sortOrder, Map<String, BigDecimal> priceList, String company) {
        this.id = id;
        this.shortId = shortId;
        this.type = type;
        this.description = description;
        this.sortOrder = sortOrder;
        this.priceList = priceList;
        this.company = company;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public Map<String, BigDecimal> getPriceList() {
        return priceList;
    }

    public void setPriceList(Map<String, BigDecimal> priceList) {
        this.priceList = priceList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
