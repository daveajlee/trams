package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.Stop;
import de.davelee.trams.server.repository.StopRepository;

import java.util.List;

/**
 * This class provides utility methods for processing related to stops in TraMS Server.
 * @author Dave Lee
 */
public class StopUtils {

    /**
     * This is a helper method which determines if the specified stop already exists in the database.
     * @param stopName a <code>String</code> object which contains the name of the stop that should be checked.
     * @param company a <code>String</code> object which contains the name of the company serving the stop.
     * @param stopRepository a <code>StopRepository</code> object containing the service responsible for retrieving data from the stops table.
     * @return a <code>boolean</code> which is true iff the stop has already been imported to the database.
     */
    public static boolean hasStopAlreadyBeenImported (final String stopName, final String company, final StopRepository stopRepository) {
        List<Stop> stops = stopRepository.findByCompanyAndName(company, stopName);
        if ( stops == null || stops.size() == 0 )
            return false;
        return true;
    }

}
