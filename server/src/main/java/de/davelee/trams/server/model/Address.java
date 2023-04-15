package de.davelee.trams.server.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents an address which is mapped to a particular stop. This stop is the closest stop to this address.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Address {

    /**
     * A unique id for this address.
     */
    @Id
    private ObjectId id;

    /**
     * The operator which serves this address.
     */
    private String addressOperator;

    /**
     * The address that can be searched for.
     */
    private String address;

    /**
     * The stop that serves this address.
     */
    private Stop stop;

    /**
     * The distance between stop and address in minutes.
     */
    private int durationInMins;

}
