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

    /**
     * Helper method to format date and time as dd-MM-yyyy HH:mm
     * @param dateTime the date object to format.
     * @return date as a string in format dd-MM-yyyy HH:mm
     */
    static formatDateTimeAsString(dateTime: Date) {
        let dateString;
        // Format date.
        if ( dateTime.getDate() < 10 ) {
            dateString = "0" + dateTime.getDate();
        } else {
            dateString = "" + dateTime.getDate();
        }
        // Format month.
        if ( (dateTime.getMonth()+1) < 10 ) {
            dateString += "-0" + (dateTime.getMonth()+1);
        } else {
            dateString += "-" + (dateTime.getMonth()+1);
        }
        // Format year.
        dateString += "-" + dateTime.getFullYear();
        // Append time.
        return dateString + " " + this.formatTimeAsString(dateTime);
    }

    /**
     * Helper method to convert the string dd-MM-yyyy HH:mm into a javascript object.
     * @param dateTime the string dd-MM-yyyy HH:mm to convert to a javascript Date object.
     * @return the date returned as a javascript Date object.
     */
    static formatStringAsDateObject(dateTime: string) {
        const dateTimeSplit = dateTime.split(" ");
        const dateParts = dateTimeSplit[0].split("-");
        const timeParts = dateTimeSplit[1].split(":");

        const year = parseInt(dateParts[2], 10);
        const month = parseInt(dateParts[1], 10) - 1;
        const day = parseInt(dateParts[0], 10);

        const hour = parseInt(timeParts[0], 10);
        const minute = parseInt(timeParts[1], 10);
        return new Date(year, month, day, hour, minute);

    }

    /**
     * This is a helper method which adds a number of minutes to the time.
     * @param time the time as a string in the format HH:mm
     * @param addMinutes the number of minutes to add to the time.
     * @return the updated time as a string in the format HH:mm
     */
    static addTime(time: string, addMinutes: number): string {
        // Extract the time from the string.
        var hours = parseInt(time.split(":")[0]);
        var minutes = parseInt(time.split(":")[1]);
        // Add the minutes to the current time.
        minutes += addMinutes;
        // Adjust the minutes if it is now higher than 59.
        while ( minutes > 59 ) {
            hours++;
            minutes -= 60;
        }
        // Adjust the hours if it is now higher than 23.
        while ( hours > 23 ) {
            hours -= 24;
        }
        // Return the time in format HH:mm
        return (hours < 10 ? "0" + hours : hours ) + ":" + (minutes < 10 ? "0" + minutes : minutes )
    }

    /**
     * This is a helper method which subtracts a number of minutes to the time.
     * @param time the time as a string in the format HH:mm
     * @param subtractMinutes the number of minutes to subtract from the time.
     * @return the updated time as a string in the format HH:mm
     */
    static subtractTime(time: string, subtractMinutes: number): string {
        // Extract the time from the string.
        var hours = parseInt(time.split(":")[0]);
        var minutes = parseInt(time.split(":")[1]);
        // Add the minutes to the current time.
        minutes -= subtractMinutes;
        // Adjust the minutes if it is less than 0.
        while ( minutes < 0 ) {
            hours--;
            minutes += 60;
        }
        // Adjust the hours if it is now less than 0.
        while ( hours < 0 ) {
            hours += 24;
        }
        // Return the time in format HH:mm
        return (hours < 10 ? "0" + hours : hours ) + ":" + (minutes < 10 ? "0" + minutes : minutes )
    }

}