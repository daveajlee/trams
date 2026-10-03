/**
 * This class represents a game which can be saved in the database.
 * A game currently consists of company name, player name, skill level, 
 * start date and an id which is assigned by the database.
 */
export class Game {

  companyName: string;
  playerName: string;
  scenarioName: string;
  level: string;
  startDate: Date;
  balance: number;
  passengerSatisfaction: number;
  id?: number;

    constructor(companyName: string, playerName: string, scenarioName: string, level: string, startDate: Date, id?: number) {
        this.companyName = companyName;
        this.playerName = playerName;
        this.scenarioName = scenarioName;
        this.level = level;
        this.startDate = startDate;
        this.balance = 200000;
        this.passengerSatisfaction = 100;
        this.id = id;
    }
}