package de.davelee.trams.server.model;

/**
 * Represent an entry for the real time output for the trams server api. Normally a real time output consists of
 * several entries.
 * @author Dave Lee
 */
public class RealTimeEntryModel {
	
	private Route route;
	private String destination;
	private int mins;

	/**
	 * Return the route object containing the route information for this real time entry.
	 * @return a <code>Route</code> containing the route information.
	 */
	public Route getRoute() {
		return route;
	}

	/**
	 * Set the route object containing the route information for this real time entry.
	 * @param route a <code>Route</code> containing the route information.
	 */
	public void setRoute(final Route route) {
		this.route = route;
	}

	/**
	 * Return the destination for this real time entry.
	 * @return a <code>String</code> containing the destination for this real time entry.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Set the destination for this real time entry.
	 * @param destination a <code>String</code> containing the destination for this real time entry.
	 */
	public void setDestination(final String destination) {
		this.destination = destination;
	}

	/**
	 * Return the number of minutes until departure for this real time entry.
	 * @return a <code>int</code> containing the number of minutes.
	 */
	public int getMins() {
		return mins;
	}

	/**
	 * Set the number of minutes until departure for this real time entry.
	 * @param mins a <code>int</code> containing the number of minutes.
	 */
	public void setMins(final int mins) {
		this.mins = mins;
	}

}
