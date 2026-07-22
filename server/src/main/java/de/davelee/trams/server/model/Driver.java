package de.davelee.trams.server.model;

import java.time.LocalDateTime;

/**
 * This class represents a driver. A driver can have a name, contracted hours and a start date.
 * More advanced features for driver are only available via PersonalMan integration.
 * @author Dave Lee
 */
public class Driver {

    /**
     * The id of the driver in the database.
     */
    private String id;

    /**
     * The name of the driver.
     */
    private String name;

    /**
     * The company that this driver works for.
     */
    private String company;

    /**
     * The contracted hours of the driver.
     */
    private int contractedHours;

    /**
     * The date that the driver started.
     */
    private LocalDateTime startDate;

    public Driver() {
    }

    public Driver(String id, String name, String company, int contractedHours, LocalDateTime startDate) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.contractedHours = contractedHours;
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", contractedHours=" + contractedHours +
                ", startDate=" + startDate +
                '}';
    }
}
