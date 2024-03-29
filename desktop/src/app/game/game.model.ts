import {Scenario} from "../shared/scenario.model";
import {Route} from "../routes/route.model";
import {Message} from "../messages/message.model";
import {Vehicle} from "../vehicles/vehicle.model";
import {Driver} from "../drivers/driver.model";
import {Allocation} from "../allocations/allocation.model";

/**
 * This class defines a model for the game that is currently loaded in the TraMS application.
 */
export class Game {

    public companyName: string;
    public balance: number;
    public playerName: string;
    public currentDateTime: Date;
    public scenario: Scenario;
    public difficultyLevel: string;
    public passengerSatisfaction: number;
    public routes: Route[];
    public messages: Message[];
    public vehicles: Vehicle[];
    public drivers: Driver[];
    public allocations: Allocation[];

    /**
     * Construct a new game which contains the supplied data.
     * @param companyName the name of the company
     * @param playerName the player name who will run the company
     * @param startingDateTime the starting date and time for playing this company
     * @param scenario the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     */

    constructor( companyName: string, playerName: string, startingDateTime: Date, scenario: Scenario,
                 difficultyLevel: string ) {
        this.companyName = companyName;
        this.balance = 200000.0;
        this.playerName = playerName;
        this.currentDateTime = startingDateTime;
        this.scenario = scenario;
        this.difficultyLevel = difficultyLevel;
        this.passengerSatisfaction = 90;
        this.routes = [];
        this.messages = [];
        this.vehicles = [];
        this.drivers = [];
        this.allocations = [];
    }

    /**
     * This method adds a route to the routes array if we are currently saving routes locally.
     * @param route a route object with the route information to add to the routes array.
     */
    addRoute(route: Route): void {
        this.routes.push(route);
    }

    /**
     * This method adds a vehicle to the vehicles array if we are currently saving vehicles locally.
     * @param vehicle a vehicle object with the vehicle information to add to the vehicles array.
     */
    addVehicle(vehicle: Vehicle): void {
        this.vehicles.push(vehicle);
    }

    /**
     * This method adds a driver to the drivers array if we are currently saving drivers locally.
     * @param driver a driver object with the driver information to add to the drivers array.
     */
    addDriver(driver: Driver): void {
        this.drivers.push(driver);
    }

    /**
     * This method adds an allocation to the allocations array if we are currently saving allocations locally.
     * @param allocation a allocation object with the allocation information to add to the allocations array.
     */
    addAllocation(allocation: Allocation): void {
        this.allocations.push(allocation);
    }

    /**
     * This method adds a message to the messages array if we are currently saving messages locally.
     * @param subject the subject of the message to add.
     * @param content the content of the message to add.
     * @param folder the folder of the message to add.
     */
    addMessage(subject: string, content: string, folder: string ): void {
        this.messages.push(new Message(subject, content, folder));
    }

    /**
     * This method returns the route object corresponding to the route number or null if none was found.
     * @param routeNumber the route number to retrieve the route object for.
     * @returns the route object or null if no route object matching the route number was found,
     */
    getRoute( routeNumber: string ): Route {
        for ( var i = 0; i < this.routes.length; i++ ) {
            if ( this.routes[i].routeNumber === routeNumber ) {
                return this.routes[i];
            }
        }
        return null;
    }

}
