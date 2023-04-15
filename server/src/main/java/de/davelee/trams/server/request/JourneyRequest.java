package de.davelee.trams.server.request;

/**
 * Represent a request to calculate a journey with the trams server api.
 * @author Dave Lee
 */
public class JourneyRequest {

    private String operator;
    private String from;
    private String to;
    private String departDateTime;
    private String arriveDateTime;
    private String language;

    /**
     * Default constructor without initialising values.
     */
    public JourneyRequest( ) {

    }

    /**
     * Constructor to initialise a new JourneyRequest based on the supplied values.
     * @param operator a <code>String</code> containing the name of the operator.
     * @param from a <code>String</code> containing the departure stop or place.
     * @param to a <code>String</code> containing the arrival stop or place.
     * @param departDateTime a <code>String</code> containing the departure date and time.
     * @param arriveDateTime a <code>String</code> containing the arrival date and time.
     * @param language a <code>String</code> containing the language to use for error messages.
     */
    public JourneyRequest(final String operator, final String from, final String to, final String departDateTime,
                          final String arriveDateTime, final String language ) {
        this.operator = operator;
        this.from = from;
        this.to = to;
        this.departDateTime = departDateTime;
        this.arriveDateTime = arriveDateTime;
        this.language = language;
    }

    /**
     * Return the operator which should be used for this journey request.
     * @return a <code>String</code> containing the operator.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Set the operator which should be used for this journey request.
     * @param operator a <code>String</code> containing the operator.
     */
    public void setOperator(final String operator) {
        this.operator = operator;
    }

    /**
     * Return the start location (either stop or address) for this journey.
     * @return a <code>String</code> containing the start location.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the start location (either stop or address) for this journey.
     * @param from a <code>String</code> containing the start location.
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Return the end location (either stop or address) for this journey.
     * @return a <code>String</code> containing the end location.
     */
    public String getTo() {
        return to;
    }

    /**
     * Set the end location (either stop or address) for this journey.
     * @param to a <code>String</code> containing the end location.
     */
    public void setTo(final String to) {
        this.to = to;
    }

    /**
     * Return the start time for this journey in format dd.MM.yyyy HH:mm
     * @return a <code>String</code> containing the start time.
     */
    public String getDepartDateTime() {
        return departDateTime;
    }

    /**
     * Set the start time for this journey in format dd.MM.yyyy HH:mm
     * @param departDateTime a <code>String</code> containing the start time.
     */
    public void setDepartDateTime(final String departDateTime) {
        this.departDateTime = departDateTime;
    }

    /**
     * Return the end time for this journey in format dd.MM.yyyy HH:mm
     * @return a <code>String</code> containing the end time.
     */
    public String getArriveDateTime() {
        return arriveDateTime;
    }

    /**
     * Set the end time for this journey in format dd.MM.yyyy HH:mm
     * @param arriveDateTime a <code>String</code> containing the end time.
     */
    public void setArriveDateTime(final String arriveDateTime) {
        this.arriveDateTime = arriveDateTime;
    }

    /**
     * Return the language code to use for text returned by the journey planner e.g. DE or UK.
     * @return a <code>String</code> containing the language code.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set the language code to use for text returned by the journey planner e.g. DE or UK.
     * @param language a <code>String</code> containing the language code.
     */
    public void setLanguage(final String language) {
        this.language = language;
    }
}
