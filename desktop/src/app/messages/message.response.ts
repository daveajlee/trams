/**
 * This class represents a response containing a single message returned from the server.
 */
export class MessageResponse {

    /**
     * The company receiving this message.
     */
    public company: string;

    /**
     * The subject of the message.
     */
    public subject: string;

    /**
     * The content of the message.
     */
    public text: string;

    /**
     * The sender of this message.
     */
    public sender: string;

    /**
     * The folder where this message is located.
     */
    public folder: string;

    /**
     * The date and time that this message was sent.
     */
    public dateTime: string;

}