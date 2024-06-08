/**
 * This class defines a model for Messages in TraMS.
 */
export class Message {

    private subject: string;
    private content: string;
    private folder: string;
    private sender: string;
    private recipient: string;
    private date: Date;

    constructor(subject: string, content: string, folder: string, date: Date) {
        this.subject = subject;
        this.content = content;
        this.folder = folder;
        this.date = date;
    }

    getDisplayDate() {
        return this.date.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
    }

    /**
     * Get the sender of this message.
     * @return the sender of this message as a String.
     */
    getSender(): string {
        return this.sender;
    }

    /**
     * Get the recipient of this message.
     * @return the recipient of this message as a String.
     */
    getRecipient(): string {
        return this.recipient;
    }

    /**
     * Set the sender of this message.
     * @param sender the sender of this message as a String.
     */
    setSender(sender: string): void {
        this.sender = sender;
    }

    /**
     * Set the recipient of this message.
     * @param recipient the recipient of this message as a String.
     */
    setRecipient(recipient: string): void {
        this.recipient = recipient;
    }

    /**
     * Get the folder of this message.
     * @return the folder of this message as a String.
     */
    getFolder(): string {
        return this.folder;
    }

    /**
     * Get the subject of this message.
     * @return the subject of this message as a String.
     */
    getSubject(): string {
        return this.subject;
    }

    /**
     * Get the content of this message.
     * @return the content of this message as a String.
     */
    getContent(): string {
        return this.content;
    }

}