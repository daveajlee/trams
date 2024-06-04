/**
 * This class defines a model for requests to create companies in TraMS.
 */
export class CompanyRequest {

    private name: string;
    private startingBalance: number;
    private playerName: string;
    private startingTime: string;
    private scenarioName: string;
    private difficultyLevel: string;

    /**
     * Construct a new company request which contains the supplied data.
     * @param name the name of the company
     * @param startingBalance the starting balance of the company
     * @param playerName the player name who will run the company
     * @param startingTime the starting date and time for playing this company
     * @param scenarioName the name of the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     */

    constructor( name: string, startingBalance: number, playerName: string, startingTime: string, scenarioName: string,
             difficultyLevel: string ) {
        this.name = name;
        this.startingBalance = startingBalance;
        this.playerName = playerName;
        this.startingTime = startingTime;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
    }

}

