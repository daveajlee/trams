/**
 * This class defines a model for Vehicles in TraMS which consist of ?
 */
export class Vehicle {

    private fleetNumber: string;
    private vehicleType: string;
    private livery: string;
    private allocatedTour: string;
    private inspectionStatus: string;
    private nextInspectionDueInDays: number;
    private additionalTypeInformationMap: Map<string, string>;
    private delay: number;

    /**
     * Construct a new model for Vehicles which contains the supplied data.
     * @param fleetNumber the fleet number of this vehicle
     * @param vehicleType the type of this vehicle which can usually be bus, train or tram
     * @param livery the colour schema of this vehicle
     * @param allocatedTour the tour allocated to this vehicle which may be null if no tour has been allocated
     * @param inspectionStatus whether or not the vehicle is due an inspection soon
     * @param nextInspectionDueInDays the number of days until the next inspection
     * @param additionalTypeInformationMap stores the field key/value pair for any additional information of this vehicle.
     */
    constructor( fleetNumber: string, vehicleType: string, livery: string, allocatedTour: string, inspectionStatus: string,
                 nextInspectionDueInDays: number, additionalTypeInformationMap: Map<string, string>) {
        this.fleetNumber = fleetNumber;
        this.vehicleType = vehicleType;
        this.livery = livery;
        this.allocatedTour = allocatedTour;
        this.inspectionStatus = inspectionStatus;
        this.nextInspectionDueInDays = nextInspectionDueInDays;
        this.additionalTypeInformationMap = additionalTypeInformationMap;
        this.delay = 0;
    }

    /**
     * Retrieve the allocated tour for this vehicle.
     * @return the allocated tour as a string in format route/schedule id.
     */
    getAllocatedTour(): string {
        return this.allocatedTour;
    }

    /**
     * Set the allocated tour for this vehicle.
     * @param allocatedTour the allocated tour as a string in format route/schedule id
     */
    setAllocatedTour( allocatedTour: string ) {
        this.allocatedTour = allocatedTour;
    }

    /**
     * Retrieve the fleet number of this vehicle.
     * @return the fleet number as a string.
     */
    getFleetNumber(): string {
        return this.fleetNumber;
    }

    /**
     * Retrieve the delay of this vehicle in minutes.
     * @return the delay of this vehicle in minutes as a number.
     */
    getDelay(): number {
        return this.delay;
    }

    /**
     * Adjust the delay of this vehicle. Minus reduces delay, plus increases delay.
     * @param adjustment the number of minutes to increase (positive) or decrease (negative)
     */
    adjustDelay(adjustment: number): void {
        // Adjust the delay - adding a minus number subtracts.
        this.delay += adjustment;
        // If delay is below 0, then set to 0.
        if ( this.delay < 0 ) {
            this.delay = 0;
        }
    }

    /**
     * Retrieve the value of this vehicle if specified.
     * @return the value of this vehicle if specified as a number.
     */
    getValue(): number {
        if ( this.additionalTypeInformationMap.get('Value') ) {
            return parseFloat(this.additionalTypeInformationMap.get('Value'));
        }
        return 0;
    }

    /**
     * Retrieve the power mode of this vehicle if specified.
     * @return the power mode of this vehicle if specified.
     */
    getPowerMode(): string {
        return this.additionalTypeInformationMap.get('Power Mode');
    }

    /**
     * Retrieve the type of the vehicle such as bus, train or tram.
     * @return the type of the vehicle as a string.
     */
    getVehicleType(): string {
        return this.vehicleType;
    }

    /**
     * Retrieve the livery of this vehicle.
     * @return the livery as a string,
     */
    getLivery(): string {
        return this.livery;
    }

    /**
     * Retrieve the additional information for types map.
     * @return the additional information as a Map object.
     */
    getAdditionalInformation(): Map<string, string> {
        return this.additionalTypeInformationMap;
    }

    /**
     * Retrieve the inspection status of this vehicle.
     * @return the inspection status as a String.
     */
    getInspectionStatus(): string {
        return this.inspectionStatus;
    }

    /**
     * Retrieve the number of days until this vehicle next needs an inspection.
     * @return the number of days as a number.
     */
    getDaysUntilNextInspection(): number {
        return this.nextInspectionDueInDays;
    }

}
