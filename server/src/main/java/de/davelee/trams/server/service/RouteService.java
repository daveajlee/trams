package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Route;
import de.davelee.trams.server.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for managing routes in Trams Server.
 * @author Dave Lee
 */
@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Add the supplied route to the database.
     * @param route a <code>Route</code> object containing the information about the route to be added.
     * @return a <code>boolean</code> which is true iff the route was added successfully.
     */
    public boolean addRoute ( final Route route) {
        //Attempt to add the route to the database.
        return routeRepository.save(route) != null;
    }

    /**
     * Return all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    public List<Route> getRoutesByCompany ( final String company ) {
        return routeRepository.findByCompany(company);
    }

    /**
     * Return all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @param routeNumber a <code>String</code> containing the route number to search for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database matching the criteria.
     */
    public List<Route> getRoutesByCompanyAndRouteNumber ( final String company, final String routeNumber ) {
        return routeRepository.findByCompanyAndRouteNumber(company, routeNumber);
    }

    /**
     * Delete a single route from the database.
     * @param route a <code>Route</code> object which should be deleted from the database.
     */
    public void deleteRoute ( final Route route ) {
        routeRepository.delete(route);
    }


}
