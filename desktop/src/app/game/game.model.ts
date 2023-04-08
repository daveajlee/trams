/**
 * This class defines a model for the game that is currently loaded in the TraMS application.
 */
export class Game {

    public companyName: string;
    public startingBalance: number;
    public playerName: string;
    public startingTime: string;
    public scenarioName: string;
    public difficultyLevel: string;

    /**
     * Construct a new game which contains the supplied data.
     * @param companyName the name of the company
     * @param startingBalance the starting balance of the company
     * @param playerName the player name who will run the company
     * @param startingTime the starting date and time for playing this company
     * @param scenarioName the name of the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     */

    constructor( companyName: string, playerName: string, startingTime: string, scenarioName: string,
                 difficultyLevel: string ) {
        this.companyName = companyName;
        this.startingBalance = 80000.0;
        this.playerName = playerName;
        this.startingTime = startingTime;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
    }

}
