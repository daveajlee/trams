package de.davelee.trams.server.response;

import lombok.*;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of all matched messages according to specified criteria. As well as containing details about the messages in form of
 * an array of <code>MessagesResponse</code> objects, the object also contains a simple count of the messages.
 * @author Dave Lee
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessagesResponse {

    //a count of the number of messages which were found by the server.
    private Long count;

    //an array of all messages found by the server.
    private MessagesResponse[] messageResponses;

}
