package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Address;
import de.davelee.trams.server.model.JourneyInstruction;
import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.model.StopTime;
import de.davelee.trams.server.request.JourneyRequest;
import de.davelee.trams.server.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service containing methods to calculate and plan journeys. A maximum of one change (excluding walking) currently exists.
 * @author Dave Lee
 */
@Service
public class CalculateJourneyService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;

    @Autowired
    private SuggestStopService suggestStopService;

    @Autowired
    private StopTimeService stopTimeService;

    private final static Logger LOG = LoggerFactory.getLogger(CalculateJourneyService.class);

    /**
     * Calculate the journey based on the journey request and return a series of journey instructions
     * which need to be followed to get from A to B.
     * @param journeyRequest a <code>JourneyRequest</code> object containing the journey to be planned.
     * @return a <code>List</code> of <code>JourneyInstruction</code> objects in order to fulfil the journey.
     */
    public List<JourneyInstruction> calculateJourney (final JourneyRequest journeyRequest) {
        //Create a blank list of journey instrutions.
        List<JourneyInstruction> journeyInstructionList = new ArrayList<>();
        //Translate time to local time.
        LocalDateTime currentDateTime = DateUtils.convertDateToLocalDateTime(journeyRequest.getDepartDateTime());
        // 1. Walk to the nearest station if the start point is not a station.
        Stop startStop = stopService.getStop(journeyRequest.getOperator(), journeyRequest.getFrom());
        if ( startStop == null ) {
            //Calculate the journey instruction for walking to a nearest stop. If no suggestion found, return empty list.
            JourneyInstruction journeyInstruction = walkToNearestStop(journeyRequest.getOperator(), journeyRequest.getFrom(), DateUtils.convertLocalDateTimeToDate(currentDateTime));
            if ( journeyInstruction == null ) {
                return Collections.emptyList();
            }
            //Otherwise add it to the list and set start stop accordingly and increment duration.
            journeyInstructionList.add(journeyInstruction);
            startStop = stopService.getStop(journeyRequest.getOperator(), journeyInstruction.getDestination());
            currentDateTime = currentDateTime.plusMinutes(journeyInstructionList.getFirst().getDurationInMins());
        }
        //Check if we have already reached the destination.
        if ( isJourneyEnd(startStop.getName(), journeyRequest.getTo() ) ) {
            return journeyInstructionList;
        }
        //1.1 Store now the nearest end stop before walking to destination if applicable.
        Stop endStop = stopService.getStop(journeyRequest.getOperator(), journeyRequest.getTo());
        if ( endStop == null ) {
            endStop = stopService.getStop(journeyRequest.getOperator(), suggestStopService.suggestNearestStop(journeyRequest.getOperator(), journeyRequest.getTo()));
        }
        //2. Determine path to destination.
        String changeStop = determineChangeStop(startStop.getName(), endStop.getName(), journeyRequest.getOperator());
        //If no change stop possible then return existing list of journey instructions.
        if ( changeStop == null ) {
            return journeyInstructionList;
        }
        //2. Get the next departures for the current station.
        final LocalTime currentFinalTime = LocalTime.of(currentDateTime.getHour(), currentDateTime.getMinute());
        List<StopTime> stopTimes = stopTimeService.getDepartures(startStop.getName(), journeyRequest.getOperator(), DateUtils.convertLocalTimeToTime(currentFinalTime));
        JourneyInstruction firstRouteJourneyInstruction = travelOn(stopTimes.getFirst().getDestination(), journeyRequest.getOperator(), stopTimes.getFirst().getRouteNumber(), DateUtils.convertLocalTimeToTime(stopTimes.getFirst().getDepartureTime()), 10);
        currentDateTime = currentDateTime.plusMinutes(Duration.between(LocalTime.of(currentDateTime.getHour(), currentDateTime.getMinute()), stopTimes.getFirst().getDepartureTime()).toMinutes() + firstRouteJourneyInstruction.getDurationInMins());
        journeyInstructionList.add(firstRouteJourneyInstruction);
        //Check if we need to change or if we have already reached our destination.
        if ( isJourneyEnd(changeStop, journeyRequest.getTo()) ) {
            return journeyInstructionList;
        }
        //Calculate the journey instruction for changing.
        JourneyInstruction changeJourneyInstruction = new JourneyInstruction("CHANGE", DateUtils.convertLocalTimeToTime(currentDateTime.toLocalTime()), 2, "", null, changeStop);
        currentDateTime = currentDateTime.plusMinutes(changeJourneyInstruction.getDurationInMins());
        journeyInstructionList.add(changeJourneyInstruction);
        //3. Get the next departures for the change station.
        final LocalTime currentFinalTime2 = LocalTime.of(currentDateTime.getHour(), currentDateTime.getMinute());
        List<StopTime> secondStopTimes = stopTimeService.getDepartures(changeStop, journeyRequest.getOperator(), DateUtils.convertLocalTimeToTime(currentFinalTime2));
        //Calculate journey duration based on waiting time and actual travel time.
        JourneyInstruction secondRouteJourneyInstruction = travelOn(secondStopTimes.getFirst().getDestination(), journeyRequest.getOperator(), secondStopTimes.getFirst().getRouteNumber(), DateUtils.convertLocalTimeToTime(secondStopTimes.getFirst().getDepartureTime()), 19);
        currentDateTime = currentDateTime.plusMinutes(Duration.between(LocalTime.of(currentDateTime.getHour(), currentDateTime.getMinute()), secondStopTimes.getFirst().getDepartureTime()).toMinutes() + secondRouteJourneyInstruction.getDurationInMins());
        journeyInstructionList.add(secondRouteJourneyInstruction);
        //4. If the end point is not a station then we have to walk it.
        if ( !isJourneyEnd(endStop.getName(), journeyRequest.getTo()) ) {
            JourneyInstruction walkDestinationJourneyInstruction = walkFromNearestStopToDestination(journeyRequest.getOperator(), journeyRequest.getTo(), DateUtils.convertLocalTimeToTime(currentDateTime.toLocalTime()));
            if ( walkDestinationJourneyInstruction != null ) {
                journeyInstructionList.add(walkDestinationJourneyInstruction);
            }
        }
        //Return the list of journey instructions.
        return journeyInstructionList;
    }

    /**
     * Privater helper method to determine if we have reached our end destination.
     * @param stopName a <code>String</code> with the name of the stop that we are currently at.
     * @param destination a <code>String</code> with the place or stop we are trying to reach.
     * @return a <code>boolean</code> which is true iff we have reached our destination.
     */
    private boolean isJourneyEnd ( final String stopName, final String destination ) {
        return stopName.contentEquals(destination);
    }

    /**
     * Private helper method to start a journey by walking to the nearest stop for a particular operator.
     * @param operator a <code>String</code> with the name of the operator.
     * @param fromAddress a <code>String</code> with the address where we should walk from to the nearest stop.
     * @param time a <code>String</code> with the time when the journey should start.
     * @return a <code>JourneyInstruction</code> which wraps information about this stage of the journey.
     */
    private JourneyInstruction walkToNearestStop ( final String operator, final String fromAddress, final String time ) {
        //Find the nearest stop.
        Address address = suggestStopService.suggestNearestStopForThisAddress(operator, fromAddress);
        //If address is not equal to null, return journey instruction.
        if ( address != null ) {
            return new JourneyInstruction("WALK", time, address.getDurationInMins(), "",
                    null, address.getStop().getName());
        }
        //Otherwise return null.
        return null;
    }

    /**
     * Private helper method to end a journey by walking from the nearest stop to the destination for a particular operator.
     * @param operator a <code>String</code> with the name of the operator.
     * @param toAddress a <code>String</code> with the address where we should to from the nearest stop.
     * @param time a <code>String</code> with the time when the walking should start.
     * @return a <code>JourneyInstruction</code> which wraps information about this stage of the journey.
     */
    private JourneyInstruction walkFromNearestStopToDestination ( final String operator, final String toAddress, final String time ) {
        //Find the nearest stop.
        Address address = suggestStopService.suggestNearestStopForThisAddress(operator, toAddress);
        //If address is not equal to null, return journey instruction.
        if ( address != null ) {
            return new JourneyInstruction("WALK", time, address.getDurationInMins(), "",
                    null, toAddress);
        }
        //Otherwise return null.
        return null;
    }

    /**
     * Private helper method to travel on a particular route at a particular time to a particular destination.
     * @param destination a <code>String</code> where the journey on this route will end.
     * @param operator a <code>String</code> with the operator to use.
     * @param routeNumber a <code>String</code> with the route number to use.
     * @param time a <code>String</code> with the time that the route will depart.
     * @param durationInMins a <code>int</code> with the duration of the journey.
     * @return a <code>JourneyInstruction</code> which wraps information about this stage of the journey.
     */
    private JourneyInstruction travelOn ( final String destination, final String operator, final String routeNumber, final String time, final int durationInMins ) {
        return new JourneyInstruction("ROUTE", time, durationInMins, "",
                routeService.getRoutesByCompanyAndRouteNumber(operator, routeNumber).getFirst(), destination);
    }

    /**
     * Determine the stop where we can change to reach our final destination. In some cases this may
     * be already the final destination if we can reach it directly.
     * @param startStopName a <code>String</code> with our starting stop.
     * @param endStopName a <code>String</code> with the final destination we are trying to reach.
     * @param operator a <code>String</code> containing the operator to use.
     * @return a <code>String</code> containing the stop name where we can change or null if no change is possible.
     */
    private String determineChangeStop ( final String startStopName, final String endStopName, final String operator ) {
        //Get all start routes.
        List<String> startRoutes = stopTimeService.getAllRouteNumbersByStop(operator, startStopName);
        //Get all end routes.
        List<String> endRoutes = stopTimeService.getAllRouteNumbersByStop(operator, endStopName);
        // If the same route number is available, then we change at last stop.
        for ( String startRoute: startRoutes  ){
            for ( String endRoute: endRoutes ) {
                if ( startRoute.contentEquals(endRoute) ) {
                    return endStopName;
                }
            }
        }
        // Otherwise, we go through the routes and check if the stops exist.
        List<StopTime> stopTimesStartRoute = stopTimeService.getDepartures(startStopName, operator, DateUtils.convertLocalTimeToTime(LocalTime.now()));
        for ( StopTime stopTimeStartRoute: stopTimesStartRoute ) {
            if ( endRoutes.contains(stopTimeStartRoute.getRouteNumber()) ) {
                return stopTimeStartRoute.getStopName();
            }
        }
        //Otherwise return null indicating no change stop possible.
        return null;
    }

}
