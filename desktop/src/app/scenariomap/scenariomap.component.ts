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
    return this.gameService.getGame().getScenario().getScenarioName();
  }

  getScenarioImage(): string {
    if ( this.gameService.getGame().getScenario().getImagePath() && this.gameService.getGame().getScenario().getImagePath() != 'assets/') {
      return this.gameService.getGame().getScenario().getImagePath();
    } else {
      return '';
    }
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
