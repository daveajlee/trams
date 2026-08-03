class Route {

    number: string;
    outwardTerminus: string;
    returnTerminus: string;
    numberTours: number;
    stopList: string[];

    constructor(number: string, outwardTerminus: string, returnTerminus: string, numberTours: number, stopList: string[]) {
        this.number = number;
        this.outwardTerminus = outwardTerminus;
        this.returnTerminus = returnTerminus;
        this.numberTours = numberTours;
        this.stopList = stopList;
    }
}

export default Route;