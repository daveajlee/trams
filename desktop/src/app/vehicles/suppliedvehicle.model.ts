import {VehicleModel} from "./vehiclemodel.model";

/**
 * This class defines a model for Vehicles which can be supplied as part of a scenario.
 */
export class SuppliedVehicles {

    private vehicleType: string;
    private model: VehicleModel;
    private quantity: number;

    /**
     * Construct a new model which contains the supplied data.
     * @param vehicleType the type of this vehicle which can usually be bus, train or tram
     * @param model the model of the supplied vehicle
     * @param quantity the quantity/amount of this model and type that are supplied
     */
    constructor( vehicleType: string, model: VehicleModel, quantity: number) {
        this.vehicleType = vehicleType;
        this.model = model;
        this.quantity = quantity;
    }

    /**
     * Retrieve the type of the vehicle which is usually bus, train or tram.
     * @return the vehicle type as a string.
     */
    getVehicleType(): string {
        return this.vehicleType;
    }

    /**
     * Retrieve the model of the vehicle.
     * @return the vehicle model as a Vehicle Model object.
     */
    getModel(): VehicleModel {
        return this.model;
    }

    /**
     * Retrieve the amount of vehicles that are supplied.
     * @return the quantity as a number.
     */
    getQuantity(): number {
        return this.quantity;
    }

}
