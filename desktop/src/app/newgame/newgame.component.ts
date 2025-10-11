import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";
import {FormsModule} from '@angular/forms';
import {HeaderComponent} from '../header/header.component';

@Component({
  selector: 'app-newgame',
  templateUrl: './newgame.component.html',
  imports: [
    FormsModule,
    HeaderComponent
  ],
  styleUrls: ['./newgame.component.css']
})
export class NewgameComponent {

  companyName: string = "";
  playerName: string = "";
  difficultyLevel: string  = "";
  startingDate: string | null = "";

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
