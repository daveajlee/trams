import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-scenariolist',
  templateUrl: './scenariolist.component.html',
  styleUrls: ['./scenariolist.component.css']
})
export class ScenariolistComponent implements OnInit {

  company: string;
  playerName: string;
  difficultyLevel: string;
  startingDate: string;

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
      this.route.queryParams
        .subscribe(params => {
              this.company = params.company;
              this.playerName = params.playerName;
              this.difficultyLevel = params.difficultyLevel;
              this.startingDate = params.startingDate;
            }
        );
  }
  onScenarioSelect(scenario: string): void {
      console.log('Attempt to generate html request or service call with ' + this.company + ' ' + this.playerName +
      ' ' + this.startingDate + ' ' + this.difficultyLevel + ' ' + scenario);
  }

}
