package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Class to represent feedback messages sent by customers and answered by a company in TraMS Server.
 * @author Dave Lee
 */
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

    public Feedback() {
    }

    public Feedback(ObjectId id, Customer customer, String emailAddress, String company, String message, Map<String, String> extraInfos, String answer) {
        this.id = id;
        this.customer = customer;
        this.emailAddress = emailAddress;
        this.company = company;
        this.message = message;
        this.extraInfos = extraInfos;
        this.answer = answer;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getExtraInfos() {
        return extraInfos;
    }

    public void setExtraInfos(Map<String, String> extraInfos) {
        this.extraInfos = extraInfos;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
