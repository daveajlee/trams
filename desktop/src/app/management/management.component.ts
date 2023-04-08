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
    return '' + this.gameService.getGame().startingBalance;
  }

  ngOnInit(): void {
  }

  onLocationMap(): void {
    this.router.navigate(['scenariomap']);
  }

  onViewInformation(): void {
    this.router.navigate(['scenarioinfo']);
  }

}
