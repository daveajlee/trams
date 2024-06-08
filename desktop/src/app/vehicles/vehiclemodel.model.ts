/**
 * This class defines a model for Vehicle Models in TraMS which consist of things like seating capacity,
 * standing capacity etc.
 */
export class VehicleModel {

    private modelName: string;
    private modelType: string;
    private seatingCapacity: number;
    private standingCapacity: number;
    private value: number;
    private picture: string;

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

    /**
     * Retrieve the name of the model.
     * @return the model name as a string.
     */
    getModelName(): string {
        return this.modelName;
    }

    /**
     * Retrieve the standing capacity of this model.
     * @return the standing capacity as a number.
     */
    getStandingCapacity(): number {
        return this.standingCapacity;
    }

    /**
     * Retrieve the seating capacity of this model.
     * @return the seating capacity as a number.
     */
    getSeatingCapacity(): number {
        return this.seatingCapacity;
    }

    /**
     * Get the value (price) of this model.
     * @return the value as a number.
     */
    getValue(): number {
        return this.value;
    }

    /**
     * Get the type of this model.
     * @return the type of the model as a string.
     */
    getModelType(): string {
        return this.modelType;
    }

    /**
     * Retrieve the picture of this model.
     * @return the picture of this model as a string.
     */
    getPicture(): string {
        return this.picture;
    }

}