package de.davelee.trams.server.service;

import de.davelee.trams.server.model.*;
import de.davelee.trams.server.repository.StopTimeRepository;
import de.davelee.trams.server.request.GenerateStopTimesRequest;
import de.davelee.trams.server.utils.DateUtils;
import de.davelee.trams.server.utils.FrequencyPatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class provides a service for managing stop times in Trams Server.
 * @author Dave Lee
 */
@Service
public class StopTimeService {

    @Autowired
    private StopTimeRepository stopTimeRepository;

    @Autowired
    private CompanyService companyService;

    /**
     * Add the supplied list of stop times to the database.
     * @param stopTimeList a <code>List</code> of <code>StopTime</code> objects containing the list of stop times to be added.
     * @return a <code>boolean</code> which is true iff all of the stop times were added successfully.
     */
    public boolean addStopTimes ( final List<StopTime> stopTimeList ) {
        //Attempt to add all of the stop times to the database.
        for ( StopTime stopTime : stopTimeList ) {
            if ( stopTimeRepository.save(stopTime) == null ) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the next 3 departures for this stop within the next 2 hours.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve departures from.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param startingTime a <code>String</code> containing the time to start retrieving departures from which may be null if current time should be used.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if no departures were found or there
     * are no departures in next 2 hours.
     */
    public List<StopTime> getDepartures (final String stopName, final String company, final String startingTime, final String scheduleNumber ) {
        return getTimes(stopName, company, startingTime, "Departure", Comparator.comparing(StopTime::getDepartureTime), scheduleNumber);
    }

    /**
     * Return the next 3 arrivals for this stop within the next 2 hours.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve arrivals for.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param startingTime a <code>String</code> containing the time to start retrieving arrivals from which may be null if current time should be used.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if the stop arrivals were not found or there
     * are no arrivals in next 2 hours.
     */
    public List<StopTime> getArrivals (final String stopName, final String company, final String startingTime, final String scheduleNumber ) {
        return getTimes(stopName, company, startingTime, "Arrival", Comparator.comparing(StopTime::getArrivalTime), scheduleNumber);
    }

    /**
     * Return the next 3 stop times (either departures or arrivals) for this stop within the next 2 hours.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve stop times for.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param startingTime a <code>String</code> containing the time to start retrieving stop times from which may be null if current time should be used.
     * @param type a <code>String</code> which can be either Departure to return the departures or Arrival to return the arrivals.
     * @param comparator a <code>Comparator</code> of <code>StopTime</code> which defines how the departures or arrivals will be sorted.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if the stop times were not found or there
     *       are no stop times in next 2 hours.
     */
    public List<StopTime> getTimes (final String stopName, final String company, final String startingTime, final String type, final Comparator<StopTime> comparator, final String scheduleNumber ) {
        //Initial time to starting time or current time if no starting time was supplied.
        final LocalTime time = startingTime != null ? convertToLocalTime(startingTime) : LocalTime.now();

        //Special processing if between 22 and 24 - otherwise normal processing.
        if ( time.isAfter(LocalTime.of(21,59))) {
            //First of all get stop times between now and midnight.
            List<StopTime> stopTimes = stopTimeRepository.findByCompanyAndStopName(company, stopName).stream()
                    //Filter stop times which do not run on this day.
                    .filter(stopTime -> stopTime.getOperatingDays().checkIfOperatingDay(LocalDateTime.now()))
                    //Filter so that stop times in the past are not shown.
                    .filter(stopTime -> !stopTime.getTime(type).isBefore(time))
                    //Filter remove stop times which lie much further in the future
                    .filter(stopTime -> !stopTime.getTime(type).isAfter(LocalTime.of(23,59)))
                    //Sort the stop times by time.
                    .sorted(comparator)
                    //Only show next 3 stop times.
                    .limit(3)
                    .collect(Collectors.toList());
            //If we already have 3 stop times then no need to look further.
            if ( stopTimes.size() == 3 ) {
                return stopTimes;
            }
            //Otherwise add the remaining stop times from the next day.
            stopTimes.addAll(stopTimeRepository.findByCompanyAndStopName(company, stopName).stream()
                    //Filter remove stop times which lie much further in the future
                    .filter(stopTime -> !stopTime.getTime(type).isAfter(time.plusHours(2)))
                    //Sort the stop times by time.
                    .sorted(comparator)
                    //Only show next 3 stop times.
                    .limit(3- stopTimes.size())
                    .collect(Collectors.toList()));
            // If schedule number is not empty then filter the schedule number.
            if ( !scheduleNumber.equalsIgnoreCase("")) {
                stopTimes = stopTimes.stream()
                        .filter(stopTime -> Integer.parseInt(stopTime.getService().getRouteSchedule().getScheduleId()) == Integer.parseInt(scheduleNumber.split("/")[1]))
                        .collect(Collectors.toList());
            }
            return stopTimes;
        }
        //Normal processing
        List<StopTime> filteredStopTimes = stopTimeRepository.findByCompanyAndStopName(company, stopName).stream()
                //Filter stop times which do not run on this day.
                .filter(stopTime -> stopTime.getOperatingDays().checkIfOperatingDay(LocalDateTime.now()))
                //Filter so that stop times in the past are not shown.
                .filter(stopTime -> !stopTime.getTime(type).isBefore(time))
                //Filter remove stop times which lie much further in the future
                .filter(stopTime -> !stopTime.getTime(type).isAfter(time.plusHours(2)))
                //Sort the stop times by time.
                .sorted(comparator)
                .collect(Collectors.toList());

        //Remove any duplicates.
        for (int i = 0; i < filteredStopTimes.size()-1; i++ ) {
            for (int j = (i+1); j < filteredStopTimes.size(); j++ ) {
                if ( filteredStopTimes.get(i).getDestination().contentEquals(filteredStopTimes.get(j).getDestination()) &&
                        filteredStopTimes.get(i).getTime(type).compareTo(filteredStopTimes.get(j).getTime(type)) == 0) {
                    filteredStopTimes.remove(i);
                }
            }
        }

        //Only show next 3 stop times and remove.
        return filteredStopTimes.stream().limit(3).collect(Collectors.toList());
    }

    /**
     * Return all stop times for this stop with a departure today.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve stop times for.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param date a <code>String</code> containing the date to retrieve stop times for in format yyyy-MM-dd.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if the stop times were
     * not found or there are no stop times on this date.
     */
    public List<StopTime> getDeparturesByDate (final String stopName, final String company, final String date, final String scheduleNumber ) {
        //Set the date as a local date
        LocalDateTime departureDate = DateUtils.convertDateToLocalDateTime(date);
        if ( departureDate != null ) {
            //Return the stop times between now and midnight with the filter criteria.
            List<StopTime> stopTimes = stopTimeRepository.findByCompanyAndStopName(company, stopName).stream()
                //Filter stop times which do not run on this day.
                .filter(stopTime -> stopTime.getOperatingDays().checkIfOperatingDay(departureDate))
                //Filter stop times that are before the valid from date.
                .filter(stopTime -> departureDate.isAfter(stopTime.getValidFromDate()))
                //Filter remove stop times are after the valid to date.
                .filter(stopTime -> departureDate.isBefore(stopTime.getValidToDate()))
                //Sort the stop times by time.
                .sorted(Comparator.comparing(StopTime::getDepartureTime))
                //Collect list as output.
                .collect(Collectors.toList());
            // If schedule number is not empty then filter the schedule number.
            if ( !scheduleNumber.equalsIgnoreCase("")) {
                stopTimes = stopTimes.stream()
                        .filter(stopTime -> Integer.parseInt(stopTime.getService().getRouteSchedule().getScheduleId()) == Integer.parseInt(scheduleNumber.split("/")[1]))
                        .collect(Collectors.toList());
            }
            return stopTimes;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Count all stop times for the supplied company, stop name and route number.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve stop times for.
     * @param routeNumber a <code>String</code> containing the route number to retrieve stop times for.
     * @return a <code>long</code> with the number of stop times for this route number.
     */
    public long countStopTimes ( final String company, final String stopName, final String routeNumber ) {
        return stopTimeRepository.countByCompanyAndStopNameAndRouteNumber(company, stopName, routeNumber);
    }

    /**
     * Get all route numbers for a particular company and stop.
     * @param company a <code>String</code> containing the name of the company to find routes for.
     * @param stop a <code>String</code> containing the name of the stop.
     * @return a <code>List</code> of <code>String</code> objects containing all route numbers for this operator and stop.
     */
    public List<String> getAllRouteNumbersByStop (final String company, final String stop ) {
        List<String> routeNumbers = new ArrayList<>();
        for ( StopTime stopTime : stopTimeRepository.findByCompanyAndStopName(company, stop) ) {
            if ( !routeNumbers.contains(stopTime.getRouteNumber()) ) {
                routeNumbers.add(stopTime.getRouteNumber());
            }
        }
        return routeNumbers;
    }

    /**
     * This helper method contains a String in the format HH:mm to a <code>LocalTime</code> object.
     * @param time a <code>String</code> in the format HH:mm
     * @return a <code>LocalTime</code> object which contains the time.
     */
    private LocalTime convertToLocalTime ( final String time ) {
        String[] timeHoursMinArray = time.split(":");
        return LocalTime.of(Integer.parseInt(timeHoursMinArray[0]), Integer.parseInt(timeHoursMinArray[1]));
    }

    /**
     * Delete all stop times currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to delete stop times for.
     * @param routeNumber a <code>String</code> object containing the route number to be deleted (optional).
     */
    public void deleteStopTimes(final String company, final Optional<String> routeNumber) {
        if (routeNumber.isPresent() ) {
            List<StopTime> stopTimes = stopTimeRepository.findByCompanyAndRouteNumber(company, routeNumber.get());
            stopTimes.forEach(stopTimeRepository::delete);
        } else {
            List<StopTime> stopTimes = stopTimeRepository.findByCompany(company);
            stopTimes.forEach(stopTimeRepository::delete);
        }
    }

    /**
     * Generate the stop times.
     * @param generateStopTimesRequest the request to generate stop times.
     */
    public void generateStopTimes(final GenerateStopTimesRequest generateStopTimesRequest) {
        // Now we go through the tours which are the schedules.
        for (int j = 0; j < generateStopTimesRequest.getNumTours(); j++) {
            RouteSchedule routeSchedule = new RouteSchedule(generateStopTimesRequest.getRouteNumber(), "" + (j + 1));
            // Note the duration which is the frequency * num Tours
            int duration = generateStopTimesRequest.getFrequency() * generateStopTimesRequest.getNumTours();
            // Set the loop time to the starting time.
            LocalTime loopTime = DateUtils.convertTimeToLocalTime(generateStopTimesRequest.getStartTime());
            int serviceCounter = 1;
            // Now repeat until we reach the end time.
            while (!loopTime.isAfter(DateUtils.convertTimeToLocalTime(generateStopTimesRequest.getEndTime()))) {
                // Add an outgoing service.
                generateService(generateStopTimesRequest, loopTime, serviceCounter,(j+1), true,
                        routeSchedule);
                // Add half of duration to cover outgoing service.
                loopTime = loopTime.plusMinutes(duration / 2);
                // Increase service counter
                serviceCounter++;
                // Add return service.
                generateService(generateStopTimesRequest, loopTime, serviceCounter, (j+1), false,
                        routeSchedule);
                // Add half of duration to cover return service.
                loopTime = loopTime.plusMinutes((duration / 2));
                // Increase service counter
                serviceCounter++;
            }
        }
    }

    /**
     * This is a helper method to generate a service based on the information provided.
     * @param generateStopTimesRequest the request to generate stop times.
     * @param startTime a <code>LocalTime</code> object with the start time for generating stop times.
     * @param serviceNumber a <code>int</code> with the first service number to generate.
     * @param tourNumber a <code>int</code> with the second service number to generate.
     * @param outgoing a <code>boolean</code> which is true iff we are generating in the outgoing direction.
     * @param routeSchedule a <code>RouteSchedule</code> object matching the route schedule that we are currently generating.
     * @return the service that we generated as a <code>ServiceTrip</code> object.
     */
    public ServiceTrip generateService( final GenerateStopTimesRequest generateStopTimesRequest, final LocalTime startTime,
                                        final int serviceNumber, final int tourNumber, final boolean outgoing,
                                        final RouteSchedule routeSchedule) {
        // Now we start generating the services.
        String serviceId = "" + serviceNumber;
        List<Stop> stopList = new ArrayList<>();
        List<StopTime> stopTimeList = new ArrayList<>();
        // Go through stops for the route.
        int distance = 0;
        if ( outgoing ) {
            for ( int k = 0; k < generateStopTimesRequest.getStopNames().length; k++ ) {
                // Get the distance between this stop and the last stop.
                distance += (k == 0 ) ? getDistanceBetweenStop(generateStopTimesRequest.getStartStop(), generateStopTimesRequest.getStopNames()[k], generateStopTimesRequest.getStopDistances())
                        : getDistanceBetweenStop(generateStopTimesRequest.getStopNames()[k-1], generateStopTimesRequest.getStopNames()[k], generateStopTimesRequest.getStopDistances());
                stopList.add(Stop.builder().name(generateStopTimesRequest.getStopNames()[k]).build());
                stopTimeList.add(StopTime.builder()
                                .arrivalTime(startTime.plusMinutes(((tourNumber * generateStopTimesRequest.getFrequency()) + distance)))
                                .departureTime(startTime.plusMinutes(((tourNumber * generateStopTimesRequest.getFrequency()) + distance)))
                                .destination(generateStopTimesRequest.getEndStop())
                                .stopName(generateStopTimesRequest.getStopNames()[k])
                                .company(generateStopTimesRequest.getCompany())
                                .operatingDays(FrequencyPatternUtils.convertDaysOfOperation(generateStopTimesRequest.getOperatingDays().split(",")))
                                .validFromDate(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidFromDate()))
                                .validToDate(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidToDate()))
                                .routeNumber(generateStopTimesRequest.getRouteNumber())
                                .build());
            }
        } else {
            for ( int m = generateStopTimesRequest.getStopNames().length - 1; m >= 0; m-- ) {
                // Get the distance between this stop and the last stop.
                distance += ( m == generateStopTimesRequest.getStopNames().length - 1 ) ? getDistanceBetweenStop(generateStopTimesRequest.getStopNames()[m], generateStopTimesRequest.getEndStop(), generateStopTimesRequest.getStopDistances())
                        : getDistanceBetweenStop(generateStopTimesRequest.getStopNames()[m], generateStopTimesRequest.getStopNames()[m+1], generateStopTimesRequest.getStopDistances());
                stopList.add(Stop.builder().name(generateStopTimesRequest.getStopNames()[m]).build());
                stopTimeList.add(StopTime.builder()
                                .arrivalTime(startTime.plusMinutes(((tourNumber * generateStopTimesRequest.getFrequency()) + distance)))
                                .departureTime(startTime.plusMinutes(((tourNumber * generateStopTimesRequest.getFrequency()) + distance)))
                                .destination(generateStopTimesRequest.getStartStop())
                                .stopName(generateStopTimesRequest.getStopNames()[m])
                                .company(generateStopTimesRequest.getCompany())
                                .operatingDays(FrequencyPatternUtils.convertDaysOfOperation(generateStopTimesRequest.getOperatingDays().split(",")))
                                .validFromDate(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidFromDate()))
                                .validToDate(DateUtils.convertDateToLocalDateTime(generateStopTimesRequest.getValidToDate()))
                                .routeNumber(generateStopTimesRequest.getRouteNumber())
                                .build());
            }
        }
        // Now we need to do the end stop.
        ServiceTrip serviceTrip = ServiceTrip.builder()
                .serviceId(serviceId)
                .stopList(stopList)
                .routeSchedule(routeSchedule)
                .build();
        for ( StopTime stopTime : stopTimeList ) {
            stopTime.setService(serviceTrip);
            stopTimeRepository.save(stopTime);
        }
        return serviceTrip;
    }

    /**
     * Get the distance between two particular stops.
     * @param stop1 the first stop as a string to measure the distance to the second stop.
     * @param stop2 the second stop as a string to measure the distance from the first stop.
     * @param stopDistances the distances between stops in the format stopName:distance1,distance2 per entry.
     * @return the distance between the stops in minutes as a number.
     */
    public int getDistanceBetweenStop (final String stop1, final String stop2, final String[] stopDistances) {
        int stop1Pos = -1; int stop2Pos = -1;
        for ( var i = 0; i < stopDistances.length; i++ ) {
            if ( stopDistances[i].split(":")[0].contentEquals(stop1) ) {
                stop1Pos = i;
            } else if ( stopDistances[i].split(":")[0].contentEquals(stop2) ) {
                stop2Pos = i;
            }
        }
        if ( stop1Pos >= 0 && stop2Pos >= 0 ) {
            return Integer.parseInt(stopDistances[stop1Pos].split(":")[1].split(",")[stop2Pos]);
        }
        return -1;
    }

    /**
     * Retrieve the current position for a vehicle which is allocated to a specific tour.
     * @param company the company that we should retrieve the vehicle for.
     * @param allocatedTour the allocated tour in the format route / route schedule or tour number.
     * @param currentDateTime the current date and time as a <code>LocalDateTime</code> object.
     */
    public Position retrievePositionForAllocatedTour ( final String company, final String allocatedTour, final LocalDateTime currentDateTime) {
        // Get all of the stop times for this company and route number,
        List<StopTime> stopTimes = stopTimeRepository.findByCompanyAndRouteNumber(company, allocatedTour.split("/")[0]);
        // Filter any of the stop times that are not valid and that do not match the allocated tour,
        stopTimes = stopTimes.stream()
                // Filter out stop times that are not valid as not yet reached.
                .filter(stopTime -> stopTime.getValidFromDate().minusDays(1).isBefore(currentDateTime))
                // Filter out stop times that are not valid because they are past.
                .filter(stopTime -> stopTime.getValidToDate().plusDays(1).isAfter(currentDateTime))
                // Filter out services that are not run by the allocated tour,
                .filter(stopTime -> stopTime.getService().getRouteSchedule().getRouteNumberAndScheduleId().contentEquals(allocatedTour))
                //Sort the stop times by time.
                .sorted(Comparator.comparing(StopTime::getDepartureTime))
                //Collect list as output.
                .collect(Collectors.toList());
        // Now we should have all stop times for this allocated tour,
        // Go through the remaining stop times until we get the departure time that is after current time and then it is the previous one.
        for ( int i = 0; i < stopTimes.size(); i++ ) {
            if ( stopTimes.get(i).getDepartureTime().isAfter(currentDateTime.toLocalTime()) ) {
                // If i is 0, then we are still at depot since we have not started.
                if ( i == 0  ) {
                    return Position.builder()
                            .stop("Depot")
                            .destination("N/A")
                            .company(company).build();
                }
                // Otherwise we have the position.
                return Position.builder()
                        .stop(stopTimes.get(i-1).getStopName())
                        .destination(stopTimes.get(i-1).getDestination())
                        .company(stopTimes.get(i-1).getCompany()).build();
            }
        }
        return Position.builder()
                .stop("Depot")
                .destination("N/A")
                .company(company).build();
    }

}
