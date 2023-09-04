/**
 * This class defines a model for Vehicle Models in TraMS which consist of things like seating capacity,
 * standing capacity etc.
 */
export class VehicleModel {

    public modelName: string;
    public seatingCapacity: number;
    public standingCapacity: number;
    public value: number;

    /**
     * Construct a new VehicleModel object with the supplied data.
     * @param modelName the name of the model
     * @param seatingCapacity the seating capacity of this model
     * @param standingCapacity the standing capacity of this model
     * @param value the current selling price of this model.
     */
    constructor ( modelName: string, seatingCapacity: number, standingCapacity: number, value: number ) {
        this.modelName = modelName;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.value = value;
    }

}