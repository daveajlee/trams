/**
 * This class defines a model for Vehicle Models in TraMS which consist of things like seating capacity,
 * standing capacity etc.
 */
export class VehicleModel {

    public modelName: string;
    public modelType: string;
    public seatingCapacity: number;
    public standingCapacity: number;
    public value: number;
    public picture: string;

    /**
     * Construct a new VehicleModel object with the supplied data.
     * @param modelName the name of the model
     * @param modelType the type of the model e.g. single decker bus, double decker, tram etc.
     * @param seatingCapacity the seating capacity of this model
     * @param standingCapacity the standing capacity of this model
     * @param value the current selling price of this model.
     * @param picture the current picture to be displayed of this vehicle model
     */
    constructor ( modelName: string, modelType: string, seatingCapacity: number, standingCapacity: number, value: number, picture: string ) {
        this.modelName = modelName;
        this.modelType = modelType;
        this.seatingCapacity = seatingCapacity;
        this.standingCapacity = standingCapacity;
        this.value = value;
        this.picture = picture;
    }

}