/**
 * This class defines a response for Vehicles in TraMS which consist of a count and a Vehicles array.
 */
import {Vehicle} from './vehicle.model';

export class VehiclesResponse {

    private count: number;
    private vehicleResponses: Vehicle[];

    /**
     * Retrieve the vehicles contained in the response.
     * @return an array of Vehicle objects.
     */
    getVehicleResponses(): Vehicle[] {
        return this.vehicleResponses;
    }

}
