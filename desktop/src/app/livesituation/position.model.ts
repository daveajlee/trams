export class PositionModel {

    private stop: string;

    private destination: string;

    private delay: number;

    constructor(stop: string, destination: string, delay: number ) {
        this.stop = stop;
        this.destination = destination;
        this.delay = delay;
    }

    /**
     * Get the name of the stop where the vehicle currently is.
     * @return the name of the stop as a String.
     */
    getStop(): string {
        return this.stop;
    }

    /**
     * Get the destination where the vehicle is heading to.
     * @return the destination as a String.
     */
    getDestination(): string {
        return this.destination;
    }

    /**
     * Get the delay that the vehicle currently has.
     * @return the delay as a number.
     */
    getDelay(): number {
        return this.delay;
    }

}