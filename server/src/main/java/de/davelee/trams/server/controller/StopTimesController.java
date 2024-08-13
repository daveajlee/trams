package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.*;
import de.davelee.trams.server.request.GenerateStopTimesRequest;
import de.davelee.trams.server.response.PositionResponse;
import de.davelee.trams.server.response.StopTimeResponse;
import de.davelee.trams.server.response.StopTimesResponse;
import de.davelee.trams.server.service.*;
import de.davelee.trams.server.utils.DateUtils;
import de.davelee.trams.server.utils.StopTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.*;

/**
 * This class provides REST endpoints which provide operations associated with stop times in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/stopTimes")
@RequestMapping(value="/api/stopTimes")
public class StopTimesController {

    @Autowired
    private StopTimeService stopTimeService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private CompanyService companyService;

    /**
     * Get the current position of the vehicle.
     * @param company a <code>String</code> object containing the name of the company to return the vehicle for.
     * @param allocatedTour a <code>String</code> containing the allocated tour of the vehicle.
     * @param dateTime a <code>String</code> with the current date and time.
     * @param difficultyLevel a <code>String</code> with the difficulty level selected by the user.
     */
    @Operation(summary = "Get current position of a particular vehicle", description="Get current position of a particular vehicle")
    @GetMapping(value="/position")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description="Successfully got the position of vehicle"), @ApiResponse(responseCode="204",description="No vehicle found")})
    public ResponseEntity<PositionResponse> getPosition(final String company, final String allocatedTour, final String dateTime, final String difficultyLevel ) {
        if ( StringUtils.isBlank(company) || StringUtils.isBlank(allocatedTour) || StringUtils.isBlank(dateTime)) {
            return ResponseEntity.badRequest().build();
        }
        // Get the vehicle currently run - return no content if no vehicle is assigned.
        List<Vehicle> vehicles = vehicleService.retrieveVehiclesByCompanyAndAllocatedRouteAndAllocatedTour(company, allocatedTour.split("/")[0], allocatedTour);
        if ( vehicles.isEmpty() ) {
            return ResponseEntity.noContent().build();
        }
        //Now calculate the current position of the vehicle.
        Position position = stopTimeService.retrievePositionForAllocatedTour(company, allocatedTour, DateUtils.convertDateToLocalDateTime(dateTime),
                vehicles.getFirst().getDelayInMinutes());
        // Return the position.
        return ResponseEntity.ok(PositionResponse.builder()
                .stop(position.getStop())
                .destination(position.getDestination())
                .delay(position.getDelay())
                .company(position.getCompany()).build());
    }

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
                                                           final String date, final String endDate, final boolean departures, final boolean arrivals,
                                                           final Optional<String> scheduleNumber) {
        //First of all, check that all necessary parameters were filled and that at least one of departures or arrivals is true.
        if (StringUtils.isBlank(stopName) || StringUtils.isBlank(company) || StringUtils.isBlank(date)
                || (!departures && !arrivals)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //If end date is not empty or null, then set last date to the normal date to indicate no range otherwise end date.
        LocalDate lastDate = StringUtils.isBlank(endDate) ? DateUtils.convertDateToLocalDate(date) : DateUtils.convertDateToLocalDate(endDate);
        //Store the results of any service operations in a variable.
        List<StopTime> stopTimeList = new ArrayList<>();
        //Set the process date and start loop.
        LocalDate processDate = DateUtils.convertDateToLocalDate(date);
        //Do this loop until the process date matches end date.
        do {
            //If departures and a starting time specified then get departures.
            if (departures && startingTime.isPresent()) {
                stopTimeList.addAll(stopTimeService.getDepartures(stopName, company, startingTime.get(), scheduleNumber.isPresent() ? scheduleNumber.get() : ""));
            }
            //Otherwise arrivals and starting time then get arrivals.
            else if (arrivals && startingTime.isPresent()) {
                stopTimeList.addAll(stopTimeService.getArrivals(stopName, company, startingTime.get(), scheduleNumber.isPresent() ? scheduleNumber.get() : ""));
            }
            //In the final case return all departures for the specified date.
            else if (departures) {
                stopTimeList.addAll(stopTimeService.getDeparturesByDate(stopName, company, DateUtils.convertLocalDateToDate(processDate), scheduleNumber.isPresent() ? scheduleNumber.get() : ""));
            }
            //Now increment the process date for next iteration if required.
            processDate = processDate.plusDays(1);
        } while ( ! processDate.isAfter(lastDate) );
        //If the list is empty or null then return no content.
        if ( stopTimeList.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //Now do the proessing into correct Response objects and return.
        StopTimeResponse[] stopTimeResponses = new StopTimeResponse[stopTimeList.size()];
        for ( int i = 0; i < stopTimeResponses.length; i++ ) {
            stopTimeResponses[i] = StopTimeResponse.builder()
                    .arrivalTime(DateUtils.convertLocalTimeToTime(stopTimeList.get(i).getArrivalTime()))
                    .departureTime(DateUtils.convertLocalTimeToTime(stopTimeList.get(i).getDepartureTime()))
                    .destination(stopTimeList.get(i).getDestination())
                    .company(stopTimeList.get(i).getCompany())
                    .journeyNumber(stopTimeList.get(i).getJourneyNumber())
                    .operatingDays(StopTimeUtils.convertOperatingDaysToString(stopTimeList.get(i).getOperatingDays()))
                    .routeNumber(stopTimeList.get(i).getRouteNumber())
                    .scheduleNumber(Integer.parseInt(stopTimeList.get(i).getService().getRouteSchedule().getScheduleId()))
                    .validFromDate(DateUtils.convertLocalDateTimeToDate(stopTimeList.get(i).getValidFromDate()))
                    .validToDate(DateUtils.convertLocalDateTimeToDate(stopTimeList.get(i).getValidToDate()))
                    .stopName(stopTimeList.get(i).getStopName())
                    .build();
        }
        return ResponseEntity.ok(StopTimesResponse.builder()
                .count((long) stopTimeResponses.length)
                .stopTimeResponses(stopTimeResponses).build());
    }

    /**
     * Return a model of the next departures for a particular stop and operator. The number of next departures to be
     * retrieved must also be supplied. Optionally a route can be supplied to only display departures for a particular
     * route. Further the language can be supplied for formatting purposes.
     * @param stop a <code>String</code> with the name of the stop to retrieve departures for.
     * @param numDepartures a <code>int</code> with the number of next departures to return.
     * @param operator a <code>String</code> with the name of the operator to retrieve departures for.
     * @param language a <code>String</code> with the language code e.g. DE or UK to use for formatting purposes.
     * @param route a <code>String</code> with the name of the route to retrieve departures for (optional)
     * @return a <code>RealTimeModel</code> with the next departures matching the specified criteria.
     */
    @GetMapping("/nextDepartures")
    @CrossOrigin
    @ResponseBody
    @Operation(summary = "View the next departures for a particular stop", description="Return the stop times.")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned stop times")})
    public RealTimeModel nextDepartures(
            final String stop,
            final int numDepartures,
            final String operator,
            final String language,
            final String route) {
        //Get the current date and time.
        RealTimeModel realTimeModel = new RealTimeModel();
        LocalDateTime localDateTime = LocalDateTime.now();
        realTimeModel.setTimestamp(DateUtils.convertLocalDateTimeToDate(localDateTime));
        List<RealTimeEntryModel> realTimeEntryModels = new ArrayList<>();
        //Get the departures from this stop - optionally only for a particular route.
        List<StopTime> stopTimes = stopTimeService.getDeparturesByDate(stop, operator, DateUtils.convertLocalDateTimeToDate(LocalDateTime.now()), "");
        stopTimes.stream().
                filter(stopTime -> stopTime.getDepartureTime().isAfter(LocalTime.of(localDateTime.getHour(), localDateTime.getMinute()))).
                forEach(stopTime -> {
                    RealTimeEntryModel realTimeEntryModel = new RealTimeEntryModel();
                    realTimeEntryModel.setMins((int) Duration.between(LocalTime.now(), stopTime.getDepartureTime()).getSeconds() / 60);
                    realTimeEntryModel.setRoute(routeService.getRoutesByCompanyAndRouteNumber(operator, stopTime.getRouteNumber()).getFirst());
                    if ( stopTime.getDestination().contentEquals(stop)) {
                        realTimeEntryModel.setDestination("Journey Terminates Here");
                    } else {
                        realTimeEntryModel.setDestination(stopTime.getDestination());
                    }
                    realTimeEntryModels.add(realTimeEntryModel);
                });
        //Sort the list now by minutes - this is necessary as Spring Data JPA only sorts by hours and not by minutes.
        Comparator<RealTimeEntryModel> byMinutes = Comparator.comparingInt(RealTimeEntryModel::getMins);
        Collections.sort(realTimeEntryModels, byMinutes);
        //Trim list to the maximum number of real time elements.
        realTimeModel.setRealTimeEntryModelList( (realTimeEntryModels.size() > numDepartures) ?
                realTimeEntryModels.subList(0, numDepartures) : realTimeEntryModels );
        return realTimeModel;
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
        // This is where we should now generate the schedules for the relevant timetable.
        if ( !(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidFromDate()).minusDays(1)).isAfter(companyService.getTime(generateStopTimesRequest.getCompany()))
                && !(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidToDate()).plusDays(1)).isBefore(companyService.getTime(generateStopTimesRequest.getCompany())) ) {
            stopTimeService.generateStopTimes(generateStopTimesRequest);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Delete all stop times currently stored in the database for a particular company.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param routeNumber a <code>String</code> containing the route number to delete stop times for (optional).
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete stop times", description="Delete all stop times")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted stop times")})
    public ResponseEntity<Void> deleteStopTimes (final String company, final Optional<String> routeNumber ) {
        //First of all, check if the company field is empty or null, then return bad request.
        if (StringUtils.isBlank(company)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Delete all stop times for this company.
        stopTimeService.deleteStopTimes(company, routeNumber);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
