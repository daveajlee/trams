/**
 * This class defines a model for retrieving service trips from the TraMS server.
 */
export class ServiceTripResponse {

    public serviceId: string;
    public scheduleId: string;
    public stopList: string[];
    public outOfService: boolean;

    // These variables allow a service to either start or stop before normal to reduce delays etc.
    public tempStartStopPos: number;
    public tempEndStopPos: number;

}