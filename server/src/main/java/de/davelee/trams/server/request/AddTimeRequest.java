package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to add the number of minutes
 * to the time for a particular company.
 * @author Dave Lee
 */
public class AddTimeRequest {

    /**
     * The name of the company to add the time to.
     */
    private String company;

    /**
     * The number of minutes to add to the time.
     */
    private int minutes;

    public AddTimeRequest() {
    }

    public AddTimeRequest(String company, int minutes) {
        this.company = company;
        this.minutes = minutes;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return "AddTimeRequest{" +
                "company='" + company + '\'' +
                ", minutes=" + minutes +
                '}';
    }
}
