import { Component, OnInit } from '@angular/core';
import {GameService} from '../shared/game.service';
import {Router} from '@angular/router';
import {TipService} from "../shared/tip.service";

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {

  gameService: GameService;

  constructor(private gameService2: GameService, public router: Router, private tipService: TipService) {
    this.gameService = gameService2;
  }

  getCurrentDate(): string {
    return this.gameService.getGame().getCurrentDateTime().toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  getBalance(): string {
    return '' + this.gameService.getGame().getBalance();
  }

  getPassengerSatisfaction(): number {
    return this.gameService.getGame().getPassengerSatisfaction();
  }

  ngOnInit(): void {
  }

  onLocationMap(): void {
    this.router.navigate(['scenariomap']);
  }

  onViewInformation(): void {
    this.router.navigate(['scenarioinfo']);
  }

  onLoadLiveSituation(): void {
    this.router.navigate(['livesituation'])
  }

  onCreateRoute(): void {
    this.router.navigate(['routecreator']);
  }

  onViewMessages(): void {
    this.router.navigate(['messages']);
  }

  onEmployDriver(): void {
    this.router.navigate(['drivercreator']);
  }

  onPurchaseVehicle(): void {
    this.router.navigate(['vehicleshowroom']);
  }

  onChangeAllocation(): void {
    this.router.navigate(['allocations']);
  }
  onResign(): void {
    if(confirm("Are you sure you want to resign from " + this.gameService.getGame().getCompanyName() + "? This will end " +
        "your game and any changes you have made will not be saved.")) {
      // Currently it is enough to redirect to the homepage since we do not save data in local storage yet.
      this.router.navigate([''])
    }
  }

  noRoutesExist(): boolean {
    return !this.gameService.getGame().doRoutesExist();
  }

  noVehiclesExist(): boolean {
    return this.gameService.getGame().doRoutesExist() && !this.gameService.getGame().doVehiclesExist();
  }

  noAllocationsExist(): boolean {
    return this.gameService.getGame().doRoutesExist() && this.gameService.getGame().doVehiclesExist()
        && !this.gameService.getGame().doAllocationsExist();
  }

  showRandomTip(): string {
    return this.tipService.getRandomTip();
  }

}
