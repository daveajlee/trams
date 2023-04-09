package de.davelee.trams.crm.model;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

}
