import {VehicleModel} from "./vehiclemodel.model";

/**
 * This class defines a model for Vehicles which can be supplied as part of a scenario.
 */
export class SuppliedVehicles {

    public vehicleType: string;
    public model: VehicleModel;
    public quantity: number;

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

}
