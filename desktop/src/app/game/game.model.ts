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

    private companyName: string;
    private balance: number;
    private playerName: string;
    private currentDateTime: Date;
    private scenario: Scenario;
    private difficultyLevel: string;
    private passengerSatisfaction: number;
    private routes: Route[];
    private messages: Message[];
    private vehicles: Vehicle[];
    private drivers: Driver[];
    private allocations: Allocation[];
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
     * This method should retrieve the information for a particular driver.
     * @param position the position in the drivers array which should be returned.
     */
    getDriverByPosition(position:number) {
        return this.drivers[position];
    }

    /**
     * This method retrieves all of the drivers currently in TraMS.
     * @return the list of drivers.
     */
    getDrivers(): Driver[] {
        return this.drivers;
    }

    /**
     * Delete a driver from the list.
     * @param name the name of the driver who should be removed.
     */
    deleteDriverByName(name: string) {
        for ( var i = 0; i < this.drivers.length; i++ ) {
            if ( this.drivers[i].getName().valueOf() === name.valueOf() ) {
                this.drivers.splice(i, 1);
            }
        }
        console.log('Currently the length of drivers is: ' + this.drivers.length )
    }

    /**
     * This method adds an allocation to the allocations array if we are currently saving allocations locally.
     * @param allocation a allocation object with the allocation information to add to the allocations array.
     */
    addAllocation(allocation: Allocation): void {
        this.allocations.push(allocation);
    }

    /**
     * This method retrieves all of the allocations currently assigned in TraMS.
     * @return the list of allocations.
     */
    getAllocations(): Allocation[] {
        return this.allocations;
    }

    /**
     * This method deletes the specified allocation.
     * @param fleetNumber the fleet number of the allocation which should be removed.
     * @param routeNumber the route number of the allocation which should be removed.
     * @param tourNumber the tour number of the allocation which should be removed.
     * @return a boolean which is true iff the allocation was deleted successfully.
     */
    deleteAllocation(fleetNumber: string, routeNumber: string, tourNumber: string): boolean {
        for ( let i = 0; i < this.allocations.length; i++ ) {
            if (this.allocations[i].getFleetNumber().valueOf() === fleetNumber.valueOf() &&
                this.allocations[i].getRouteNumber().valueOf() === routeNumber.valueOf() &&
                this.allocations[i].getTourNumber().valueOf() === tourNumber.valueOf()
            ) {
                this.allocations.splice(i, 1);
                return true;
            }
        }
        return false;
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
    getVehicleByFleetNumber(fleetNumber: string): Vehicle {
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

    /**
     * Check if any routes exist.
     * @return a boolean which is true iff at least one route exists.
     */
    doRoutesExist(): boolean {
        return this.routes.length > 0;
    }

    /**
     * Retrieve the first route number in the routes list.
     * @return the route number of the first route in the list as a string.
     */
    getFirstRouteNumber(): string {
        return this.routes[0].routeNumber;
    }

    /**
     * Retrieve all of the route numbers in the routes list.
     * @return a string array of all route numbers in the routes list.
     */
    getRouteNumbers(): string[] {
        var routeNumbers = [];
        for ( var i = 0; i < this.routes.length; i++ ) {
            routeNumbers[i] = this.routes[i].routeNumber;
        }
        return routeNumbers;
    }

    /**
     * Retrieve all of the routes in the routes list.
     * @return a route array of all routes in the routes list.
     */
    getRoutes(): Route[] {
        return this.routes;
    }

    /**
     * Retrieve all of the fleet numbers in the vehicles list.
     * @return a string array of all fleet numbers in the vehicles list.
     */
    getFleetNumbers(): string[] {
        var fleetNumbers = [];
        for ( var i = 0; i < this.vehicles.length; i++ ) {
            fleetNumbers[i] = this.vehicles[i].fleetNumber;
        }
        return fleetNumbers;
    }

    /**
     * Check if any vehicles exist.
     * @return a boolean which is true iff at least one vehicle exists.
     */
    doVehiclesExist(): boolean {
        return this.vehicles.length > 0;
    }

    /**
     * Retrieve the first fleet number in the vehicles list.
     * @return the fleet number of the first vehicle in the list as a string.
     */
    getFirstFleetNumber(): string {
        return this.vehicles[0].fleetNumber;
    }

    /**
     * Check if any allocations exist.
     * @return a boolean which is true iff at least one allocation exists.
     */
    doAllocationsExist(): boolean {
        return this.allocations.length > 0;
    }

    /**
     * Get the current date and time.
     * @return the current date and time as a Date object.
     */
    getCurrentDateTime(): Date {
        return this.currentDateTime;
    }

    /**
     * Get the current passenger satisfaction.
     * @return the current passenger satisfaction as a number.
     */
    getPassengerSatisfaction(): number {
        return this.passengerSatisfaction;
    }

    /**
     * Get the company name.
     * @return the company name as a string.
     */
    getCompanyName(): string {
        return this.companyName;
    }

    /**
     * This method updates the game model for a simulation step i.e. an increment of minutes.
     * Currently it updates time and calculates a new version for the passenger satisfaction.
     */
    updateSimulationStep(): void {
        // Increase the time by the simulation interval of minutes,
        this.currentDateTime = new Date(this.currentDateTime.getTime() + this.getSimulationInterval() *60000);
        // Check if we went past midnight. If so, then we need to pay drivers again.
        if ( ((this.currentDateTime.getHours() * 60) + this.currentDateTime.getMinutes()) <= this.getSimulationInterval() ) {
            // Now we need to pay drivers.
            this.withdrawBalance(this.drivers.length * 90);
        }
        // Decrease or increase the passenger satisfaction by a maximum of 2 in either plus or minus direction,
        var randomDiff = Math.random() * (4);
        this.passengerSatisfaction = Math.round(this.passengerSatisfaction + (randomDiff-2));
        // Ensure that passenger satisfaction is between 0 and 100.
        if ( this.passengerSatisfaction < 0 ) {
            this.passengerSatisfaction = 0;
        } else if ( this.passengerSatisfaction > 100 ) {
            this.passengerSatisfaction = 100;
        }
    }

    /**
     * Filter the messages for a particular folder.
     * @param folder the name of the folder to filter for as a string.
     * @return the messages array with the filtered messages.
     */
    filterMessagesByFolder(folder: string) {
        return this.messages.filter((message: Message) =>
            message.folder.valueOf() === folder);
    }

    /**
     * Get the difficulty level.
     * @return the difficulty level as string.
     */
    getDifficultyLevel(): string {
        return this.difficultyLevel;
    }

    /**
     * Set the difficulty level.
     * @param level the difficulty level to set as a string.
     */
    setDifficultyLevel(level: string): void {
        this.difficultyLevel = level;
    }

    /**
     * Retrieve the scenario that this game is running.
     * @return the scenario object that is being run in this game.
     */
    getScenario(): Scenario {
        return this.scenario;
    }

    /**
     * Get the name of the player currently playing the game.
     * @return the name of the player as a string.
     */
    getPlayerName(): string {
        return this.playerName;
    }

    /**
     * Get the fleet number that is running the specified schedule.
     * @param schedule the route number / schedule id that we want to find the vehicle for.
     * @return the fleet number as a number.
     */
    getAssignedVehicle(schedule: string): string {
        for ( let i = 0; i < this.vehicles.length; i++ ) {
            if ( this.vehicles[i].allocatedTour === (schedule) ) {
                return this.vehicles[i].fleetNumber;
            }
        }
    }

    /**
     * This method should retrieve the information for a particular vehicle.
     * @param position the position in the vehicles array which should be returned.
     */
    getVehicleByPosition(position:number) {
        return this.vehicles[position];
    }

    /**
     * Sell the specified vehicle by deleting it from the vehicles list.
     * @param fleetNumber the vehicle that should be deleted.
     */
    deleteVehicleByFleetNumber(fleetNumber: string): void {
        for ( var i = 0; i < this.vehicles.length; i++ ) {
            if ( this.vehicles[i].fleetNumber.valueOf() === fleetNumber.valueOf() ) {
                this.creditBalance(parseFloat(this.vehicles[i].additionalTypeInformationMap.get('Value')));
                this.vehicles.splice(i, 1);
            }
        }
        console.log('Currently the length of vehicles is: ' + this.vehicles.length )
    }

    /**
     * Get a list of all vehicles in the game to display to the user.
     * @return the list of vehicles currently being used in the game.
     */
    getVehicles(): Vehicle[] {
        return this.vehicles;
    }

    /**
     * Get the highest fleet number used so far.
     * @return the highest fleet number used so far as a number.
     */
    getHighestFleetNumber(): number {
        let highestFleetNumberSoFar = 0;
        for ( var i = 0; i < this.vehicles.length; i++ ) {
            if ( parseInt(this.vehicles[i].fleetNumber) > highestFleetNumberSoFar ) {
                highestFleetNumberSoFar = parseInt(this.vehicles[i].fleetNumber);
            }
        }
        return  highestFleetNumberSoFar;
    }


}
