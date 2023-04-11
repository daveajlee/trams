package de.davelee.trams.server.utils;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.repository.RouteRepository;

import java.util.List;

/**
 * This class provides utility methods for processing related to routes in TraMS Server.
 * @author Dave Lee
 */
public class RouteUtils {

    /**
     * This is a helper method which determines if the specified route already exists in the database.
     * @param routeNumber a <code>String</code> object which contains the route number that should be checked.
     * @param company a <code>String</code> object which contains the name of the company serving the stop.
     * @param routeRepository a <code>RouteRepository</code> object containing the service responsible for retrieving data from the routes table.
     * @return a <code>boolean</code> which is true iff the route number has already been imported to the database.
     */
    public static boolean hasRouteAlreadyBeenImported (final String routeNumber, final String company, final RouteRepository routeRepository) {
        List<Route> routes = routeRepository.findByCompanyAndRouteNumber(company, routeNumber);
        if ( routes == null || routes.size() == 0) {
            return false;
        }
        return true;
    }

}
