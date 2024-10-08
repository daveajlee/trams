import {ScheduleModel} from "../stops/stop-detail/schedule.model";
import {TimeHelper} from "./time.helper";
import {Game} from "../game/game.model";
import {PositionModel} from "../livesituation/position.model";
import {ServiceModel} from "../stops/stop-detail/service.model";
import {Route} from "../routes/route.model";

export class PositionHelper {

    /**
     * Helper method to find the current position of the vehicle and schedule that it is assigned to.
     * Take into account any delays.
     * @param routeTour the route and tour id that we want to get the position of in the format route number / tour.
     * @param routes the list of routes that are in the game.
     * @param game the current game that we are playing.
     * @return the position as a PositionModel object.
     */
    static getCurrentPosition(routeTour: string, routes: Route[], game: Game): PositionModel {
        // Get the schedule based on the route and tour.
        let schedule: ScheduleModel;
        for ( let i = 0; i < routes.length; i++ ) {
            if ( routes[i].getRouteNumber() === routeTour.split("/")[0] ) {
                let schedules = routes[i].getSchedules();
                for ( let j = 0; j < schedules.length; j++ ) {
                    if ( schedules[j].getRouteNumberAndScheduleId() === routeTour ) {
                        schedule = schedules[j];
                    }
                }
            }
        }
        if ( !schedule ) {
            return new PositionModel("Depot", "", 0);
        }
        // If a schedule is not assigned then it cannot be shown in the live situation and it's position is depot.
        if ( game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()) === -1 ) {
            return new PositionModel("Depot", "", 0);
        }
        var currentDateTime = game.getCurrentDateTime();
        let currentTime = TimeHelper.formatTimeAsString(currentDateTime);
        let assignedFleetId = game.getAssignedVehicle(schedule.getRouteNumberAndScheduleId());
        let delay = game.getVehicleByFleetNumber(assignedFleetId).getDelay();
        // Decrease time.
        currentTime = TimeHelper.subtractTime(currentTime, delay);
        // Now find relevant service and position.
        let services = schedule.getServices();
        // Go through the services.
        for ( let i = 0; i < services.length; i++ ) {
            // We skip the service if we are out of service.
            if ( services[i].isOutOfService() ) {
                continue;
            }
            for ( let j = 0; j < services[i].getStopList().length; j++ ) {

                // If we exactly match the departure time then this is where we are.
                if ( services[i].getStopList()[j].getDepartureTime() === currentTime ) {
                    // Check if we have a temporary start stop or end stop - then we are there.
                    if ( services[i].getTempEndStop() != -1 && j >= services[i].getTempEndStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    } else if ( services[i].getTempStartStop() != -1 && j <= services[i].getTempStartStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempStartStop()].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    }
                    // Otherwise we are at the normal position.
                    return new PositionModel(schedule.getServices()[i].getStopList()[j].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                }
                // If we are before departure time but after arrival time then we are also here.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() <= currentTime) {
                    // Check if we have a temporary start stop or end stop - then we are there.
                    if ( services[i].getTempEndStop() != -1 && j >= services[i].getTempEndStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    } else if ( services[i].getTempStartStop() != -1 && j <= services[i].getTempStartStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempStartStop()].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    }
                    return new PositionModel(schedule.getServices()[i].getStopList()[j].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                }
                // If we are before both departure time and arrival time then we are at the previous stop if there was one.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() > currentTime
                    && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
                    && j != 0 ) {
                    // Check if we have a temporary start stop or end stop - then we are there.
                    if ( services[i].getTempEndStop() != -1 && j >= services[i].getTempEndStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), schedule.getServices()[i].getStopList()[services[i].getTempEndStop()].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    } else if ( services[i].getTempStartStop() != -1 && j <= services[i].getTempStartStop() ) {
                        return new PositionModel(schedule.getServices()[i].getStopList()[services[i].getTempStartStop()].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                    }
                    return new PositionModel(schedule.getServices()[i].getStopList()[j-1].getStop(), schedule.getServices()[i].getStopList()[schedule.getServices()[i].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                }
                // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() > currentTime
                    && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
                    && j === 0
                    && i != 0 ) {
                    return new PositionModel(schedule.getServices()[i-1].getStopList()[schedule.getServices()[i-1].getStopList().length-1].getStop(), schedule.getServices()[i-1].getStopList()[schedule.getServices()[i-1].getStopList().length-1].getStop(), game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()));
                }

            }
        }
        // If we still did not match, then we must be at the depot.
        return new PositionModel("Depot", "", 0);
    }

    /**
     * Helper method to find the current and next service that is and will be running for a schedule.
     * Take into account any delays.
     * @param schedule the Schedule Model object that we want to retrieve the current service for.
     * @param game the current game that we are playing.
     * @return the current service and next service as elements 0 and 1 in a ServiceModel array.
     */
    static getCurrentAndNextService(schedule: ScheduleModel, game: Game): ServiceModel[] {
        // If a schedule is not assigned then it cannot be shown in the live situation and it's position is depot.
        if ( game.retrieveDelayForAssignedTour(schedule.getRouteNumberAndScheduleId()) === -1 ) {
            return null;
        }
        var currentDateTime = game.getCurrentDateTime();
        let currentTime = TimeHelper.formatTimeAsString(currentDateTime);
        let assignedFleetId = game.getAssignedVehicle(schedule.getRouteNumberAndScheduleId());
        let delay = game.getVehicleByFleetNumber(assignedFleetId).getDelay();
        // Decrease time.
        currentTime = TimeHelper.subtractTime(currentTime, delay);
        // Now find relevant service and position.
        let services = schedule.getServices();
        for ( let i = 0; i < services.length; i++ ) {
            for ( let j = 0; j < services[i].getStopList().length; j++ ) {
                // If we exactly match the departure time then this is where we are.
                if ( services[i].getStopList()[j].getDepartureTime() === currentTime ) {
                    if (i < (services.length - 1) ) {
                        return [services[i], services[i+1]];
                    } else {
                        return [services[i]];
                    }
                }
                // If we are before departure time but after arrival time then we are also here.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() <= currentTime) {
                    if (i < (services.length - 1) ) {
                        return [services[i], services[i+1]];
                    } else {
                        return [services[i]];
                    }
                }
                // If we are before both departure time and arrival time then we are at the previous stop if there was one.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() > currentTime
                    && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
                    && j != 0 ) {
                    if (i < (services.length - 1) ) {
                        return [services[i], services[i+1]];
                    } else {
                        return [services[i]];
                    }
                }
                // If there was not a previous stop then it is the previous service last stop where we are at if there was one.
                else if ( services[i].getStopList()[j].getDepartureTime() > currentTime
                    && services[i].getStopList()[j].getArrivalTime() > currentTime
                    && services[0].getStopList()[0].getArrivalTime() < currentTime // Ensure service has started
                    && j === 0
                    && i != 0 ) {
                    if (i < (services.length - 1) ) {
                        return [services[i-1], services[i]];
                    } else {
                        return [services[i-1]];
                    }
                }

            }
        }
        // If we still did not match, then no current service so return null.
        return null;
    }


}

