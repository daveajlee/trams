/**
 * This class defines a response for Vehicles in TraMS which consist of a count and a Vehicles array.
 */
import {Vehicle} from './vehicle.model';

export class VehiclesResponse {

    public count: number;
    public vehicleResponses: Vehicle[];

}
