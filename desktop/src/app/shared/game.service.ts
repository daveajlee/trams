import {Injectable} from '@angular/core';
import {Game} from '../game/game.model';
import {ServerService} from "./server.service";

@Injectable()
/**
 * This class stores either the local game (offline mode) or uses a separate service to contact the server (online version).
 */
export class GameService {

    private game: Game;
    private offlineMode: boolean = true;


    /**
     * Construct a new game service which contains a server service that manages http calls in the case of online mode.
     * @param serverService the server service that manages the http calls.
     */
    constructor( private serverService: ServerService ) {
    }

    /**
     * Set the game to the current game that was supplied by the user.
     * @param currentGame the current game that the user supplied.
     */
    setGame(currentGame: Game): void {
        if ( this.offlineMode ) {
            this.game = currentGame;
        } else {
            this.serverService.createCompany(currentGame);
        }

    }

    getGame(): Game {
        return this.game;
    }

    /**
     * Set whether the offline mode should be used.
     * @param useOfflineMode a boolean which is true iff the offline mode should be used.
     */
    setOfflineMode(useOfflineMode: boolean) {
        this.offlineMode = useOfflineMode;
    }

    isOfflineMode(): boolean {
        return this.offlineMode;
    }

    /**
     * Set the server url.
     * @param serverUrl the new server url to be set
     */
    setServerUrl(serverUrl: string) {
        this.serverService.setServerUrl(serverUrl);
    }

    getServerUrl() {
        return this.serverService.getServerUrl();
    }

}
