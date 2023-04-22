/**
 * This file contains all of the constants for the Landuff Scenario.
 */
import Route from "../models/route";
import Vehicle from "../models/vehicle";

export const LANDUFF_NAME = "Landuff";

export const LANDUFF_DESCRIPTION = "Landuff Town is a small town with a very friendly town council. They want to work with you in providing an efficient and effective transport service for Landuff Town."

export const LANDUFF_TARGETS = "Serve all bus stops in Landuff. // Ensure a frequent service on all routes. // Ensure that passenger satisfaction remains above 70% at all times."

export const LANDUFF_ROUTES = [
    new Route('1','Airport','Town Park',6),
    new Route('1A','Airport','Uni Campus',4),
    new Route('2','Town Park','Promenade',3),
    new Route('3','Sea Village','Greenfield',4),
    new Route('5','Promenade','T Junction',5),];

export const LANDUFF_VEHICLES = [
    new Vehicle(101,'23-LATS-101','SimpleChassisBE','Easy Low Bendi','Low Floor, Electronic Display, Bendi-Bus','White'),
    new Vehicle(201,'23-LATS-102','SimpleChassisDD','Easy Low Double','Low Floor, Electronic Display, Double-Decker','White'),
    new Vehicle(401,'23-LATS-103','SimpleChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
];