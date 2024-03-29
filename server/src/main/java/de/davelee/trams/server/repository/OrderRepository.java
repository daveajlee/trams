package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface class for database operations on order - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface OrderRepository extends MongoRepository<Order, Long> {

}
