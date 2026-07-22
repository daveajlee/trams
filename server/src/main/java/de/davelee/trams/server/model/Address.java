package de.davelee.trams.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents an address which is mapped to a particular stop. This stop is the closest stop to this address.
 * @author Dave Lee
 */
@Document
public class Address {

    /**
     * A unique id for this address.
     */
    @Id
    private ObjectId id;

    /**
     * The operator which serves this address.
     */
    private String addressOperator;

    /**
     * The address that can be searched for.
     */
    private String address;

    /**
     * The stop that serves this address.
     */
    private Stop stop;

    /**
     * The distance between stop and address in minutes.
     */
    private int durationInMins;

    public Address() {
    }

    public Address(ObjectId id, String addressOperator, String address, Stop stop, int durationInMins) {
        this.id = id;
        this.addressOperator = addressOperator;
        this.address = address;
        this.stop = stop;
        this.durationInMins = durationInMins;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAddressOperator() {
        return addressOperator;
    }

    public void setAddressOperator(String addressOperator) {
        this.addressOperator = addressOperator;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public int getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(int durationInMins) {
        this.durationInMins = durationInMins;
    }
}
