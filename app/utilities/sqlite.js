import * as SQLite from 'expo-sqlite';
import { Game } from "../models/game";

/**
 * Define the file where the database will be stored by SQLite.
 */
const database = SQLite.openDatabase('games.db');

/**
 * Initialise the database by creating all of the required tables
 * and returning whether or not it was successful.
 * @returns a promise with either a success result or an error message.
 */
export function init() {
    var promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql(`CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY NOT NULL,
                companyName TEXT NOT NULL,
                playerName TEXT NOT NULL,
                level TEXT NOT NULL,
                startDate TEXT NOT NULL
            )`,
            [],
            () => {
                resolve();
            },
            (_, error) => {
                reject(error);
            }
            );
        });
    });

    return promise;
}

/**
 * Insert a game to the database by saving all of the required
 * information to the database
 * @param {Game} game the information to be saved
 * @returns a promise with either a success result or an error message.
 */
export function insertGame(game) {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql(`INSERT INTO games (companyName, playerName, level, startDate) VALUES (?, ?, ?, ?)`,
            [game.companyName, game.playerName, game.level, game.startDate],
            (_, result) => {
                resolve(result);
            },
            (_, error) => {
                reject(error);
            })
        })
    });

    return promise;
}

/**
 * Retrieve all of the games from the database.
 * @returns a promise with all of the games or an error message if something bad happens
 */
export function fetchGames() {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql('SELECT * FROM games', [],
            (_, result) => {
                const games = [];
                for (const game of result.rows._array) {
                    games.push(new Game(game.companyName, game.playerName, game.level, game.startDate, game.id));
                }
                resolve(games);
            }, (_, error) => { reject(error); });
        })
    })

    return promise;
}