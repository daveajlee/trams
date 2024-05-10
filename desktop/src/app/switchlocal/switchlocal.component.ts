import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-switchlocal',
  templateUrl: './switchlocal.component.html',
  styleUrls: ['./switchlocal.component.css']
})
export class SwitchlocalComponent {

    gameService: GameService;

    constructor(gameService: GameService) {
        this.gameService = gameService;
    }

    changeOfflineVersion( event: Event ) {
        this.gameService.setOfflineVersion((<HTMLInputElement>event.target).checked);
    }

}
