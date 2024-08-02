import {DriverResponse} from "./driver.response";

/**
 * This class defines a response for retrieving Driverrs in TraMS.
 */
export class DriversResponse {

    //a count of the number of drivers which were found by the server.
    public count: number;

    //an array of all drivers found by the server.
    public driverResponses: DriverResponse[];

}