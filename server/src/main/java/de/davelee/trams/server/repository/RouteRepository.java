package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the route objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface RouteRepository extends MongoRepository<Route, String> {

    /**
     * Return a list of routes run by this company.
     * @param company a <code>String</code> containing the name of the company to retrieve routes for.
     * @return a <code>List</code> of <code>Route</code> objects containing the matching routes.
     */
    List<Route> findByCompany (final String company);

    /**
     * Return a list of routes run by this company and this route number (should usually be 1).
     * @param company a <code>String</code> containing the name of the company to retrieve routes for.
     * @param routeNumber a <code>String</code> containing the route number to retrieve.
     * @return a <code>List</code> of <code>Route</code> objects containing the matching routes (usually 1).
     */
    List<Route> findByCompanyAndRouteNumber (final String company, final String routeNumber);

}
