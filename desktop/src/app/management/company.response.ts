/**
 * This class defines a model for retrieving a company from the TraMS server.
 */
export class CompanyResponse {

    public name: string;
    public balance: number;
    public playerName: string;
    public time: string;
    public satisfactionRate: number;
    public scenarioName: string;
    public difficultyLevel: string;

    /**
     * Construct a new company response which contains the supplied data.
     * @param name the name of the company
     * @param balance the balance of the company
     * @param playerName the player name running the company
     * @param time the date and time for playing this company
     * @param satisfactionRate the current passenger satisfaction rate for this company.
     * @param scenarioName the name of the scenario that the player will play with this company
     * @param difficultyLevel the level of difficulty that the player wants to play
     */

    constructor( name: string, balance: number, playerName: string, time: string, satisfactionRate: number,
                 scenarioName: string, difficultyLevel: string ) {
        this.name = name;
        this.balance = balance;
        this.playerName = playerName;
        this.time = time;
        this.satisfactionRate = satisfactionRate;
        this.scenarioName = scenarioName;
        this.difficultyLevel = difficultyLevel;
    }

}