import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {ScenarioService} from './scenario.service';
import {GameService} from '../shared/game.service';
import {Game} from '../game/game.model';
import { Scenario } from '../shared/scenario.model';
import { SCENARIOS } from 'src/data/scenario.data';
import {SCENARIO_LANDUFF} from "../../data/scenarios/landuff.data";
import {SCENARIO_LONGTS} from "../../data/scenarios/longts.data";
import {SCENARIO_MDORF} from "../../data/scenarios/mdorf.data";
import {Vehicle} from "../vehicles/vehicle.model";
import {Driver} from "../drivers/driver.model";

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

  scenarios: Scenario[];
  gameService: GameService;

  /**
   * Create a new scenario list component which displays a series of scenarios that the user can choose from.
   * @param route which manages the current route in Angular.
   * @param scenarioService which manages the creation of a new company and scenario.
   * @param gameService2 which manages the game that is currently being played.
   * @param router which manages routing in Angular.
   */
  constructor(private route: ActivatedRoute, private scenarioService: ScenarioService, private gameService2: GameService,
              public router: Router) {
      this.gameService = gameService2;
  }

    /**
     * Copy parameters from last request so that we do not lose the information that the user has provided.
     */
  ngOnInit(): void {
      this.route.queryParams
        .subscribe(params => {
              this.company = params.company;
              this.playerName = params.playerName;
              this.difficultyLevel = params.difficultyLevel;
              this.startingDate = params.startingDate;
            }
        );
      this.scenarios = SCENARIOS;
  }

    /**
     * When the user has chosen a scenario, we should create the company via API.
     * @param scenario which contains the name of the scenario that the user chose.
     */
  onScenarioSelect(scenario: string): void {
      this.gameService.setGame(new Game(this.company, this.playerName, new Date(this.startingDate), this.loadScenario(scenario), this.difficultyLevel
        , 200000.0, 90, [], [], [], [], []));
      // Add the message.
      this.gameService.getGame().addMessage("New Managing Director Announced",
            "Congratulations - " +  this.playerName + " has been appointed Managing Director of " + this.company + "!"
            +  "\n\nYour targets for the coming days and months: \n" + this.formatTargets(this.loadScenario(scenario).targets)
            + "\nYour contract to run public transport services in " + scenario + " will be terminated if these targets are not met!"
            + "\n\nGood luck!",
            "INBOX", this.gameService.getGame().currentDateTime, true, scenario + " Council");
      // Add the supplied vehicles.
      var mySuppliedVehicles = this.loadScenario(scenario).suppliedVehicles;
      for ( var i = 0; i < mySuppliedVehicles.length; i++ ) {
          for ( var j = 0; j < mySuppliedVehicles[i].quantity; j++ ) {
              const additionalProps = new Map<string, string>();
              additionalProps.set('Model', mySuppliedVehicles[i].model.modelName);
              additionalProps.set('Age', '0 months');
              additionalProps.set('Standing Capacity', '' + mySuppliedVehicles[i].model.standingCapacity);
              additionalProps.set('Seating Capacity', '' + mySuppliedVehicles[i].model.seatingCapacity);
              additionalProps.set('Value', '' + mySuppliedVehicles[i].model.value);
              this.gameService.getGame().addVehicle(new Vehicle('' + (i+j+1), mySuppliedVehicles[i].model.modelType, '',
                  '', '', 0, additionalProps));
          }
      }
      // Add the supplied drivers.
      var mySuppliedDrivers = this.loadScenario(scenario).suppliedDrivers;
      for ( i = 0; i < mySuppliedDrivers.length; i++ ) {
          this.gameService.getGame().addDriver(new Driver(mySuppliedDrivers[i], 35, this.startingDate));
      }
      this.router.navigate(['management']);
      // this.scenarioService.createCompany(this.company, this.playerName, this.difficultyLevel, this.startingDate, scenario);
  }

    /**
     * This is a helper method to process the targets into a formatted string for adding to a message.
     * @param targets the targets array for the scenario.
     */
    formatTargets(targets): string {
        var formattedTarget = "";
        for ( var i = 0; i < targets.length; i++ ) {
            formattedTarget += (i+1) + ". " + targets[i] + "\n";
        }
        return formattedTarget;
    }

    /**
     * This is a helper method to load the correct scenario based on the supplied scenario name.
     * @param scenario which contains the name of the scenario that the user chose.
     * @returns the scenario object corresponding to the supplied name.
     */
    loadScenario(scenario: string): Scenario {
        if ( scenario === SCENARIO_LANDUFF.scenarioName ) {
            return SCENARIO_LANDUFF;
        } else if ( scenario === SCENARIO_LONGTS.scenarioName) {
            return SCENARIO_LONGTS;
        } else if ( scenario === SCENARIO_MDORF.scenarioName ) {
            return SCENARIO_MDORF;
        } else {
            return null;
        }
    }

  

}
