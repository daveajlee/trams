/**
 * This class defines a scenario which can be played in the TraMS application.
 */
export class Scenario {

    public locationMap: string;
    public scenarioName: string;
    public description: string;
    public targets: string[];

    /**
     * Construct a new scenario which contains the supplied data.
     * @param locationMap the path to the image containing the map of the town or city
     * @param scenarioName the name of the scenario
     * @param description the description of the scenario
     * @param targets the targets that have to be reached when playing this scenario
     */

    constructor ( locationMap: string, scenarioName: string, description: string, targets: string[] ) {
        this.locationMap = locationMap;
        this.scenarioName = scenarioName;
        this.description = description;
        this.targets = targets;
    }

    getImagePath(): string {
        return "assets/" + this.locationMap;
    }

}