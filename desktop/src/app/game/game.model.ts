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
    private balance: number;
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
    private simulationInterval: number;

    /**
     * Construct a new game which contains the supplied data.
     * @param companyName the name of the company
     * @param playerName the player name who will run the company
     * @param startingDateTime the starting date and time for playing this company
     * @param scenario the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     * @param balance the balance of the company when creating or loading the company.
     * @param passengerSatisfaction the passenger satisfaction value when creating or loading the company.
     * @param routes an array of routes which have already been created (can be an empty array if none exist).
     * @param messages an array of messages which have already been created (can be an empty array if none exist).
     * @param vehicles an array of vehicles which have already been created (can be an empty array if none exist).
     * @param drivers an array of drivers which have already been created (can be an empty array if none exist).
     * @param allocations an array of allocations which have already been created (can be an empty array if none exist).
     */

    constructor( companyName: string, playerName: string, startingDateTime: Date, scenario: Scenario,
                 difficultyLevel: string, balance: number, passengerSatisfaction: number,
                 routes: [], messages: [], vehicles: [], drivers: [], allocations: []) {
        this.companyName = companyName;
        this.balance = balance;
        this.playerName = playerName;
        this.currentDateTime = startingDateTime;
        this.scenario = scenario;
        this.difficultyLevel = difficultyLevel;
        this.passengerSatisfaction = passengerSatisfaction;
        this.routes = routes;
        this.messages = messages;
        this.vehicles = vehicles;
        this.drivers = drivers;
        this.allocations = allocations;
        this.simulationInterval = 100;
    }

    /**
     * This method adds a route to the routes array if we are currently saving routes locally.
     * @param route a route object with the route information to add to the routes array.
     */
    addRoute(route: Route): void {
        this.routes.push(route);
    }

    /**
     * This method deletes the route supplied with the route number.
     * @param routeNumber the route number as a string to be deleted.
     */
    deleteRoute(routeNumber: string): boolean {
        let counter = 0;
        this.routes.forEach((element) => {
            if ( element.routeNumber == routeNumber ) {
                this.routes.splice(counter, 1);
                return true;
            }
            counter++;
        } );
        return false;
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
     * @param date the date of the message to add.
     * @param isSender a boolean which is true iff it is the sender of the message in the name field.
     * @param name the name of the sender or recipient depending on the boolean variable above.
     */
    addMessage(subject: string, content: string, folder: string, date: Date, isSender: boolean, name: string ): void {
        let message = new Message(subject, content, folder, date);
        if ( isSender ) {
            message.sender = name;
        } else {
            message.recipient = name;
        }
        this.messages.push(message);
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

    /**
     * Retrieve the current balance.
     * This method ensures that no method can manipulate the balance without permission.
     * @return the current balance as a number,
     */
    getBalance(): number {
        return this.balance;
    }

    /**
     * Withdraw the supplied number from the balance.
     * A negative number will be ignored.
     * @param amount the amount to withdraw from the balance.
     */
    withdrawBalance(amount: number) {
        if ( amount > 0 && amount < this.balance ) {
            this.balance -= amount;
        } else if ( amount >= this.balance ) {
            this.balance = 0;
        } else {
            console.log('Attempted to withdraw a negative number from the balance: ' + amount);
        }
    }

    /**
     * Credit the balance with the supplied amount.
     * A negative number will be ignored.
     * @param amount the amount to credit to the balance.
     */
    creditBalance(amount: number) {
        if ( amount > 0 ) {
            this.balance += amount;
        }
    }

    /**
     * Get the simulation interval.
     * @return the simulation interval in minutes.
     */
    getSimulationInterval(): number {
        return this.simulationInterval;
    }

    /**
     * Set the simulation interval which should be higher than 0 and less than 120 (i.e. between 1 minute and 2 hours),
     * @param simulationInterval the simulation interval in minutes.
     * @return a boolean which is true iff the simulation interval was set successfully.
     */
    setSimulationInterval(simulationInterval: number): boolean {
        if ( simulationInterval > 0 && simulationInterval < 121 ) {
            this.simulationInterval = simulationInterval;
            return true;
        }
        return false;
    }

    /**
     * Retrieve a particular vehicle based on the fleet number.
     * @param fleetNumber the fleet number to retrieve the vehicle for.
     * @return the vehicle information for the fleet number or null if the fleet number does not exist.
     */
    retrieveVehicleByFleetNumber(fleetNumber: string): Vehicle {
        for ( var i = 0; i < this.vehicles.length; i++ ) {
            if ( this.vehicles[i].fleetNumber.valueOf() === fleetNumber ) {
                return this.vehicles[i];
            }
        }
        return null;
    }

    /**
     * Retrieve the delay of a particular vehicle according to the assigned tour.
     * @param assignedTour the assigned tour to retrieve the vehicle for.
     * @return the delay of the vehicle running the assigned tour or -1 if the tour is not assigned.
     */
    retrieveDelayForAssignedTour(assignedTour: string): number {
        for ( var i = 0; i < this.vehicles.length; i++ ) {
            if ( this.vehicles[i].allocatedTour === assignedTour ) {
                return this.vehicles[i].delay;
            }
        }
        return -1;
    }

}
