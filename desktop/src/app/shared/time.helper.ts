export class TimeHelper {

    /**
     * Helper method to format time as HH:mm
     * @param time the date object to format
     * @return time as a String in format HH:mm
     */
    static formatTimeAsString(time: Date): string {
        let hour: string;
        // Format hour with a 0 as prefix if before 10.
        if ( time.getHours() < 10 ) {
            hour = "0" + time.getHours();
        } else {
            hour = "" + time.getHours();
        }
        let minute: string;
        // Format minutes with a 0 as prefix if before 10.
        if ( time.getMinutes() < 10 ) {
            minute = "0" + time.getMinutes();
        } else {
            minute = "" + time.getMinutes();
        }
        return hour + ":" + minute;
    }

}