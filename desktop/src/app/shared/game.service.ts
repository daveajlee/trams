import {Injectable} from '@angular/core';
import {Game} from '../game/game.model';

@Injectable()
/**
 * This class provides access via http calls to the server to collect data about stops and routes as necessary.
 */
export class GameService {

    private game: Game;

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

}
