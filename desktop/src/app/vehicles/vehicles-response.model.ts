/**
 * This class defines a response for Vehicles in TraMS which consist of a count and a Vehicles array.
 */
import {VehicleResponse} from "./vehicle.response";

export class VehiclesResponse {

    public count: number;
    public vehicleResponses: VehicleResponse[];

}
