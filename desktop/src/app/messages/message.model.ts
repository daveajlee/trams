/**
 * This class defines a model for Messages in TraMS.
 */
export class Message {

    public subject: string;
    public content: string;
    public folder: string;
    public sender: string;
    public recipient: string;
    public date: Date;

    constructor(subject: string, content: string, folder: string, date: Date) {
        this.subject = subject;
        this.content = content;
        this.folder = folder;
        this.date = date;
    }

    getDisplayDate() {
        return this.date.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
    }

}