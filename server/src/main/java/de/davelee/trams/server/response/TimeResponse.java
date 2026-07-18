package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response containing the company
 * and its current time in the format dd-MM-yyyy HH:mm.
 * @author Dave Lee
 */
public class TimeResponse {

    /**
     * The name of the company.
     */
    private String company;

    /**
     * The time of the company in the format dd-MM-yyyy HH:mm.
     */
    private String time;

    public TimeResponse() {
    }

    public TimeResponse(String company, String time) {
        this.company = company;
        this.time = time;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimeResponse{" +
                "company='" + company + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
