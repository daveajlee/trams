import {Injectable} from '@angular/core';
import {Game} from '../game/game.model';

@Injectable()
/**
 * This class provides access via http calls to the server to collect data about stops and routes as necessary.
 */
export class GameService {

    private game: Game;
    private offlineVersion: boolean = true;
    private serverUrl: string;

    /**
     * Set the game to the current game that was supplied by the user.
     * @param currentGame the current game that the user supplied.
     */
    setGame(currentGame: Game): void {
        this.game = currentGame;
    }

    getGame(): Game {
        return this.game;
    }

    /**
     * Set whether the offline version should be used.
     * @param useOfflineVersion a boolean which is true iff the offline version should be used.
     */
    setOfflineVersion(useOfflineVersion: boolean) {
        this.offlineVersion = useOfflineVersion;
    }

    isOfflineVersion(): boolean {
        return this.offlineVersion;
    }

    /**
     * Set the server url.
     * @param serverUrl the new server url to be set
     */
    setServerUrl(serverUrl: string) {
        this.serverUrl = serverUrl;
    }

    getServerUrl() {
        return this.serverUrl;
    }

}
