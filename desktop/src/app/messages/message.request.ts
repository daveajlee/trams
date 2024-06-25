/**
 * This class defines a model for requests to create messages in TraMS.
 */
export class MessageRequest {

    private company: string;
    private subject: string;
    private text: string;
    private sender: string;
    private folder: string;
    private dateTime: string;

    /**
     * Create a new message request.
     * @param company the company that the message belongs to as a string.
     * @param subject the subject of the message as a string.
     * @param content the content of the message as a string.
     * @param sender the sender of the message as a string.
     * @param folder the folder of the message as a string.
     * @param date the date of the message as a string in the format dd-MM-yyyy HH:mm.
     */
    constructor(company: string, subject: string, content: string, sender: string, folder: string, date: string) {
        this.company = company;
        this.subject = subject;
        this.text = content;
        this.sender = sender;
        this.folder = folder;
        this.dateTime = date;
    }

    /**
     * Retrieve the subject of the message.
     * @return the subject as a string.
     */
    getSubject(): string {
        return this.subject;
    }

    /**
     * Retrieve the content of the message.
     * @return the content as a string.
     */
    getContent(): string {
        return this.text;
    }

    /**
     * Retrieve the folder of the message.
     * @return the folder as a string.
     */
    getFolder(): string {
        return this.folder;
    }

    /**
     * Retrieve the date and time of the message.
     * @return the date and time as a string in the format dd-MM-yyyy HH:mm.
     */
    getDateTime(): String {
        return this.dateTime;
    }

    /**
     * Retrieve the sender of the message.
     * @return the sender as a string.
     */
    getSender(): string {
        return this.sender;
    }

}