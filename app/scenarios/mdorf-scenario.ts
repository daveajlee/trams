/**
 * This file contains all of the constants for the MDorf Scenario.
 */
import Route from "../models/route";
import Vehicle from "../models/vehicle"

export const MDORF_NAME = "MDorf";

export const MDORF_DESCRIPTION = "Millenium Dorf City is a small city. The city council are prepared to work with you providing that you can meet their targets within their timescales."

export const MDORF_TARGETS = "Serve all bus stops in MDorf. // Ensure a frequent service on all routes. // Ensure that passenger satisfaction remains above 35% at all times."

export const MDORF_ROUTES = [
    new Route('1','City Park','Airport Road',6),
    new Route('2','Leisure Avenue','Y Junction',5),
    new Route('4','M Junction','Y Street',5),
    new Route('4A','Stadium','Airport Terminal',6),
    new Route('5','Expressway','Y Street',5),
];

export const MDORF_VEHICLES = [
    new Vehicle(101,'23-MDOR-101','MBuildChassisBE','Easy Low Bendi','Low Floor, Electronic Display, Bendi-Bus','White'),
    new Vehicle(201,'23-MDOR-201','MBuildChassisDD','Easy Low Double','Low Floor, Electronic Display, Double-Decker','White'),
    new Vehicle(301,'23-MDOR-202','MBuildChassisDD','Easy Low Double','Low Floor, Electronic Display, Double-Decker','White'),
    new Vehicle(302,'23-MDOR-301','MBuildChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
    new Vehicle(303,'23-MDOR-302','MBuildChassisSD','Easy Low Mini','Low Floor, Electronic Display, Minibus','White'),
];