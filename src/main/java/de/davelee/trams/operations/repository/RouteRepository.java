package de.davelee.trams.operations.repository;

import de.davelee.trams.operations.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * This class enables as part of Spring Data access to the route objects stored in the Mongo DB.
 * @author Dave Lee
 */
public interface RouteRepository extends MongoRepository<Route, String> {

    List<Route> findByCompany (final String company);

    List<Route> findByCompanyAndRouteNumber (final String company, final String routeNumber);

}
