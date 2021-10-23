package de.davelee.trams.operations.service;

import de.davelee.trams.operations.model.Route;
import de.davelee.trams.operations.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides a service for managing routes in Trams Operations.
 * @author Dave Lee
 */
@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    /**
     * Return all routes for the specified company that are currently stored in the database.
     * @param company a <code>String</code> containing the name of the company to search for.
     * @return a <code>List</code> of <code>Route</code> objects which may be null if there are no routes in the database.
     */
    public List<Route> getRoutesByCompany ( final String company ) {
        return routeRepository.findByCompany(company);
    }


}
