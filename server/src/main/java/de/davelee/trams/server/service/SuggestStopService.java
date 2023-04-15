package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Address;
import de.davelee.trams.server.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides operations to suggest stops which are near addresses.
 * @author Dave Lee
 */
@Service
public class SuggestStopService {

    @Autowired
    private AddressRepository addressStopRepository;

    /**
     * Suggest the name of a stop near to the supplied address for the supplied operator.
     * @param operator a <code>String</code> containing the name of the operator.
     * @param address a <code>String</code> containing the address to be found.
     * @return a <code>String</code> containing the name of the stop that is suggested.
     */
    public String suggestNearestStop ( final String operator, final String address ) {
        Address addressStop = suggestNearestStopForThisAddress(operator, address);
        return addressStop != null ? addressStop.getStop().getName() : "";
    }

    /**
     * Suggest and return all stop information of a stop near to the supplied address for the supplied operator.
     * @param operator a <code>String</code> containing the name of the operator.
     * @param address a <code>String</code> containing the address to be found.
     * @return a <code>Address</code> object containing all stop information of the stop that is being suggested.
     */
    public Address suggestNearestStopForThisAddress (final String operator, final String address ) {
        return addressStopRepository.findByAddressOperatorAndAddress(operator, address);
    }

    /**
     * Return all addresses which are stored for a particular operator.
     * @param operator a <code>String</code> containing the name of the operator.
     * @return a <code>List</code> of <code>String</code> objects containing all addresses.
     */
    public List<String> getAddressesForOperator (final String operator ) {
        List<Address> addressStops = addressStopRepository.findByAddressOperator(operator);
        List<String> addresses = new ArrayList<>();
        for ( Address address : addressStops ) {
            addresses.add(address.getAddress());
        }
        return addresses;
    }

}

