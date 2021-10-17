package de.davelee.trams.operations.utils;

import de.davelee.trams.operations.model.InspectionStatus;
import de.davelee.trams.operations.response.VehicleResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * This class provides utility methods for processing related to /vehicle and /vehicles endpoints in the TraMS Operations.
 * @author Dave Lee
 */
public class VehicleUtils {

    /**
     * This is a private helper method to calculate the inspection status of a vehicle and how many days until the next
     * inspection is due based on the last inspection date.
     * @param vehicleResponse a <code>VehicleResponse</code> object to write the results of the calculations in.
     * @param inspectionDate a <code>LocalDate</code> containing the date of the last inspection range
     * @param inspectionPeriod a <code>int</code> containing the number of years within which an inspection must take place
     */
    public static void processInspectionDate (final VehicleResponse vehicleResponse, final LocalDate inspectionDate,
                                        final int inspectionPeriod ) {
        if ( inspectionDate.isAfter(LocalDate.now().minusYears(inspectionPeriod)) ) {
            vehicleResponse.setInspectionStatus(InspectionStatus.INSPECTED.getInspectionNotice());
            vehicleResponse.setNextInspectionDueInDays(ChronoUnit.DAYS.between(LocalDate.now(),
                    inspectionDate.plusYears(inspectionPeriod)));
        } else {
            vehicleResponse.setInspectionStatus(InspectionStatus.INSPECTION_DUE.getInspectionNotice());
            vehicleResponse.setNextInspectionDueInDays(0);
        }
    }

}
