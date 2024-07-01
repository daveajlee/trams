/**
 * This class defines a model for responses containing a vehicle in TraMS.
 */
export class VehicleResponse {

    /**
     * The fleet number of this vehicle.
     */
    public fleetNumber: string;

    /**
     * The company that owns this vehicle.
     */
    public company: string;

    /**
     * The date that the vehicle was delivered to its current company in the format dd-MM-yyyy.
     */
    public deliveryDate: string;

    /**
     * The date that the vehicle last went through an inspection in the format dd-MM-yyyy.
     */
    public inspectionDate: string;

    /**
     * The type of this vehicle which is mapped from subclasses as appropriate.
     */
    public vehicleType: string;

    /**
     * The purchase price of the vehicle.
     */
    public purchasePrice: string;

    /**
     * The current status of the vehicle which is mapped from the Enum.
     */
    public vehicleStatus: string;

    /**
     * The number of seats that this vehicle has.
     */
    public seatingCapacity: number;

    /**
     * The number of persons who are allowed to stand in this vehicle.
     */
    public standingCapacity: number;

    /**
     * The name of the model of this vehicle.
     */
    public modelName: string;

    /**
     * The livery that this vehicle has.
     */
    public livery: string;

    /**
     * The allocated route for this vehicle.
     */
    public allocatedRoute: string;

    /**
     * The allocated tour for this vehicle.
     */
    public allocatedTour: string;

    /**
     * The current delay of this vehicle in minutes.
     */
    public delayInMinutes: number;

    /**
     * The current status of inspection for this vehicle.
     */
    public inspectionStatus: string;

    /**
     * The number of days until the next inspection is due.
     */
    public nextInspectionDueInDays: number;

}
