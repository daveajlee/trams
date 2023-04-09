package de.davelee.trams.crm.request;

import lombok.*;

import java.util.Map;

/**
 * This class is part of the TraMS CRM REST API. It represents a request to add the following feedback to the server
 * containing customer (via email address &amp; company), message and extraInfos (key/value pair).
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackRequest {

    /**
     * The email address of the customer e.g. max@mustermann.de
     */
    private String emailAddress;

    /**
     * The company that the customer has registered with to give feedback.
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

}
