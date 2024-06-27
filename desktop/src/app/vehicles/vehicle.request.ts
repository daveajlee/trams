import {AdditionalTypeInformation} from "./additionalTypeInfo.model";

/**
 * This class defines a model for requests to purchase vehicles in TraMS.
 */
export class VehicleRequest {

    private fleetNumber: string;
    private company: string;
    private vehicleType: string;
    private livery: string;
    private additionalTypeInformationMap: AdditionalTypeInformation;
    private seatingCapacity: string;
    private standingCapacity: string;
    private modelName: string;

    /**
     * Construct a new vehicle request which contains the supplied data.
     * @param fleetNumber the fleet number of the vehicle.
     * @param company the name of the company purchasing the vehicle.
     * @param vehicleType the type of this vehicle which can usually be bus, train or tram
     * @param livery the colour schema of this vehicle
     * @param additionalTypeInformationMap stores the field key/value pair for any additional information of this vehicle.
     * @param seatingCapacity the seating capacity of this vehicle
     * @param standingCapacity the standing capacity of this vehicle
     * @param modelName the name of the model of the vehicle
     */

    constructor( fleetNumber: string, company: string, vehicleType: string, livery: string, additionalTypeInformationMap: AdditionalTypeInformation,
                 seatingCapacity: string, standingCapacity: string, modelName: string ) {
        this.fleetNumber = fleetNumber;
        this.company = company;
        this.vehicleType = vehicleType;
        this.livery = livery;
        this.additionalTypeInformationMap = additionalTypeInformationMap;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.modelName = modelName;
    }

}