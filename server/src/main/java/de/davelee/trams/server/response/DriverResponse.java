package de.davelee.trams.server.response;

/**
 * This class is part of the TraMS Server REST API. It represents a response with
 * a driver for a particular company.
 * @author Dave Lee
 */
public class DriverResponse {

    /**
     * The name of this driver.
     */
    private String name;

    /**
     * The company that this driver works for.
     */
    private String company;

    /**
     * The contracted hours that the driver works.
     */
    private int contractedHours;

    /**
     * The date that this driver starts in format dd-MM-yyyy
     */
    private String startDate;

    public DriverResponse() {
    }

    public DriverResponse(String name, String company, int contractedHours, String startDate) {
        this.name = name;
        this.company = company;
        this.contractedHours = contractedHours;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getContractedHours() {
        return contractedHours;
    }

    public void setContractedHours(int contractedHours) {
        this.contractedHours = contractedHours;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "DriverResponse{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", contractedHours=" + contractedHours +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
