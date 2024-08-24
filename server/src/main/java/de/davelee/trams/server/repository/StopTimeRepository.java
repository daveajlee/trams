package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.StopTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the stop time objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface StopTimeRepository extends MongoRepository<StopTime, Long> {

    /**
     * Find all departures and/or arrivals for a particular company and stop name.
     * @param company a <code>String</code> containing the name of the company to find departures and/or arrivals for.
     * @param stopName a <code>String</code> containing the name of the stop to find departures and/or arrivals for.
     * @return a <code>List</code> of <code>StopTime</code> objects containing the deparatures and/or arrivals matching the stop name.
     */
    List<StopTime> findByCompanyAndStopName(@Param("company") final String company, @Param("stopName") final String stopName );

    /**
     * Find all departures and/or arrivals for a particular company and routeNumber.
     * @param company a <code>String</code> containing the name of the company to find departures and/or arrivals for.
     * @param routeNumber a <code>String</code> containing the route number to find departures and/or arrivals for.
     * @return a <code>List</code> of <code>StopTime</code> objects containing the departures and/or arrivals matching the route number.
     */
    List<StopTime> findByCompanyAndRouteNumber(@Param("company") final String company, @Param("routeNumber") final String routeNumber );

    /**
     * Count all stop times matching a particular stop, company and route number.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param stopName a <code>String</code> containing the name of the stop to find departures and/or arrivals for.
     * @param routeNumber a <code>String</code> containing the route number to retrieve stop times for.
     * @return a <code>long</code> with the number of stop times for this route number.
     */
    long countByCompanyAndStopNameAndRouteNumber(@Param("company") final String company, @Param("stopName") final String stopName, @Param("routeNumber") final String routeNumber );

    /**
     * Return a list of stop times owned by this company.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @return a <code>List</code> of <code>StopTime</code> objects containing the matching stop times.
     */
    List<StopTime> findByCompany (final String company);
}
