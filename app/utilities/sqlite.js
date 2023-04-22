import * as SQLite from 'expo-sqlite';
import { Game } from "../models/game";
import AdditionalTour from '../models/additionalTour';

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

    promise.then(
        promise = new Promise((resolve, reject) => {
            database.transaction((tx) => {
                tx.executeSql(`CREATE TABLE IF NOT EXISTS assignments (
                    id INTEGER PRIMARY KEY NOT NULL,
                    routeNumber TEXT NOT NULL,
                    tourNumber INTEGER NOT NULL,
                    fleetNumber INTEGER NOT NULL,
                    scenarioName TEXT NOT NULL,
                    company TEXT NOT NULL
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
        }));
    
    promise.then(
        promise = new Promise((resolve, reject) => {
            database.transaction((tx) => {
                tx.executeSql(`CREATE TABLE IF NOT EXISTS additionalTours (
                    id INTEGER PRIMARY KEY NOT NULL,
                    routeNumber TEXT NOT NULL,
                    tourNumber INTEGER NOT NULL,
                    scenarioName TEXT NOT NULL,
                    company TEXT NOT NULL
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
        }));

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
 * Insert an assignment to the database by saving all of the required
 * information to the database
 * @param {Assignment} assignment the information to be saved
 * @returns a promise with either a success result or error message
 */
export function insertAssignment(assignment) {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql(`INSERT INTO assignments (routeNumber, tourNumber, fleetNumber, scenarioName, company) VALUES (?, ?, ?, ?, ?)`,
            [assignment.routeNumber, assignment.tourNumber, assignment.fleetNumber, assignment.scenarioName, assignment.company],
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

/**
 * Fetch the current assignments for a particular company
 * @param {String} company the name of the company
 * @returns the current assignments for a particular company
 */
export function fetchAssignments(company) {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql('SELECT * FROM assignments WHERE company = ?', [company],
            (_, result) => {
                const assignments = [];
                for (const assignment of result.rows._array) {
                    assignments.push(new Assignment(assignment.routeNumber, assignment.tourNumber, assignment.fleetNumber, assignment.scenarioName, assignment.company));
                }
                resolve(assignments);
            }, (_, error) => { reject(error); });
        })
    })
    
    return promise;
}

/**
 * Delete an assignment
 * @param {string} routeNumber the route number to delete the assignment of
 * @param {string} tourNumber the tour number to delete the assignment of
 * @param {string} company the name of the company to delete the assignment
 * @returns a promise with either a success result or error message
 */
export function deleteAssignment(routeNumber, tourNumber, company) {
    console.log('Calling delete assignment with ' + company + ', ' + routeNumber + ', ' + tourNumber)
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql('DELETE FROM assignments WHERE company = ? AND routeNumber = ? AND tourNumber = ?', [company, routeNumber, tourNumber],
            (_, result) => {
                console.log(result);
                resolve(result);
            },
            (_, error) => {
                reject(error);
            })
        })
    })
    
    return promise;
}

/**
 * Insert an additional tour for this scenario
 * @param {AdditionalTour} additionalTour the additional tour to add
 * @returns a promise with either a success result or error message
 */
export function insertAdditionalTour(additionalTour) {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql(`INSERT INTO additionalTours (routeNumber, tourNumber, scenarioName, company) VALUES (?, ?, ?, ?)`,
            [additionalTour.routeNumber, additionalTour.tourNumber, additionalTour.scenarioName, additionalTour.company],
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
 * Fetch the additional tours for a particular company
 * @param {string} company the name of the company to fetch the additional tours for
 * @returns the additional tours
 */
export function fetchAdditionalTours(company) {
    const promise = new Promise((resolve, reject) => {
        database.transaction((tx) => {
            tx.executeSql('SELECT * FROM additionalTours WHERE company = ?', [company],
            (_, result) => {
                const additionalTours = [];
                for (const additionalTour of result.rows._array) {
                    additionalTours.push(new AdditionalTour(additionalTour.routeNumber, additionalTour.tourNumber, additionalTour.scenarioName, additionalTour.company));
                }
                resolve(additionalTours);
            }, (_, error) => { reject(error); });
        })
    })
    
    return promise;
}