import { Game } from "../models/game";
import AdditionalTour from '../models/additionalTour';
import Assignment from "../models/assignment";
import { open, QueryResult } from '@op-engineering/op-sqlite';

/**
 * Define the file where the database will be stored by SQLite.
 */
export const database = open({
  name: 'games.sqlite'
});

/**
 * Initialise the database by creating required tables
 * and returning whether it was successful.
 * @returns a promise with either a success result or an error message.
 */
export async function init(): Promise<void> {
  // Create games table.
  let createGameRes: QueryResult = await database.execute(`CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY NOT NULL,
                companyName TEXT NOT NULL,
                playerName TEXT NOT NULL,
                scenarioName TEXT NOT NULL,
                level TEXT NOT NULL,
                startDate TEXT NOT NULL
   )`);
  console.log(createGameRes);
  // Create assignments table.
  let createAssignmentsRes: QueryResult = await database.execute(`CREATE TABLE IF NOT EXISTS assignments (
                    id INTEGER PRIMARY KEY NOT NULL,
                    routeNumber TEXT NOT NULL,
                    tourNumber INTEGER NOT NULL,
                    fleetNumber INTEGER NOT NULL,
                    scenarioName TEXT NOT NULL,
                    company TEXT NOT NULL
                )`);
  console.log(createAssignmentsRes);
  // Create additional tours table.
  let additionalToursRes: QueryResult = await database.execute(`CREATE TABLE IF NOT EXISTS additionalTours (
                    id INTEGER PRIMARY KEY NOT NULL,
                    routeNumber TEXT NOT NULL,
                    tourNumber INTEGER NOT NULL,
                    scenarioName TEXT NOT NULL,
                    company TEXT NOT NULL
                )`);
  console.log(additionalToursRes);
}

/**
 * Insert a game to the database by saving required
 * information to the database
 * @param {Game} game the information to be saved
 * @returns a promise with either a success result or an error message.
 */
export async function insertGame(game: Game): Promise<number> {
    let insertResult: QueryResult = await database.execute(`INSERT INTO games (companyName, playerName, scenarioName, level, startDate) VALUES (?, ?, ?, ?, ?)`,
      [game.companyName, game.playerName, game.scenarioName, game.level, game.startDate.toDateString()]);
    return insertResult.insertId ? insertResult.insertId : 0;
}

/**
 * Insert an assignment to the database by saving required
 * information to the database
 * @param {Assignment} assignmentToAdd the information to be saved
 * @returns a promise with either a success result or error message
 */
export async function insertAssignment(assignmentToAdd: Assignment): Promise<number> {
  let insertResult: QueryResult = await database.execute(`INSERT INTO assignments (routeNumber, tourNumber, fleetNumber, scenarioName, company) VALUES (?, ?, ?, ?, ?)`,
    [assignmentToAdd.routeNumber, assignmentToAdd.tourNumber, assignmentToAdd.fleetNumber, assignmentToAdd.scenarioName, assignmentToAdd.company]);
  return insertResult.insertId ? insertResult.insertId : 0;
}

/**
 * Retrieve all games from the database.
 * @returns a promise with all games or an error message if something bad happens
 */
export async function fetchGames(): Promise<Game[]> {
  const games = <Game[]>[];
  let {rows} = await database.execute(`SELECT * FROM games`);
  rows.forEach(game => {
    games.push(new Game(game.companyName?.toString()!, game.playerName?.toString()!, game.scenarioName?.toString()!, game.level?.toString()!, new Date(game.date?.toString()!), parseInt(game.id?.toString()!, 10)));
  })
  return games;
}

/**
 * Retrieve the game with the specified company name from the database.
 * @param companyName the company name of the game to retrieve.
 * @returns a promise with all games or an error message if something bad happens
 */
