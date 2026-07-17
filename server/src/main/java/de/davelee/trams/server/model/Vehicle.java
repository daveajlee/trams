package de.davelee.trams.server.model;

import de.davelee.trams.server.constant.VehicleHistoryReason;
import de.davelee.trams.server.constant.VehicleStatus;
import de.davelee.trams.server.constant.VehicleType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents a vehicle. A vehicle can contain a fleet number and company,
 * a delivery date, an inspection date, a seating capacity, a standing capacity, a model name,
 * a livery and a status.
 * @author Dave Lee
 */
public class Vehicle {

    /**
     * The id of the vehicle in the database.
     */
    private String id;

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The date that the vehicle was delivered to its current company.
     */
    private LocalDateTime deliveryDate;

    /**
     * The date that the vehicle last went through an inspection.
     */
    private LocalDateTime inspectionDate;

    /**
     * The number of seats that this vehicle has.
     */
    private int seatingCapacity;

    /**
     * The number of persons who are allowed to stand in this vehicle.
     */
    private int standingCapacity;

    /**
     * The name of the model of this vehicle.
     */
    private String modelName;

    /**
     * The livery that this vehicle has.
     */
    private String livery;

    /**
     * The current status of the vehicle.
     */
    private VehicleStatus vehicleStatus;

    /**
     * The allocated route for this vehicle.
     */
    private String allocatedRoute;

    /**
     * The allocated tour for this vehicle.
     */
    private String allocatedTour;

    /**
     * The current delay of this vehicle in minutes.
     */
    private int delayInMinutes;

    /**
     * The type of this vehicle.
     */
    private VehicleType vehicleType;

    /**
     * Map of type specific infos as key/value pair where additional information can be stored.
     */
    private Map<String, String> typeSpecificInfos;

    /**
     * The number of hours that a vehicle was in service on a particular day.
     */
    private Map<LocalDateTime, Integer> timesheet;

    /**
     * A log of entries representing the history of this vehicle whilst working for this company.
     */
    private List<VehicleHistoryEntry> vehicleHistoryEntryList;

    /**
     * Add a number of hours for a particular day to the timesheet.
     * @param hours a <code>int</code> with the number of hours to add.
     * @param date a <code>LocalDateTime</code> object containing the day to add the hours to.
     */
    public void addHoursForDate ( final int hours, final LocalDateTime date ) {
        //If the date already exists then add the hours to the hours already there.
        if ( timesheet.get(date) != null ) {
            timesheet.put(date, timesheet.get(date).intValue() + hours);
        } else {
            //If no hours are present then just add it as first entry.
            timesheet.put(date, hours);
        }
    }

    /**
     * Retrieve the number of hours that the vehicle has been in service on a particular day.
     * @param date a <code>LocalDateTime</code> object containing the day to retrieve hours for.
     * @return a <code>int</code> with the number of hours.
     */
    public int getHoursForDate ( final LocalDateTime date ) {
        //If the date is null then return 0.
        if ( timesheet.get(date) == null ) {
            return 0;
        }
        //Otherwise return the number of hours.
        return timesheet.get(date);
    }

    /**
     * Add a new history entry to the list.
     * @param date a <code>LocalDateTime</code> containing the date that the entry/event took place.
     * @param vehicleHistoryReason a <code>VehicleHistoryReason</code> containing the reason that the entry/event took place.
     * @param comment a <code>String</code> containing the comment about the entry/event.
     */
    public void addVehicleHistoryEntry (final LocalDateTime date, final VehicleHistoryReason vehicleHistoryReason, final String comment ) {
        if (vehicleHistoryEntryList == null) {
            vehicleHistoryEntryList = new ArrayList<>();
        }
        VehicleHistoryEntry vehicleHistoryEntry = new VehicleHistoryEntry();
        vehicleHistoryEntry.setDate(date);
        vehicleHistoryEntry.setVehicleHistoryReason(vehicleHistoryReason);
        vehicleHistoryEntry.setComment(comment);
        vehicleHistoryEntryList.add(vehicleHistoryEntry);
    }

    public Vehicle(String id, String fleetNumber, String company, LocalDateTime deliveryDate, LocalDateTime inspectionDate, int seatingCapacity, int standingCapacity, String modelName, String livery, VehicleStatus vehicleStatus, String allocatedRoute, String allocatedTour, int delayInMinutes, VehicleType vehicleType, Map<String, String> typeSpecificInfos, Map<LocalDateTime, Integer> timesheet, List<VehicleHistoryEntry> vehicleHistoryEntryList) {
        this.id = id;
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.deliveryDate = deliveryDate;
        this.inspectionDate = inspectionDate;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.modelName = modelName;
        this.livery = livery;
        this.vehicleStatus = vehicleStatus;
        this.allocatedRoute = allocatedRoute;
        this.allocatedTour = allocatedTour;
        this.delayInMinutes = delayInMinutes;
        this.vehicleType = vehicleType;
        this.typeSpecificInfos = typeSpecificInfos;
        this.timesheet = timesheet;
        this.vehicleHistoryEntryList = vehicleHistoryEntryList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFleetNumber() {
        return fleetNumber;
    }

    public void setFleetNumber(String fleetNumber) {
        this.fleetNumber = fleetNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(LocalDateTime inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public int getStandingCapacity() {
        return standingCapacity;
    }

    public void setStandingCapacity(int standingCapacity) {
        this.standingCapacity = standingCapacity;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getLivery() {
        return livery;
    }

    public void setLivery(String livery) {
        this.livery = livery;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public String getAllocatedRoute() {
        return allocatedRoute;
    }

    public void setAllocatedRoute(String allocatedRoute) {
        this.allocatedRoute = allocatedRoute;
    }

    public String getAllocatedTour() {
        return allocatedTour;
    }

    public void setAllocatedTour(String allocatedTour) {
        this.allocatedTour = allocatedTour;
    }

    public int getDelayInMinutes() {
        return delayInMinutes;
    }

    public void setDelayInMinutes(int delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Map<String, String> getTypeSpecificInfos() {
        return typeSpecificInfos;
    }

    public void setTypeSpecificInfos(Map<String, String> typeSpecificInfos) {
        this.typeSpecificInfos = typeSpecificInfos;
    }

    public Map<LocalDateTime, Integer> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Map<LocalDateTime, Integer> timesheet) {
        this.timesheet = timesheet;
    }

    public List<VehicleHistoryEntry> getVehicleHistoryEntryList() {
        return vehicleHistoryEntryList;
    }

    public void setVehicleHistoryEntryList(List<VehicleHistoryEntry> vehicleHistoryEntryList) {
        this.vehicleHistoryEntryList = vehicleHistoryEntryList;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", fleetNumber='" + fleetNumber + '\'' +
                ", company='" + company + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", inspectionDate=" + inspectionDate +
                ", seatingCapacity=" + seatingCapacity +
                ", standingCapacity=" + standingCapacity +
                ", modelName='" + modelName + '\'' +
                ", livery='" + livery + '\'' +
                ", vehicleStatus=" + vehicleStatus +
                ", allocatedRoute='" + allocatedRoute + '\'' +
                ", allocatedTour='" + allocatedTour + '\'' +
                ", delayInMinutes=" + delayInMinutes +
                ", vehicleType=" + vehicleType +
                ", typeSpecificInfos=" + typeSpecificInfos +
                ", timesheet=" + timesheet +
                ", vehicleHistoryEntryList=" + vehicleHistoryEntryList +
                '}';
    }
}
