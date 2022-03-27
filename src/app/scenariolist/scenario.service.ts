import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';

@Injectable({ providedIn: 'root' })
/**
 * This class provides the html connection for creating a new game including the choice of scenario to the server.
 */
export class ScenarioService {

    constructor(public router: Router, private http: HttpClient) {}

    /**
     * Create the game based on the provided settings: company name, player name, difficulty level, starting date and scenario.
     * @param companyName the name of the company to create.
     * @param playerName the name of the player who will be playing.
     * @param difficultyLevel the difficulty level which can be either Easy, Intermediate or Hard.
     * @param startingDate the date and time as a string when the simulation should start.
     * @param scenarioName the name of the scenario that the player wants to play.
     */
    upload(companyName: string, playerName: string, difficultyLevel: string, startingDate: string, scenarioName: string): void {
        const data = new FormData();
        data.append('name', companyName);
        data.append('startingBalance', '80000.0');
        data.append('playerName', playerName);
        data.append('startingTime', startingDate);
        data.append('scenarioName', scenarioName);
        data.append('difficultyLevel', difficultyLevel);
        this.http
            .post('http://localhost:8083/api/company/', data)
            .subscribe(
                () => {
                    this.router.navigate(['routes']);
                });
    }

}
