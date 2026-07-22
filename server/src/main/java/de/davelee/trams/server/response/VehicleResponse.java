package de.davelee.trams.server.response;

import java.util.List;
import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a response from the server containing details
 * of a single vehicle.
 * @author Dave Lee
 */
public class VehicleResponse {

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
     * The purchase price of the vehicle.
     */
    private double purchasePrice;

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
     * The current status of inspection for this vehicle.
     */
    private String inspectionStatus;

    /**
     * The number of days until the next inspection is due.
     */
    private long nextInspectionDueInDays;

    /**
     * The additional parameters relevant to this vehicle type e.g. registration number for buses are stored as key/value pairs.
     */
    private Map<String, String> additionalTypeInformationMap;

    /**
     * The list of entries in the log history of this vehicle.
     */
    private List<VehicleHistoryResponse> userHistory;

    /**
     * The number of hours that a vehicle was in service on a particular day.
     */
    private Map<String, Integer> timesheet;

    public VehicleResponse() {
    }

    public VehicleResponse(String fleetNumber, String company, String deliveryDate, String inspectionDate, String vehicleType, double purchasePrice, String vehicleStatus, int seatingCapacity, int standingCapacity, String modelName, String livery, String allocatedRoute, String allocatedTour, int delayInMinutes, String inspectionStatus, long nextInspectionDueInDays, Map<String, String> additionalTypeInformationMap, List<VehicleHistoryResponse> userHistory, Map<String, Integer> timesheet) {
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.deliveryDate = deliveryDate;
        this.inspectionDate = inspectionDate;
        this.vehicleType = vehicleType;
        this.purchasePrice = purchasePrice;
        this.vehicleStatus = vehicleStatus;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.modelName = modelName;
        this.livery = livery;
        this.allocatedRoute = allocatedRoute;
        this.allocatedTour = allocatedTour;
        this.delayInMinutes = delayInMinutes;
        this.inspectionStatus = inspectionStatus;
        this.nextInspectionDueInDays = nextInspectionDueInDays;
        this.additionalTypeInformationMap = additionalTypeInformationMap;
        this.userHistory = userHistory;
        this.timesheet = timesheet;
    }

    @Override
    public String toString() {
        return "VehicleResponse{" +
                "fleetNumber='" + fleetNumber + '\'' +
                ", company='" + company + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", inspectionDate='" + inspectionDate + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", vehicleStatus='" + vehicleStatus + '\'' +
                ", seatingCapacity=" + seatingCapacity +
                ", standingCapacity=" + standingCapacity +
                ", modelName='" + modelName + '\'' +
                ", livery='" + livery + '\'' +
                ", allocatedRoute='" + allocatedRoute + '\'' +
                ", allocatedTour='" + allocatedTour + '\'' +
                ", delayInMinutes=" + delayInMinutes +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                ", nextInspectionDueInDays=" + nextInspectionDueInDays +
                ", additionalTypeInformationMap=" + additionalTypeInformationMap +
                ", userHistory=" + userHistory +
                ", timesheet=" + timesheet +
                '}';
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public long getNextInspectionDueInDays() {
        return nextInspectionDueInDays;
    }

    public void setNextInspectionDueInDays(long nextInspectionDueInDays) {
        this.nextInspectionDueInDays = nextInspectionDueInDays;
    }

    public Map<String, String> getAdditionalTypeInformationMap() {
        return additionalTypeInformationMap;
    }

    public void setAdditionalTypeInformationMap(Map<String, String> additionalTypeInformationMap) {
        this.additionalTypeInformationMap = additionalTypeInformationMap;
    }

    public List<VehicleHistoryResponse> getUserHistory() {
        return userHistory;
    }

    public void setUserHistory(List<VehicleHistoryResponse> userHistory) {
        this.userHistory = userHistory;
    }

    public Map<String, Integer> getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Map<String, Integer> timesheet) {
        this.timesheet = timesheet;
    }
}
