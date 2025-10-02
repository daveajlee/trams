class Route {

    number: string;
    outwardTerminus: string;
    returnTerminus: string;
    numberTours: number;

    constructor(number: string, outwardTerminus: string, returnTerminus: string, numberTours: number) {
        this.number = number;
        this.outwardTerminus = outwardTerminus;
        this.returnTerminus = returnTerminus;
        this.numberTours = numberTours;
    }
}

export default Route;