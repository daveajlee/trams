import {Scenario} from "../shared/scenario.model";
import {Route} from "../routes/route.model";
import {Message} from "../messages/message.model";

/**
 * This class defines a model for the game that is currently loaded in the TraMS application.
 */
export class Game {

    public companyName: string;
    public startingBalance: number;
    public playerName: string;
    public startingTime: string;
    public scenario: Scenario;
    public difficultyLevel: string;
    public routes: Route[];
    public messages: Message[];

    /**
     * Construct a new game which contains the supplied data.
     * @param companyName the name of the company
     * @param playerName the player name who will run the company
     * @param startingTime the starting date and time for playing this company
     * @param scenario the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     */

    constructor( companyName: string, playerName: string, startingTime: string, scenario: Scenario,
                 difficultyLevel: string ) {
        this.companyName = companyName;
        this.startingBalance = 80000.0;
        this.playerName = playerName;
        this.startingTime = startingTime;
        this.scenario = scenario;
        this.difficultyLevel = difficultyLevel;
        this.routes = [];
        this.messages = [];
    }

    /**
     * This method adds a route to the routes array if we are currently saving routes locally.
     * @param route a route object with the route information to add to the routes array.
     */
    addRoute(route: Route): void {
        this.routes.push(route);
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
