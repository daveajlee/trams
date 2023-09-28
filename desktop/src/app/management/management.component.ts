import { Component, OnInit } from '@angular/core';
import {GameService} from '../shared/game.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {

  currentDate: Date;
  gameService: GameService;

  constructor(private gameService2: GameService, public router: Router) {
    this.currentDate = new Date();
    this.gameService = gameService2;
  }

  getCurrentDate(): string {
    return this.currentDate.toLocaleString('en-gb', { weekday: 'long', day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit' });
  }

  getBalance(): string {
    return '' + this.gameService.getGame().balance;
  }

  ngOnInit(): void {
  }

  onLocationMap(): void {
    this.router.navigate(['scenariomap']);
  }

  onViewInformation(): void {
    this.router.navigate(['scenarioinfo']);
  }

  onCreateRoute(): void {
    this.router.navigate(['routecreator']);
  }

  onViewMessages(): void {
    this.router.navigate(['messages']);
  }

  onResign(): void {
    if(confirm("Are you sure you want to resign from " + this.gameService.getGame().companyName + "? This will end " +
        "your game and any changes you have made will not be saved.")) {
      // Currently it is enough to redirect to the homepage since we do not save data in local storage yet.
      this.router.navigate([''])
    }
  }

  noRoutesExist(): boolean {
    return this.gameService.getGame().routes.length === 0;
  }


}
