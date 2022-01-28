package de.davelee.trams.operations.model;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
    private List<LocalDate> specialOperatingDays;

    /**
     * Check if the supplied date is an operating day for this stop time.
     * @param currentDate a <code>LocalDate</code> containing the date to check.
     * @return a <code>boolean</code> which is true iff this stop time operates on the supplied date.
     */
    public boolean checkIfOperatingDay ( final LocalDate currentDate ) {
        return operatingDays.contains(currentDate.getDayOfWeek()) || specialOperatingDays.contains(currentDate);
    }

}
