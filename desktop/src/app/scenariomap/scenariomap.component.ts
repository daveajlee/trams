import { Component, OnInit } from '@angular/core';
import { GameService } from '../shared/game.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-scenariomap',
  templateUrl: './scenariomap.component.html',
  styleUrls: ['./scenariomap.component.css']
})
export class ScenariomapComponent implements OnInit {

  constructor(private gameService: GameService, public router: Router) {
  }

  ngOnInit(): void {

  }

  getScenarioName(): string {
    return this.gameService.getGame().getScenario().scenarioName;
  }

  getScenarioImage(): string {
    if ( this.gameService.getGame().getScenario().locationMap && this.gameService.getGame().getScenario().locationMap != '') {
      return 'assets/' + this.gameService.getGame().getScenario().locationMap;
    } else {
      return '';
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
