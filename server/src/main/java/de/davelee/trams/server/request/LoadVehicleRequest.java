package de.davelee.trams.server.request;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a request to load a single vehicle for a particular
 * company.
 * @author Dave Lee
 */
public class LoadVehicleRequest {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The date that the vehicle was delivered to its current company in the format dd-MM-yyyy.
     */
    private String deliveryDate;

    /**
     * The date that the vehicle last went through an inspection in the format dd-MM-yyyy.
     */
    private String inspectionDate;

    /**
     * The type of this vehicle which is mapped from subclasses as appropriate.
     */
    private String vehicleType;

    /**
     * The current status of the vehicle which is mapped from the Enum.
     */
    private String vehicleStatus;

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
     * The allocated tour for this vehicle.
     */
    private String allocatedTour;

    /**
     * The additional parameters relevant to this vehicle type e.g. registration number for buses are stored as key/value pairs.
     */
    private Map<String, String> additionalTypeInformationMap;

    /**
     * The list of entries in the log history of this vehicle.
     */
    private List<VehicleHistoryRequest> userHistory;

    /**
     * The number of hours that a vehicle was in service on a particular day.
     */
    private Map<String, Integer> timesheet;

    public LoadVehicleRequest() {
    }

    public LoadVehicleRequest(String fleetNumber, String company, String deliveryDate, String inspectionDate, String vehicleType, String vehicleStatus, int seatingCapacity, int standingCapacity, String modelName, String livery, String allocatedTour, Map<String, String> additionalTypeInformationMap, List<VehicleHistoryRequest> userHistory, Map<String, Integer> timesheet) {
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.deliveryDate = deliveryDate;
        this.inspectionDate = inspectionDate;
        this.vehicleType = vehicleType;
        this.vehicleStatus = vehicleStatus;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.modelName = modelName;
        this.livery = livery;
        this.allocatedTour = allocatedTour;
        this.additionalTypeInformationMap = additionalTypeInformationMap;
        this.userHistory = userHistory;
        this.timesheet = timesheet;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(String vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
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

    public String getAllocatedTour() {
        return allocatedTour;
    }

    public void setAllocatedTour(String allocatedTour) {
        this.allocatedTour = allocatedTour;
    }

    public Map<String, String> getAdditionalTypeInformationMap() {
        return additionalTypeInformationMap;
    }

    public void setAdditionalTypeInformationMap(Map<String, String> additionalTypeInformationMap) {
        this.additionalTypeInformationMap = additionalTypeInformationMap;
    }

    public List<VehicleHistoryRequest> getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(List<VehicleHistoryRequest> userHistory) {
        this.userHistory = userHistory;
    }

    public Map<String, Integer> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Map<String, Integer> timesheet) {
        this.timesheet = timesheet;
    }
}
