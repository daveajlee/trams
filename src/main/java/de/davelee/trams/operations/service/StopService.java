package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.Stop;
import de.davelee.trams.operations.repository.StopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for managing stops in Trams Operations.
 * @author Dave Lee
 */
@Service
public class StopService {

    @Autowired
    private StopRepository stopRepository;

    /**
     * Add the supplied stop to the database.
     * @param stop a <code>Stop</code> object containing the information about the stop to be added.
     * @return a <code>boolean</code> which is true iff the stop was added successfully.
     */
    public boolean addStop ( final Stop stop) {
        //Attempt to add the stop to the database.
        return stopRepository.save(stop) != null;
    }

    /**
     * Return all stops currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to return stops for.
     * @return a <code>List</code> of <code>Stop</code> objects which may be null if there are no stops in the database
     * matching the specified company.
     */
    public List<Stop> getStopsByCompany(final String company) {
        return stopRepository.findByCompany(company);
    }

    /**
     * Return a particular stop stored in the database which matches the specified company and stop name.
     * @param company a <code>String</code> object containing the name of the company which should match.
     * @param stopName a <code>String</code> object containing the name of the stop which should match.
     * @return a <code>Stop</code> object which may be null if no stop is found matching the company and the stop name.
     */
    public Stop getStop ( final String company, final String stopName ) {
        List<Stop> stops = stopRepository.findByCompanyAndName(company, stopName);
        return stops != null && stops.size() == 1 ? stops.get(0) : null;
    }

    /**
     * Delete all stops currently stored in the database for the specified company.
     * @param company a <code>String</code> object containing the name of the company to return stops for.
     */
    public void deleteStops(final String company) {
        List<Stop> stops = getStopsByCompany(company);
        stops.forEach(stopRepository::delete);
    }

}
