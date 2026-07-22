package de.davelee.trams.server.request;

import java.util.Map;

/**
 * This class is part of the TraMS Server REST API. It represents a request to purchase a vehicle for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
public class PurchaseVehicleRequest {

    /**
     * The fleet number of this vehicle.
     */
    private String fleetNumber;

    /**
     * The company that owns this vehicle.
     */
    private String company;

    /**
     * The type of this vehicle which is mapped from subclasses as appropriate.
     */
    private String vehicleType;

    /**
     * The livery that this vehicle has.
     */
    private String livery;

    /**
     * The additional parameters relevant to this vehicle type e.g. registration number for buses are stored as key/value pairs.
     */
    private Map<String, String> additionalTypeInformationMap;

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

    public PurchaseVehicleRequest() {
    }

    public PurchaseVehicleRequest(String fleetNumber, String company, String vehicleType, String livery, Map<String, String> additionalTypeInformationMap, int seatingCapacity, int standingCapacity, String modelName) {
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.vehicleType = vehicleType;
        this.livery = livery;
        this.additionalTypeInformationMap = additionalTypeInformationMap;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.modelName = modelName;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLivery() {
        return livery;
    }

    public void setLivery(String livery) {
        this.livery = livery;
    }

    public Map<String, String> getAdditionalTypeInformationMap() {
        return additionalTypeInformationMap;
    }

    public void setAdditionalTypeInformationMap(Map<String, String> additionalTypeInformationMap) {
        this.additionalTypeInformationMap = additionalTypeInformationMap;
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

    @Override
    public String toString() {
        return "PurchaseVehicleRequest{" +
                "fleetNumber='" + fleetNumber + '\'' +
                ", company='" + company + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", livery='" + livery + '\'' +
                ", additionalTypeInformationMap=" + additionalTypeInformationMap +
                ", seatingCapacity=" + seatingCapacity +
                ", standingCapacity=" + standingCapacity +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
