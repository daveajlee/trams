package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Timetable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the timetable objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface TimetableRepository extends MongoRepository<Timetable, String> {

    /**
     * Return a list of timetables owned by this company and have a route number starting with the supplied route number.
     * @param company a <code>String</code> containing the name of the company to retrieve timetables for.
     * @param routeNumber a <code>String</code> containing the route number that the timetable should contain as a minimum.
     * @return a <code>List</code> of <code>Timetable</code> objects containing the matching timetables (usually one).
     */
    List<Timetable> findByCompanyAndRouteNumberStartsWith (final String company, final String routeNumber);

}