export async function fetchGame(companyName: string): Promise<Game[]> {
  const games = <Game[]>[];
  let result: QueryResult = await database.execute(`SELECT * FROM games where companyName = ?`, [companyName]);
  result.rows.forEach(game => {
    games.push(new Game(game.companyName?.toString()!, game.playerName?.toString()!, game.scenarioName?.toString()!, game.level?.toString()!, new Date(game.date?.toString()!), parseInt(game.id?.toString()!, 10)));
  })
  return games;
}

/**
 * Delete a game based on the supplied company name.
 * @param {string} companyName the company name to delete
 * @returns a promise with either a success result or error message
 */
export async function deleteGame(companyName: string): Promise<number> {
  let deleteResult: QueryResult = await database.execute(`DELETE FROM games WHERE companyName = ?`, [companyName]);
  return deleteResult.insertId ? deleteResult.insertId : 0;
}

/**
 * Set the scenario name of the supplied game company name to the supplied scenario name.
 * @param companyName the company name of the game to retrieve.
 * @param scenarioName the scenario name to set.
 * @returns a promise with either a success result or error message
 */
export async function setScenarioNameForGame(companyName: string, scenarioName: string): Promise<number> {
  let updateResult: QueryResult = await database.execute(`UPDATE games SET scenarioName = ? WHERE companyName = ?`, [scenarioName, companyName]);
  return updateResult.insertId ? updateResult.insertId : 0;
}

/**
 * Fetch the current assignments for a particular company
 * @param {String} company the name of the company
 * @returns the current assignments for a particular company
 */
export async function fetchAssignments(company: string): Promise<Assignment[]> {
  const assignments = <Assignment[]>[];
  let result: QueryResult = await database.execute(`SELECT * FROM assignments where companyNane = ?`, [company]);
  result.rows.forEach(assignment => {
    assignments.push(new Assignment(assignment.routeNumber?.toString()!, parseInt(assignment.tourNumber?.toString()!, 10), parseInt(assignment.fleetNumber?.toString()!, 10), assignment.scenarioName?.toString()!, assignment.company?.toString()!));
  });
  return assignments;
}

/**
 * Delete an assignment
 * @param {string} routeNumber the route number to delete the assignment of
 * @param {string} tourNumber the tour number to delete the assignment of
 * @param {string} company the name of the company to delete the assignment
 * @returns a promise with either a success result or error message
 */
export async function deleteAssignment(routeNumber: string, tourNumber: number, company: string) : Promise<number> {
    console.log('Calling delete assignment with ' + company + ', ' + routeNumber + ', ' + tourNumber);
    let deleteResult: QueryResult = await database.execute(`DELETE FROM assignments WHERE company = ? AND routeNumber = ? AND tourNumber = ?`, [company, routeNumber, tourNumber]);
    return deleteResult.insertId ? deleteResult.insertId : 0;
}

/**
 * Insert an additional tour for this scenario
 * @param {AdditionalTour} additionalTour the additional tour to add
 * @returns a promise with either a success result or error message
 */
export async function insertAdditionalTour(additionalTour: AdditionalTour) {
  let insertResult: QueryResult = await database.execute(`INSERT INTO additionalTours (routeNumber, tourNumber, scenarioName, company) VALUES (?, ?, ?, ?)`,
    [additionalTour.routeNumber, additionalTour.tourNumber, additionalTour.scenarioName, additionalTour.company]);
  return insertResult.insertId ? insertResult.insertId : 0;
}

/**
 * Fetch the additional tours for a particular company
 * @param {string} company the name of the company to fetch the additional tours for
 * @returns the additional tours
 */
export async function fetchAdditionalTours(company: string): Promise<AdditionalTour[]> {
  const additionalTours = <AdditionalTour[]>[];
  let result: QueryResult = await database.execute(`SELECT * FROM additionalTours WHERE company = ?`, [company]);
  result.rows.forEach(additionalTour => {
    additionalTours.push(new AdditionalTour(additionalTour.routeNumber?.toString()!, parseInt(additionalTour.tourNumber?.toString()!, 10), additionalTour.scenarioName?.toString()!, additionalTour.company?.toString()!));
  });
  return additionalTours;
}