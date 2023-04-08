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
    return this.gameService.getGame().scenarioName;
  }

  getScenarioImage(): string {
    return 'assets/' + this.gameService.getGame().scenarioName.toLowerCase().replace(' ','') + '-map-picture.jpg';
  }

  backToManagementScreen(): void {
    this.router.navigate(['management']);
  }

}
