package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.model.StopTime;
import de.davelee.trams.server.repository.StopTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides a service for managing stop times in Trams Server.
 * @author Dave Lee
 */
@Service
public class StopTimeService {

    @Autowired
    private StopTimeRepository stopTimeRepository;

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
    public List<StopTime> getDepartures (final String stopName, final String company, final String startingTime ) {
        return getTimes(stopName, company, startingTime, "Departure", Comparator.comparing(StopTime::getDepartureTime));
    }

    /**
     * Return the next 3 arrivals for this stop within the next 2 hours.
     * @param stopName a <code>String</code> containing the name of the stop to retrieve arrivals for.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param startingTime a <code>String</code> containing the time to start retrieving arrivals from which may be null if current time should be used.
     * @return a <code>List</code> of <code>StopTime</code> objects which may be null if the stop arrivals were not found or there
     * are no arrivals in next 2 hours.
     */
    public List<StopTime> getArrivals (final String stopName, final String company, final String startingTime ) {
        return getTimes(stopName, company, startingTime, "Arrival", Comparator.comparing(StopTime::getArrivalTime));
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
    public List<StopTime> getTimes (final String stopName, final String company, final String startingTime, final String type, final Comparator<StopTime> comparator ) {
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
    public List<StopTime> getDeparturesByDate (final String stopName, final String company, final String date ) {
        //Set the date as a local date
        LocalDateTime departureDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        //Return the stop times between now and midnight with the filter criteria.
        return stopTimeRepository.findByCompanyAndStopName(company, stopName).stream()
                //Filter stop times which do not run on this day.
                .filter(stopTime -> stopTime.getOperatingDays().checkIfOperatingDay(departureDate))
                //Filter stop times that are before the valid from date.
                .filter(stopTime -> stopTime.getValidFromDate().minusDays(1).isBefore(departureDate))
                //Filter remove stop times are after the valid to date.
                .filter(stopTime -> stopTime.getValidToDate().plusDays(1).isAfter(departureDate))
                //Sort the stop times by time.
                .sorted(Comparator.comparing(StopTime::getDepartureTime))
                //Collect list as output.
                .collect(Collectors.toList());
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

}
