package de.davelee.trams.crm.repository;

import de.davelee.trams.crm.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Interface class for database operations on order - uses Spring Data Mongo.
 * @author Dave Lee
 */
public interface OrderRepository extends MongoRepository<Order, Long> {

}
