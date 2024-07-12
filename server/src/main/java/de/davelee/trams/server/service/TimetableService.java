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

    /**
     * Retrieve all timetables starting with the supplied company name, route number and timetable name from the database.
     * @param company a <code>String</code> with the name of the company to search for.
     * @param routeNumber a <code>String</code> with the route number to search for.
     * @param name a <code>Stringt</code> with the name of the timetable to search for.
     * @return a <code>List</code> of <code>Driver</code> objects matching the supplied criteria.
     */
    public List<Timetable> retrieveTimetablesByCompanyAndRouteNumberAndName (final String company, final String routeNumber, final String name) {
        //Return all matching timetables
        return timetableRepository.findByCompanyAndRouteNumberAndName(company, routeNumber, name);
    }

    /**
     * Retrieve all timetables for a particular company from the database for all types.
     * @param company a <code>String</code> with the company to search for.
     * @return a <code>List</code> of <code>Timetable</code> objects.
     */
    public List<Timetable> retrieveTimetablesByCompany ( final String company) {
        //Return the timetables found.
        return timetableRepository.findByCompany(company);
    }

    /**
     * Delete all timetables currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to delete timetables for.
     */
    public void deleteTimetables(final String company) {
        List<Timetable> timetables = retrieveTimetablesByCompany(company);
        timetables.forEach(timetableRepository::delete);
    }

    /**
     * Delete all timetables matching the supplied name and route number currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to delete timetables for.
     * @param name a <code>String</code> containing the name of the timetable to delete.
     * @param routeNumber a <code>String</code> containing the route number of the timetable to delete.
     */
    public void deleteTimetable(final String company, final String name, final String routeNumber) {
        List<Timetable> timetables = timetableRepository.findByCompanyAndRouteNumberAndName(company, routeNumber, name);
        timetables.forEach(timetableRepository::delete);
    }

}
