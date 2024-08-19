/**
 * This class defines a model for requests to adjust the simulation interval in TraMS.
 */
export class AdjustSimulationIntervalRequest {

    private company: string;
    private simulationInterval: number;

    /**
     * Construct a new request to adjust the simulation interval.
     * @param company the company that we should adjust the difficulty level for.
     * @param simulationInterval the new simulation interval in minutes that should be set.
     */
    constructor(company: string, simulationInterval: number) {
        this.company = company;
        this.simulationInterval = simulationInterval;
    }
}