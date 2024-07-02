import {MessageResponse} from "./message.response";

/**
 * This class represents a response from the server containing details
 * of all matched messages according to specified criteria. As well as containing details about the messages in form of
 * an array of MessageResponse objects, the object also contains a simple count of the messages.
 */
export class MessagesResponse {

    //a count of the number of messages which were found by the server.
    public count: number;

    //an array of all messages found by the server.
    public messageResponses: MessageResponse[];

}