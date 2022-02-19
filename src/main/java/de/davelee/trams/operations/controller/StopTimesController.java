package de.davelee.trams.operations.controller;

import de.davelee.trams.operations.model.StopTime;
import de.davelee.trams.operations.request.GenerateStopTimesRequest;
import de.davelee.trams.operations.response.StopTimeResponse;
import de.davelee.trams.operations.response.StopTimesResponse;
import de.davelee.trams.operations.service.StopService;
import de.davelee.trams.operations.service.StopTimeService;
import de.davelee.trams.operations.utils.DateUtils;
import de.davelee.trams.operations.utils.Direction;
import de.davelee.trams.operations.utils.StopTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class provides REST endpoints which provide operations associated with stop times in the TraMS Operations API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/stopTimes")
@RequestMapping(value="/api/stopTimes")
public class StopTimesController {

    @Autowired
    private StopTimeService stopTimeService;

    @Autowired
    private StopService stopService;

    /**
     * Return the next departures and/or arrivals based on the supplied user parameters.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve departures/arrivals from.
     * @param company a <code>String</code> containing the name of the company to retrieve times for.
     * @param startingTime a <code>String</code> containing the time to start retrieving departures/arrivals from which may be null if rest of day should be returned.
     * @param date a <code>String</code> containing the date to retrieve stop times for in format yyyy-MM-dd. If the endDate parameter is supplied then this is the start date for the range.
     * @param endDate a <code>String</code> containing the end date of a range of dates to retrieve stop times for in format yyyy-MM-dd. This parameter may be empty or missing if no range is desired.
     * @param departures a <code>boolean</code> which is true iff departure times should be returned.
     * @param arrivals a <code>boolean</code> which is true iff arrivals times should be returned.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if none were found.
     */
    @GetMapping("/")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "Get stop times", description="Return the stop times.")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned stop times")})
    public ResponseEntity<StopTimesResponse> getStopTimes (final String stopName, final String company, final Optional<String> startingTime,
                                                           final String date, final String endDate, final boolean departures, final boolean arrivals) {
        //First of all, check that all necessary parameters were filled and that at least one of departures or arrivals is true.
        if (StringUtils.isBlank(stopName) || StringUtils.isBlank(company) || StringUtils.isBlank(date)
                || (!departures && !arrivals)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //If end date is not empty or null, then set last date to the normal date to indicate no range otherwise end date.
        LocalDate lastDate = StringUtils.isBlank(endDate) ? LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")) : LocalDate.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //Store the results of any service operations in a variable.
        List<StopTime> stopTimeList = new ArrayList<>();
        //Set the process date and start loop.
        LocalDate processDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //Do this loop until the process date matches end date.
        do {
            //If departures and a starting time specified then get departures.
            if (departures && startingTime.isPresent()) {
                stopTimeList = stopTimeService.getDepartures(stopName, company, startingTime.get());
            }
            //Otherwise arrivals and starting time then get arrivals.
            else if (arrivals && startingTime.isPresent()) {
                stopTimeList = stopTimeService.getArrivals(stopName, company, startingTime.get());
            }
            //In the final case return all departures for the specified date.
            else if (departures) {
                stopTimeList = stopTimeService.getDeparturesByDate(stopName, company, processDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            //Now increment the process date for next iteration if required.
            processDate = processDate.plusDays(1);
        } while ( ! processDate.isAfter(lastDate) );
        //If the list is empty or null then return no content.
        if ( stopTimeList.size() == 0 ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Now do the proessing into correct Response objects and return.
        StopTimeResponse[] stopTimeResponses = new StopTimeResponse[stopTimeList.size()];
        for ( int i = 0; i < stopTimeResponses.length; i++ ) {
            stopTimeResponses[i] = StopTimeResponse.builder()
                    .arrivalTime(stopTimeList.get(i).getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .departureTime(stopTimeList.get(i).getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                    .destination(stopTimeList.get(i).getDestination())
                    .company(stopTimeList.get(i).getCompany())
                    .journeyNumber(stopTimeList.get(i).getJourneyNumber())
                    .operatingDays(StopTimeUtils.convertOperatingDaysToString(stopTimeList.get(i).getOperatingDays()))
                    .routeNumber(stopTimeList.get(i).getRouteNumber())
                    .validFromDate(stopTimeList.get(i).getValidFromDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .validToDate(stopTimeList.get(i).getValidToDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .stopName(stopTimeList.get(i).getStopName())
                    .build();
        }
        return ResponseEntity.ok(StopTimesResponse.builder()
                .count((long) stopTimeResponses.length)
                .stopTimeResponses(stopTimeResponses).build());
    }

    /**
     * Generate stop time entries within a specified time and frequency and save them to the database.
     * @param generateStopTimesRequest a <code>GenerateStopTimesRequest</code> object containing the information to
     *                                 generate stop times including stops, distances and frequencies.
     * @return a <code>ResponseEntity</code> object containing the results of the endpoint.
     */
    @PostMapping("/generate")
    @CrossOrigin
    @Operation(summary = "Generate stop times", description="Generate the stop times for a route and frequency.")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned stop times")})
    public ResponseEntity<Void> generateStopTimes ( @RequestBody final GenerateStopTimesRequest generateStopTimesRequest ) {
        //Store list of stop times generated.
        List<StopTime> stopTimeList = new ArrayList<>();
        //The start time for outward trips.
        LocalTime randomStartTime = LocalTime.parse(generateStopTimesRequest.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        Random rand = new Random();
        randomStartTime = randomStartTime.plusMinutes(rand.nextInt(generateStopTimesRequest.getFrequency()));
        //Generate the first trip in outward direction.
        stopTimeList.addAll(generateFirstTrip(Direction.OUTGOING, generateStopTimesRequest, randomStartTime, 1));
        //Generate all remaining trips in outward direction.
        stopTimeList.addAll(generateRemainingTrips(Direction.OUTGOING, generateStopTimesRequest,
                        randomStartTime.plusMinutes(generateStopTimesRequest.getFrequency()),
                        Integer.parseInt(stopTimeList.get(stopTimeList.size()-1).getJourneyNumber())+1));
        //The start time for return trips.
        LocalTime randomStartReturnTime = LocalTime.parse(generateStopTimesRequest.getStartTime(), DateTimeFormatter.ofPattern("HH:mm"));
        randomStartReturnTime = randomStartReturnTime.plusMinutes(rand.nextInt(generateStopTimesRequest.getFrequency()));
        //Generate the first trip in return direction.
        stopTimeList.addAll(generateFirstTrip(Direction.RETURN, generateStopTimesRequest, randomStartReturnTime,Integer.parseInt(stopTimeList.get(stopTimeList.size()-1).getJourneyNumber())+1));
        //Generate all remaining trips in return direction.
        stopTimeList.addAll(generateRemainingTrips(Direction.RETURN, generateStopTimesRequest, randomStartReturnTime.plusMinutes(generateStopTimesRequest.getFrequency()),
                Integer.parseInt(stopTimeList.get(stopTimeList.size()-1).getJourneyNumber())+1));
        //Add all of the stop times in bulk to the database.
        return stopTimeService.addStopTimes(stopTimeList) ? ResponseEntity.ok().build() : ResponseEntity.status(500).build();
    }

    /**
     * Private helper method to generate all stop times for the first trip in a particular direction.
     * @param direction a <code>Direction</code> enum which can be either OUTGOING or RETURN.
     * @param generateStopTimesRequest a <code>GenerateStopTimesRequest</code> object containing the information to
     *                                 generate stop times including stops, distances and frequencies.
     * @param startTripTime a <code>LocalTime</code> object with the time to start to generate the trip from.
     * @param startJourneyNumber a <code>int</code> with the journey number to use for the first stop time generated.
     * @return a <code>List</code> of <code>StopTime</code> objects which have been generated automatically.
     */
    private List<StopTime> generateFirstTrip ( final Direction direction, final GenerateStopTimesRequest generateStopTimesRequest,
                                               final LocalTime startTripTime, final int startJourneyNumber ) {
        //Initialise journey number.
        int journeyNumber = startJourneyNumber;
        //Initilise time.
        LocalTime generateTime = startTripTime;
        //Create an empty list to add stop times to.
        List<StopTime> stopTimeList = new ArrayList<>();
        if ( direction == Direction.OUTGOING ) {
            //Generate first trip in the outward direction.
            stopTimeList.add(StopTime.builder()
                    .arrivalTime(generateTime)
                    .company(generateStopTimesRequest.getCompany())
                    .departureTime(generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[0]).getWaitingTime()))
                    .stopName(generateStopTimesRequest.getStopNames()[0])
                    .id(journeyNumber)
                    .journeyNumber("" + journeyNumber++)
                    .destination(generateStopTimesRequest.getStopNames()[generateStopTimesRequest.getStopNames().length - 1])
                    .routeNumber(generateStopTimesRequest.getRouteNumber())
                    .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                    .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                    .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                    .build());
            //Generate remaining stop times for the first trip.
            for (int i = 1; i < generateStopTimesRequest.getStopNames().length; i++) {
                generateTime = generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getDistances().get(generateStopTimesRequest.getStopNames()[i-1]));
                stopTimeList.add(StopTime.builder()
                        .arrivalTime(generateTime)
                        .company(generateStopTimesRequest.getCompany())
                        .departureTime(generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getWaitingTime()))
                        .stopName(generateStopTimesRequest.getStopNames()[i])
                        .id(journeyNumber)
                        .journeyNumber("" + journeyNumber++)
                        .destination(generateStopTimesRequest.getStopNames()[generateStopTimesRequest.getStopNames().length - 1])
                        .routeNumber(generateStopTimesRequest.getRouteNumber())
                        .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                        .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                        .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                        .build());
            }
        } else if ( direction == Direction.RETURN ) {
            //Generate first trip in the return direction.
            stopTimeList.add(StopTime.builder()
                    .arrivalTime(generateTime)
                    .company(generateStopTimesRequest.getCompany())
                    .departureTime(generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[generateStopTimesRequest.getStopNames().length-1]).getWaitingTime()))
                    .stopName(generateStopTimesRequest.getStopNames()[generateStopTimesRequest.getStopNames().length-1])
                    .id(journeyNumber)
                    .journeyNumber("" + journeyNumber++)
                    .destination(generateStopTimesRequest.getStopNames()[0])
                    .routeNumber(generateStopTimesRequest.getRouteNumber())
                    .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                    .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                    .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                    .build());
            //Generate remaining stop times for the first trip.
            for (int i = generateStopTimesRequest.getStopNames().length-2; i >= 0; i--) {
                generateTime = generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getDistances().get(generateStopTimesRequest.getStopNames()[i+1]));
                stopTimeList.add(StopTime.builder()
                        .arrivalTime(generateTime)
                        .company(generateStopTimesRequest.getCompany())
                        .departureTime(generateTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getWaitingTime()))
                        .stopName(generateStopTimesRequest.getStopNames()[i])
                        .id(journeyNumber)
                        .journeyNumber("" + journeyNumber++)
                        .destination(generateStopTimesRequest.getStopNames()[0])
                        .routeNumber(generateStopTimesRequest.getRouteNumber())
                        .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                        .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                        .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                        .build());
            }
        }
        //Return the list of stop times.
        return stopTimeList;
    }

    /**
     * Private helper method to generate all stop times for all trips apart from the first trip in a particular direction.
     * @param direction a <code>Direction</code> enum which can be either OUTGOING or RETURN.
     * @param generateStopTimesRequest a <code>GenerateStopTimesRequest</code> object containing the information to
     *                                 generate stop times including stops, distances and frequencies.
     * @param firstArrivalTime a <code>LocalTime</code> object with the time to start to generate the trip from.
     * @param startJourneyNumber a <code>int</code> with the journey number to use for the first stop time generated.
     * @return a <code>List</code> of <code>StopTime</code> objects which have been generated automatically.
     */
    private List<StopTime> generateRemainingTrips ( final Direction direction, final GenerateStopTimesRequest generateStopTimesRequest,
                                                    final LocalTime firstArrivalTime, final int startJourneyNumber ) {
        //Initialise journey number.
        int journeyNumber = startJourneyNumber;
        //Create an empty list to add stop times to.
        List<StopTime> stopTimeList = new ArrayList<>();
        //Store the last time after which no trips should be generated.
        LocalTime endTime = LocalTime.parse(generateStopTimesRequest.getEndTime(), DateTimeFormatter.ofPattern("HH:mm"));
        //Store the generate time.
        LocalTime generateTime = firstArrivalTime;
        //Process by direction.
        if ( direction == Direction.OUTGOING ) {
            //Generate stopTimes until the end time is reached.
            while (!generateTime.isAfter(endTime) && !generateTime.isBefore(firstArrivalTime)) {
                //Now store the start time of this trip.
                LocalTime startTripTime = LocalTime.of(generateTime.getHour(), generateTime.getMinute());
                //Generate the stopTimes for this trip.
                for (int i = 0; i < generateStopTimesRequest.getStopNames().length; i++) {
                    stopTimeList.add(StopTime.builder()
                            .arrivalTime(startTripTime)
                            .company(generateStopTimesRequest.getCompany())
                            .departureTime(startTripTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getWaitingTime()))
                            .stopName(generateStopTimesRequest.getStopNames()[i])
                            .id(journeyNumber)
                            .journeyNumber("" + journeyNumber++)
                            .destination(generateStopTimesRequest.getStopNames()[generateStopTimesRequest.getStopNames().length - 1])
                            .routeNumber(generateStopTimesRequest.getRouteNumber())
                            .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                            .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                            .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                            .build());
                    if ( i != generateStopTimesRequest.getStopNames().length-1 ) {
                        startTripTime = startTripTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getDistances().get(generateStopTimesRequest.getStopNames()[i+1]));
                    }
                }
                //After trip is finished then increment the generate time by the frequency.
                generateTime = generateTime.plusMinutes(generateStopTimesRequest.getFrequency());
            }
        } else if ( direction == Direction.RETURN ) {
            //Generate stopTimes until the end time is reached.
            while (!generateTime.isAfter(endTime) && !generateTime.isBefore(firstArrivalTime)) {
                //Now store the start time of this trip.
                LocalTime startTripTime = LocalTime.of(generateTime.getHour(), generateTime.getMinute());
                //Generate the stopTimes for this trip.
                for (int i = generateStopTimesRequest.getStopNames().length-1; i >= 0; i--) {
                    stopTimeList.add(StopTime.builder()
                            .arrivalTime(startTripTime)
                            .company(generateStopTimesRequest.getCompany())
                            .departureTime(startTripTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getWaitingTime()))
                            .stopName(generateStopTimesRequest.getStopNames()[i])
                            .id(journeyNumber)
                            .journeyNumber("" + journeyNumber++)
                            .destination(generateStopTimesRequest.getStopNames()[0])
                            .routeNumber(generateStopTimesRequest.getRouteNumber())
                            .operatingDays(StopTimeUtils.convertOperatingDays(generateStopTimesRequest.getOperatingDays()))
                            .validFromDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidFromDate()))
                            .validToDate(DateUtils.convertDateToLocalDate(generateStopTimesRequest.getValidToDate()))
                            .build());
                    if ( i != 0 ) {
                        startTripTime = startTripTime.plusMinutes(stopService.getStop(generateStopTimesRequest.getCompany(), generateStopTimesRequest.getStopNames()[i]).getDistances().get(generateStopTimesRequest.getStopNames()[i-1]));
                    }
                }
                //After trip is finished then increment the generate time by the frequency.
                generateTime = generateTime.plusMinutes(generateStopTimesRequest.getFrequency());
            }
        }
        //Return the list of stop times.
        return stopTimeList;
    }

}
