package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to employ a driver for the particular
 * company fulfilling the details supplied.
 * @author Dave Lee
 */
public class EmployDriverRequest {

    /**
     * The name of the driver.
     */
    private String name;

    /**
     * The contracted hours of the driver.
     */
    private int contractedHours;

    /**
     * The start date of the driver.
     */
    private String startDate;

    /**
     * The company that the driver wants to work for.
     */
    private String company;

    public EmployDriverRequest() {
    }

    public EmployDriverRequest(String name, int contractedHours, String startDate, String company) {
        this.name = name;
        this.contractedHours = contractedHours;
        this.startDate = startDate;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "EmployDriverRequest{" +
                "name='" + name + '\'' +
                ", contractedHours=" + contractedHours +
                ", startDate='" + startDate + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
