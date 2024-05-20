import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-newgame',
  templateUrl: './newgame.component.html',
  styleUrls: ['./newgame.component.css']
})
export class NewgameComponent {

  companyName: string;
  playerName: string;
  difficultyLevel: string;
  startingDate: string;

  constructor(public router: Router, private datePipe: DatePipe) {
    this.difficultyLevel = 'Easy';
    this.startingDate = this.datePipe.transform(new Date(), 'yyyy-MM-dd');
  }

  /**
   * On submission of the start game form, we create a game.
   */
  onStartSubmit(): void {
    this.router.navigate(['scenariolist'], { queryParams: { company: this.companyName,
        playerName: this.playerName, startingDate: this.startingDate, difficultyLevel: this.difficultyLevel } });
  }

}
