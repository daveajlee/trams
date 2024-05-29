/**
 * This class defines a model for Vehicles in TraMS which consist of ?
 */
export class Vehicle {

    public fleetNumber: string;
    public vehicleType: string;
    public livery: string;
    public allocatedTour: string;
    public inspectionStatus: string;
    public nextInspectionDueInDays: number;
    public additionalTypeInformationMap: Map<string, string>;
    public delay: number;

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

}
