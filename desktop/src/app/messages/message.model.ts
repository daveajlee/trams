/**
 * This class defines a model for Messages in TraMS.
 */
export class Message {

    public subject: string;
    public content: string;
    public folder: string;

    constructor(subject: string, content: string, folder: string) {
        this.subject = subject;
        this.content = content;
        this.folder = folder;
    }


}