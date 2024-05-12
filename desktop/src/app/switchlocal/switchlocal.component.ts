import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-switchlocal',
  templateUrl: './switchlocal.component.html',
  styleUrls: ['./switchlocal.component.css']
})
export class SwitchlocalComponent {

    gameService: GameService;
    hideServerUrl: boolean;
    serverUrl: string;

    constructor(gameService: GameService) {
        this.gameService = gameService;
        this.hideServerUrl = this.gameService.isOfflineVersion();
        this.serverUrl = "http://localhost:8084/api";
        this.gameService.setServerUrl(this.serverUrl);
    }

    changeOfflineVersion( event: Event ) {
        this.gameService.setOfflineVersion((<HTMLInputElement>event.target).checked);
        this.hideServerUrl = (<HTMLInputElement>event.target).checked;
    }

    changeServerUrl( event: Event ) {
        console.log('Server Url: ' + (<HTMLInputElement>event.target).value);
        this.gameService.setServerUrl((<HTMLInputElement>event.target).value);
    }

}
