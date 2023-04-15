package de.davelee.trams.server.model;

/**
 * Represent a single instruction in a journey plan for the trams server api. Normally a journey consists of
 * several instructions.
 * @author Dave Lee
 */
public class JourneyInstruction {

    private String method;
    private String time;
    private int durationInMins;
    private String direction;
    private Route route;
    private String destination;

    /**
     * Default constructor which creates an empty JourneyInstruction object.
     */
    public JourneyInstruction( ) {

    }

    /**
     * Create a new JourneyInstruction object and initialise it with the supplied values.
     * @param method a <code>String</code> containing the method for this journey.
     * @param time a <code>String</code> with the time in format HH:mm
     * @param durationInMins a <code>int</code> containing the duration in minutes.
     * @param direction a <code>String</code> containing the direction.
     * @param route a <code>Route</code> containing the route information.
     * @param destination a <code>String</code> containing the destination for this journey part.
     */
    public JourneyInstruction(final String method, final String time, final int durationInMins,
                              final String direction, final Route route, final String destination ) {
        this.method = method;
        this.time = time;
        this.durationInMins = durationInMins;
        this.direction = direction;
        this.route = route;
        this.destination = destination;
    }

    /**
     * Get the method for this journey e.g. Walk, Train, Bus etc.
     * @return a <code>String</code> containing the method for this journey.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Set the method for this journey e.g. Walk, Train, Bus etc.
     * @param method a <code>String</code> containing the method for this journey.
     */
    public void setMethod(final String method) {
        this.method = method;
    }

    /**
     * Get the time where this journey instruction should start in format HH:mm
     * @return a <code>String</code> with the time in format HH:mm
     */
    public String getTime() {
        return time;
    }

    /**
     * Set the time where this journey instruction should start in format HH:mm
     * @param time a <code>String</code> with the time in format HH:mm
     */
    public void setTime(final String time) {
        this.time = time;
    }

    /**
     * Get the duration in minutes for this journey instruction.
     * @return a <code>int</code> containing the duration in minutes.
     */
    public int getDurationInMins() {
        return durationInMins;
    }

    /**
     * Set the duration in minutes for this journey instruction.
     * @param durationInMins a <code>int</code> containing the duration in minutes.
     */
    public void setDurationInMins(final int durationInMins) {
        this.durationInMins = durationInMins;
    }

    /**
     * Get the direction that should be followed for this journey instruction.
     * @return a <code>String</code> containing the direction.
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Set the direction that should be followed for this journey instruction.
     * @param direction a <code>String</code> containing the direction.
     */
    public void setDirection(final String direction) {
        this.direction = direction;
    }

    /**
     * Return the route containing the route information for this journey instruction.
     * @return a <code>Route</code> containing the route information.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Set the route containing the route information for this journey instruction.
     * @param route a <code>Route</code> containing the route information.
     */
    public void setRoute(final Route route) {
        this.route = route;
    }

    /**
     * Return the destination for this journey instruction.
     * @return a <code>String</code> containing the destination for this journey part.
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Set the destination for this journey instruction.
     * @param destination a <code>String</code> containing the destination for this journey part.
     */
    public void setDestination(final String destination) {
        this.destination = destination;
    }
}
