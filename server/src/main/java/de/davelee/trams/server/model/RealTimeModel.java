package de.davelee.trams.server.model;

import java.util.List;

/**
 * Represent a set of real time entries for the trams timetabler api. Normally they consist of a timestamp and multiple
 * real time entries.
 * @author Dave Lee
 */
public class RealTimeModel {

    private String timestamp;
    private List<RealTimeEntryModel> realTimeEntryModelList;

    /**
     * Return the timestamp for this real time output.
     * @return a <code>String</code> containing the timestamp.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp for this real time output.
     * @param timestamp a <code>String</code> containing the timestamp.
     */
    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Return the entries for this real time output as a list of entries.
     * @return a <code>List</code> of <code>RealTimeEntryModel</code> containing the list of entries.
     */
    public List<RealTimeEntryModel> getRealTimeEntryModelList() {
        return realTimeEntryModelList;
    }

    /**
     * Set the entries for this real time output as a list of entries.
     * @param realTimeEntryModelList a <code>List</code> of <code>RealTimeEntryModel</code> containing the list of entries.
     */
    public void setRealTimeEntryModelList(final List<RealTimeEntryModel> realTimeEntryModelList) {
        this.realTimeEntryModelList = realTimeEntryModelList;
    }

}
