/**
 * This class defines a model for responses to selling vehicles in TraMS.
 */
export class SellVehicleResponse {

    /**
     * True if and only if the vehicle was sold successfully.
     */
    public sold: boolean;

    /**
     * The amount that the company got for selling the vehicle.
     */
    public soldPrice: number;

}