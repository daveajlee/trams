package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Class to represent messages that are sent or received within TraMS Server.
 * @author Dave Lee
 */
@Document
public class Message {

    /**
     * A unique id for this message.
     */
    @Id
    private ObjectId id;

    /**
     * The company receiving this message.
     */
    private String company;

    /**
     * The subject of the message.
     */
    private String subject;

    /**
     * The content of the message.
     */
    private String text;

    /**
     * The sender of this message.
     */
    private String sender;

    /**
     * The folder where this message is located.
     */
    private String folder;

    /**
     * The date and time that this message was sent.
     */
    private LocalDateTime dateTime;

    public Message() {
    }

    public Message(ObjectId id, String company, String subject, String text, String sender, String folder, LocalDateTime dateTime) {
        this.id = id;
        this.company = company;
        this.subject = subject;
        this.text = text;
        this.sender = sender;
        this.folder = folder;
        this.dateTime = dateTime;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
