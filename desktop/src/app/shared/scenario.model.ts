import {SuppliedVehicles} from "../vehicles/suppliedvehicle.model";

/**
 * This class defines a scenario which can be played in the TraMS application.
 */
export class Scenario {

    public locationMap: string;
    public scenarioName: string;
    public description: string;
    public targets: string[];
    public minimumSatisfaction: number;
    public suppliedVehicles: SuppliedVehicles[];
    public suppliedDrivers: string[];
    public stopDistances: string[];

    /**
     * Construct a new scenario which contains the supplied data.
     * @param locationMap the path to the image containing the map of the town or city
     * @param scenarioName the name of the scenario
     * @param description the description of the scenario
     * @param targets the targets that have to be reached when playing this scenario
     * @param minimumSatisfaction the minimum satisfaction rating needed for this scenario.
     * @param suppliedVehicles the type and number of supplied vehicles.
     * @param suppliedDrivers the supplied drivers for this scenario.
     * @param stopDistances the names and distances of stops of this scenario.
     */

    constructor ( locationMap: string, scenarioName: string, description: string, targets: string[],
                  minimumSatisfaction: number, suppliedVehicles: SuppliedVehicles[], suppliedDrivers: string[],
                  stopDistances: string[]) {
        this.locationMap = locationMap;
        this.scenarioName = scenarioName;
        this.description = description;
        this.targets = targets;
        this.minimumSatisfaction = minimumSatisfaction;
        this.suppliedVehicles = suppliedVehicles;
        this.suppliedDrivers = suppliedDrivers;
        this.stopDistances = stopDistances;
    }

    getImagePath(): string {
        return "assets/" + this.locationMap;
    }

}