/**
 * This class represents a game which can be saved in the database.
 * A game currently consists of company name, player name, skill level, 
 * start date and an id which is assigned by the database.
 */
export class Game {
    constructor(companyName, playerName, scenarioName, level, startDate, id) {
        this.companyName = companyName;
        this.playerName = playerName;
        this.scenarioName = scenarioName;
        this.level = level;
        this.startDate = startDate;
        this.id = id;
    }
}