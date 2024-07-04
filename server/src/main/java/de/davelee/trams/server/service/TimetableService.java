package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Timetable;
import de.davelee.trams.server.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for managing timetables in Trams Server.
 * @author Dave Lee
 */
@Service
public class TimetableService {

    @Autowired
    private TimetableRepository timetableRepository;

    /**
     * Add the supplied timetable to the database.
     * @param timetable <code>Timetabler</code> object containing the information about the timetable to be added.
     * @return a <code>boolean</code> which is true iff the timetable was added successfully.
     */
    public boolean addTimetable(final Timetable timetable) {
        //If the timetable is valid, then attempt to add driver to db.
        return timetableRepository.insert(timetable) != null;
    }

    /**
     * Retrieve all timetables starting with the supplied company name and route number from the database.
     * @param company a <code>String</code> with the name of the company to search for.
     * @param routeNumber a <code>String</code> with the route number to search for.
     * @return a <code>List</code> of <code>Driver</code> objects matching the supplied criteria.
     */
    public List<Timetable> retrieveTimetablesByCompanyAndRouteNumber (final String company, final String routeNumber) {
        //Return all matching timetables
        return timetableRepository.findByCompanyAndRouteNumberStartsWith(company, routeNumber);
    }

}
