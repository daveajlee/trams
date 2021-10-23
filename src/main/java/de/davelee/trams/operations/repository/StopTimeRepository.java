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

}
