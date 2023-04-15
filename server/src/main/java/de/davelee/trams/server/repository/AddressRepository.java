package de.davelee.trams.server.repository;

import de.davelee.trams.server.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Methods to insert, find, count or delete addresses from the database using Spring Data JPA.
 * @author Dave Lee
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    /**
     * Find an address object based on the supplied operator and address as String.
     * @param addressOperator a <code>String</code> based on the operator.
     * @param address a <code>String</code> based on the address to find.
     * @return a <code>Address</code> object matching the address and operator.
     */
    public Address findByAddressOperatorAndAddress (@Param("addressOperator") final String addressOperator, @Param("address") final String address);

    /**
     * Find address objects based on the supplied operator.
     * @param addressOperator a <code>String</code> based on the operator.
     * @return a <code>List</code> of <code>Address</code> objects matching the operator.
     */
    public List<Address> findByAddressOperator (@Param("addressOoperator") final String addressOperator);

    /**
     * Count how many address objects exist for the supplied operator.
     * @param addressOperator a <code>String</code> based on the operator.
     * @return a <code>long</code> with the number of address objects for this operator.
     */
    public long countByAddressOperator (@Param("addressOperator") final String addressOperator);

    /**
     * Delete all address objects for a specified operator.
     * @param addressOperator a <code>String</code> based on the operator.
     * @return a <code>List</code> of <code>Address</code> objects matching the operator which have been deleted.
     */
    public List<Address> deleteByAddressOperator (@Param("addressOperator") final String addressOperator);

}
