package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.StopTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the stop time objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface StopTimeRepository extends MongoRepository<StopTime, String> {

    /**
     * Find all departures and/or arrivals for a particular company and stop name.
     * @param company a <code>String</code> containing the name of the company to find departures and/or arrivals for.
     * @param stopName a <code>String</code> containing the name of the stop to find departures and/or arrivals for.
     * @return a <code>List</code> of <code>StopTime</code> objects containing the deparatures and/or arrivals matching the stop name.
     */
    List<StopTime> findByCompanyAndStopName(@Param("company") final String company, @Param("stopName") final String stopName );

    /**
     * Count all stop times matching a particular stop, company and route number.
     * @param company a <code>String</code> containing the name of the company to retrieve stop times for.
     * @param stopName a <code>String</code> containing the name of the stop to find departures and/or arrivals for.
     * @param routeNumber a <code>String</code> containing the route number to retrieve stop times for.
     * @return a <code>long</code> with the number of stop times for this route number.
     */
    long countByCompanyAndStopNameAndRouteNumber(@Param("company") final String company, @Param("stopName") final String stopName, @Param("routeNumber") final String routeNumber );

}
