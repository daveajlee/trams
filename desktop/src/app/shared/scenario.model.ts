import {SuppliedVehicles} from "../vehicles/suppliedvehicle.model";

/**
 * This class defines a scenario which can be played in the TraMS application.
 */
export class Scenario {

    private locationMap: string;
    private scenarioName: string;
    private description: string;
    private targets: string[];
    private minimumSatisfaction: number;
    private suppliedVehicles: SuppliedVehicles[];
    private suppliedDrivers: string[];
    private stopDistances: string[];

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

    /**
     * Get the path to the location map image.
     * @return the path as a String.
     */
    getImagePath(): string {
        return "assets/" + this.locationMap;
    }

    /**
     * Get the name of the scenario.
     * @return the name as a String.
     */
    getScenarioName(): string {
        return this.scenarioName;
    }

    /**
     * Get the distances between stops for this scenario.
     * @return the stop distances as a string array.
     */
    getStopDistances(): string[] {
        return this.stopDistances;
    }

    /**
     * Get the distance between two particular stops.
     * @param stop1 the first stop as a string to measure the distance to the second stop.
     * @param stop2 the second stop as a string to measure the distance from the first stop.
     * @return the distance between the stops in minutes as a number.
     */
    getDistanceBetweenStop (stop1: string, stop2: string): number {
        var stop1Pos: number; var stop2Pos: number;
        for ( var i = 0; i < this.stopDistances.length; i++ ) {
            if ( this.stopDistances[i].split(":")[0] == stop1 ) {
                stop1Pos = i;
            } else if ( this.stopDistances[i].split(":")[0] == stop2 ) {
                stop2Pos = i;
            }
        }
        if ( stop1Pos >= 0 && stop2Pos >= 0 ) {
            return parseInt(this.stopDistances[stop1Pos].split(":")[1].split(",")[stop2Pos]);
        }
    }

    /**
     * Get the description for this scenario.
     * @return the description as a String.
     */
    getDescription(): string {
        return this.description;
    }

    /**
     * Get the targets for this scenario.
     * @return the targets as a String array.
     */
    getTargets(): string[] {
        return this.targets;
    }

    /**
     * Get the supplied vehicles for this scenario.
     * @return the supplied vehicles as an array of SuppliedVehicle objects.
     */
    getSuppliedVehicles(): SuppliedVehicles[] {
        return this.suppliedVehicles;
    }

    /**
     * Get the supplied drivers for this scenario.
     * @return the supplied driver names as an array of Strings.
     */
    getSuppliedDrivers(): string[] {
        return this.suppliedDrivers;
    }

}