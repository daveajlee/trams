package de.davelee.trams.server.utils;

import de.davelee.trams.server.constant.InspectionStatus;
import de.davelee.trams.server.constant.VehicleHistoryReason;
import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import de.davelee.trams.server.model.*;
import de.davelee.trams.server.request.LoadVehicleRequest;
import de.davelee.trams.server.request.VehicleHistoryRequest;
import de.davelee.trams.server.response.VehicleHistoryResponse;
import de.davelee.trams.server.response.VehicleResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * This class provides utility methods for processing related to /vehicle and /vehicles endpoints in the TraMS Server.
 * @author Dave Lee
 */
public class VehicleUtils {

    /**
     * This is a private helper method to calculate the inspection status of a vehicle and how many days until the next
     * inspection is due based on the last inspection date.
     * @param vehicleResponse a <code>VehicleResponse</code> object to write the results of the calculations in.
     * @param inspectionDate a <code>LocalDateTime</code> containing the date of the last inspection range
     * @param inspectionPeriod a <code>int</code> containing the number of years within which an inspection must take place
     */
    public static void processInspectionDate (final VehicleResponse vehicleResponse, final LocalDateTime inspectionDate,
                                        final int inspectionPeriod ) {
        if ( inspectionDate != null && inspectionDate.isAfter(LocalDateTime.now().minusYears(inspectionPeriod)) ) {
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
    public static List<VehicleHistoryResponse> convertHistoryEntriesToResponse (final List<VehicleHistoryEntry> vehicleHistoryEntryList) {
        List<VehicleHistoryResponse> vehicleHistoryResponseList = new ArrayList<>();
        if ( vehicleHistoryEntryList != null ) {
            for (VehicleHistoryEntry vehicleHistoryEntry : vehicleHistoryEntryList) {
                vehicleHistoryResponseList.add(VehicleHistoryResponse.builder()
                        .comment(vehicleHistoryEntry.getComment())
                        .vehicleHistoryReason(vehicleHistoryEntry.getVehicleHistoryReason().getText())
                        .date(DateUtils.convertLocalDateTimeToDate(vehicleHistoryEntry.getDate()))
                        .build());
            }
        }
        return vehicleHistoryResponseList;
    }

    /**
     * This is a private helper method to convert history entries into a suitable response object.
     * @param vehicleHistoryRequestList a <code>VehicleHistoryRequest</code> List which should be converted
     * @return a <code>VehicleHistoryEntry</code> List which has been converted.
     */
    public static List<VehicleHistoryEntry> convertHistoryRequestToEntries (final List<VehicleHistoryRequest> vehicleHistoryRequestList) {
        List<VehicleHistoryEntry> vehicleHistoryEntryList = new ArrayList<>();
        if ( vehicleHistoryRequestList != null ) {
            for (VehicleHistoryRequest vehicleHistoryRequest : vehicleHistoryRequestList) {
                vehicleHistoryEntryList.add(VehicleHistoryEntry.builder()
                        .comment(vehicleHistoryRequest.getComment())
                        .vehicleHistoryReason(VehicleHistoryReason.valueOf(vehicleHistoryRequest.getVehicleHistoryReason()))
                        .date(DateUtils.convertDateToLocalDateTime(vehicleHistoryRequest.getDate()))
                        .build());
            }
        }
        return vehicleHistoryEntryList;
    }

    /**
     * This is a private helper method to convert a timesheet map into a map suitable for a response.
     * @param timesheet a <code>Map</code> of <code>LocalDate</code> and <code>Integer</code> List which should be converted.
     * @return a <code>Map</code> of <code>String</code> and <code>Integer</code> which has been converted.
     */
    public static Map<String, Integer> convertTimesheetToResponse (final Map<LocalDateTime, Integer> timesheet) {
        Map<String, Integer> timesheetResponse = new HashMap<>();
        if ( timesheet == null ) {
            return Map.of();
        }
        Iterator<LocalDateTime> keySetIterator = timesheet.keySet().iterator();
            while(keySetIterator.hasNext()) {
                LocalDateTime next = keySetIterator.next();
                timesheetResponse.put(DateUtils.convertLocalDateTimeToDate(next), timesheet.get(next));
        }
        return timesheetResponse;
    }

    /**
     * This is a private helper method to convert a timesheet request into a timesheet map for the database.
     * @param timesheetRequest a <code>Map</code> of <code>String</code> and <code>Integer</code> which should be converted.
     * @return a <code>Map</code> of <code>LocalDateTime</code> and <code>Integer</code> List which has been converted.
     */
    public static Map<LocalDateTime, Integer> convertRequestToTimesheet (final Map<String, Integer> timesheetRequest) {
        Map<LocalDateTime, Integer> timesheet = new HashMap<>();
        Iterator<String> keySetIterator = timesheetRequest.keySet().iterator();
        while(keySetIterator.hasNext()) {
            String next = keySetIterator.next();
            timesheet.put(DateUtils.convertDateToLocalDateTime(next), timesheet.get(next));
        }
        return timesheet;
    }

    /**
     * This is a private helper method to convert a <code>LoadVehicleRequest</code> object into a <code>Vehicle</code>
     * object for saving in the database.
     * @param loadVehicleRequest a <code>LoadVehicleRequest</code> object to convert.
     * @return a <code>Vehicle</code> object which has been converted.
     */
    public static Vehicle convertToVehicle(final LoadVehicleRequest loadVehicleRequest) {
        return Vehicle.builder()
                .modelName(loadVehicleRequest.getModelName())
                .standingCapacity(loadVehicleRequest.getStandingCapacity())
                .seatingCapacity(loadVehicleRequest.getSeatingCapacity())
                .vehicleType(VehicleType.getVehicleTypeFromName(loadVehicleRequest.getVehicleType()))
                .typeSpecificInfos(loadVehicleRequest.getAdditionalTypeInformationMap())
                .livery(loadVehicleRequest.getLivery())
                .fleetNumber(loadVehicleRequest.getFleetNumber())
                .deliveryDate(DateUtils.convertDateToLocalDateTime(loadVehicleRequest.getDeliveryDate()))
                .company(loadVehicleRequest.getCompany())
                .inspectionDate(loadVehicleRequest.getInspectionDate() != null ? DateUtils.convertDateToLocalDateTime(loadVehicleRequest.getInspectionDate()) : null)
                .vehicleStatus(loadVehicleRequest.getVehicleStatus() != null ? VehicleStatus.valueOf(loadVehicleRequest.getVehicleStatus()) : null)
                .allocatedTour(loadVehicleRequest.getAllocatedTour())
                .vehicleHistoryEntryList(VehicleUtils.convertHistoryRequestToEntries(loadVehicleRequest.getUserHistory()))
                .timesheet(VehicleUtils.convertRequestToTimesheet(loadVehicleRequest.getTimesheet()))
                .build();
    }

}
