export class PositionModel {

    stop: string;

    destination: string;

    delay: number;

    constructor(stop: string, destination: string, delay: number ) {
        this.stop = stop;
        this.destination = destination;
        this.delay = delay;
    }

}