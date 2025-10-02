/**
 * This file contains all of the constants for the Longts Scenario.
 */
import Route from "../models/route";
import Vehicle from "../models/vehicle";

export const LONGTS_NAME = "Longts";

export const LONGTS_DESCRIPTION = "Longts City is a very large city. The city council are suspicious of your new company and you will need to impress them very quickly in order to establish a good working relationship."

export const LONGTS_TARGETS = "Serve all bus stops in Longts. // Ensure a very frequent service on all routes. // Ensure that passenger satisfaction remains above 50% at all times."

export const LONGTS_ROUTES = [
    new Route('1','North Link','Arrow Junction',8),
    new Route('3','Crescent Avenue','Park North',7),
    new Route('5','South Street','Park North',5),
    new Route('7','Airport','South Square',6),
    new Route('7A','Airport','Lake Junction',5),
];

export const LONGTS_VEHICLES = [
    new Vehicle(1001,'23-LONG-1001','LongChassisBE','Easy Low Bendi','Low Floor, Electronic Display, Bendi-Bus','White'),
    new Vehicle(2001,'23-LONG-2001','LongChassisDD','Easy Low Double','Low Floor, Electronic Display, Double-Decker','White'),
    new Vehicle(3001,'23-LONG-3001','LongChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
    new Vehicle(3002,'23-LONG-3002','LongChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
    new Vehicle(3003,'23-LONG-3003','LongChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
];