package de.davelee.trams.server.model;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class represents the operating days of a Stop Time.
 * An operating day can consist of either a day of the week or calendar dates or both.
 * @author Dave Lee
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OperatingDays {

    /**
     * The days on which this stop takes place.
     */
    private List<DayOfWeek> operatingDays;

    /**
     * Special operating days on which this stop takes place in addition to operating days.
     */
    private List<LocalDateTime> specialOperatingDays;

    /**
     * Operating days where this service does not run because of disruptions.
     */
    private List<LocalDateTime> disruptedOperatingDays;

    /**
     * Check if the supplied date is an operating day for this stop time. This means either that the stop times
     * run on this day of the week or on the supplied date and that this is not disrupted because of construction etc.
     * @param currentDateTime a <code>LocalDateTime</code> containing the date to check.
     * @return a <code>boolean</code> which is true iff this stop time operates on the supplied date.
     */
    public boolean checkIfOperatingDay ( final LocalDateTime currentDateTime ) {
        if ( disruptedOperatingDays != null && disruptedOperatingDays.contains(currentDateTime) ) {
            return false;
        }
        return operatingDays.contains(currentDateTime.getDayOfWeek()) || specialOperatingDays.contains(currentDateTime);
    }

}
