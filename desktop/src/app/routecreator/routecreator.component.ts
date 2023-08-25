import {Component, OnInit} from '@angular/core';
import {GameService} from "../shared/game.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-routecreator',
  templateUrl: './routecreator.component.html',
  styleUrls: ['./routecreator.component.css']
})
export class RoutecreatorComponent implements OnInit {

  routeNumber: string;
  gameService: GameService;

  constructor(private gameService2: GameService, public router: Router) {
    this.gameService = gameService2;
  }

  ngOnInit(): void {
  }

  getScenarioName(): string {
    return this.gameService.getGame().scenarioName;
  }

}
