package de.davelee.trams.operations.utils;

import de.davelee.trams.operations.model.InspectionStatus;
import de.davelee.trams.operations.model.VehicleHistoryEntry;
import de.davelee.trams.operations.response.VehicleHistoryResponse;
import de.davelee.trams.operations.response.VehicleResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * This is a private helper method to convert history entries into a suitable response object.
     * @param vehicleHistoryEntryList a <code>VehicleHistoryEntry</code> List which should be converted
     * @return a <code>VehicleHistoryResponse</code> List which has been converted.
     */
    public static List<VehicleHistoryResponse> convertHistoryEntries (final List<VehicleHistoryEntry> vehicleHistoryEntryList) {
        List<VehicleHistoryResponse> vehicleHistoryResponseList = new ArrayList<>();
        if ( vehicleHistoryEntryList != null ) {
            for (VehicleHistoryEntry vehicleHistoryEntry : vehicleHistoryEntryList) {
                vehicleHistoryResponseList.add(VehicleHistoryResponse.builder()
                        .comment(vehicleHistoryEntry.getComment())
                        .vehicleHistoryReason(vehicleHistoryEntry.getVehicleHistoryReason().getText())
                        .date(DateUtils.convertLocalDateToDate(vehicleHistoryEntry.getDate()))
                        .build());
            }
        }
        return vehicleHistoryResponseList;
    }

}
