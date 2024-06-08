import { Component } from '@angular/core';
import {GameService} from "../shared/game.service";

@Component({
  selector: 'app-switchlocal',
  templateUrl: './switchlocal.component.html',
  styleUrls: ['./switchlocal.component.css']
})
export class SwitchlocalComponent {

    private hideServerUrl: boolean;
    serverUrl: string;

    constructor(private gameService: GameService) {
        this.hideServerUrl = this.gameService.isOfflineVersion();
        this.serverUrl = "http://localhost:8084/api";
        this.gameService.setServerUrl(this.serverUrl);
    }

    /**
     * Change the value of offline version to match the current value supplied by the event.
     * @param event the event caused by user interaction.
     */
    changeOfflineVersion( event: Event ) {
        this.gameService.setOfflineVersion((<HTMLInputElement>event.target).checked);
        this.hideServerUrl = (<HTMLInputElement>event.target).checked;
    }

    /**
     * Change the value of server url to match the current value supplied by the event.
     * @param event the event caused by user interaction.
     */
    changeServerUrl( event: Event ) {
        console.log('Server Url: ' + (<HTMLInputElement>event.target).value);
        this.gameService.setServerUrl((<HTMLInputElement>event.target).value);
    }

    /**
     * Return true iff hiding the server url is true.
     * @return a boolean with the value of hiding the server url.
     */
    isHideServerUrl(): boolean {
        return this.hideServerUrl;
    }

}
