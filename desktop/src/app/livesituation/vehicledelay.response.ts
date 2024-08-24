/**
 * This class defines a model for retrieving the delay of a vehicle from the TraMS server.
 */
export class VehicleDelayResponse {

    //company that owns the vehicle
    company: string;

    //fleet number of the vehicle
    fleetNumber: string;

    //delay of the vehicle in minutes (must be 0 or greater)
    delayInMinutes: number;

}