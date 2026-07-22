package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add a message.
 * @author Dave Lee
 */
public class MessageRequest {

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
     * The date and time that this message was sent in format dd-MM-yyyy HH:mm.
     */
    private String dateTime;

    public MessageRequest() {
    }

    public MessageRequest(String company, String subject, String text, String sender, String folder, String dateTime) {
        this.company = company;
        this.subject = subject;
        this.text = text;
        this.sender = sender;
        this.folder = folder;
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "company='" + company + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", folder='" + folder + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
