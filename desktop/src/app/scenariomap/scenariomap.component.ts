import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-scenariomap',
  templateUrl: './scenariomap.component.html',
  styleUrls: ['./scenariomap.component.css']
})
export class ScenariomapComponent implements OnInit {

  gameService: GameService;

  constructor(private gameService2: GameService, public router: Router) { 
    this.gameService = gameService2;
  }

  ngOnInit(): void {

  }

  getScenarioName(): string {
    return this.gameService.getGame().scenario.scenarioName;
  }

  getScenarioImage(): string {
    if ( this.gameService.getGame().scenario.locationMap && this.gameService.getGame().scenario.locationMap != '') {
      return 'assets/' + this.gameService.getGame().scenario.locationMap;
    } else {
      return '';
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
