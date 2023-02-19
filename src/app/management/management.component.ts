import { Component, OnInit } from '@angular/core';
import {GameService} from '../shared/game.service';

@Component({
  selector: 'app-management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent implements OnInit {

  constructor(private gameService: GameService) { }

  ngOnInit(): void {
    console.log(this.gameService.getGame().companyName);
  }

}
