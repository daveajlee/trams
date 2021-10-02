package de.davelee.trams.crm.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Class to represent feedback messages sent by customers and answered by a company in TraMS CRM.
 * @author Dave Lee
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Feedback {

    /**
     * A unique id for this feedback.
     */
    @Id
    private ObjectId id;

    /**
     * The customer giving this feedback.
     */
    private Customer customer;

    /**
     * The email address of the customer giving this feedback.
     */
    private String emailAddress;

    /**
     * The company getting this feedback.
     */
    private String company;

    /**
     * Message that the customer sent.
     */
    private String message;

    /**
     * Map of extra infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> extraInfos;

    /**
     * Answer that the company sends to the feedback.
     */
    private String answer;

}
