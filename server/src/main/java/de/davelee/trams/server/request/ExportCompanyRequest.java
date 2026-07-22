package de.davelee.trams.server.request;

/**
 * This class is part of the TraMS Server REST API. It represents a request to export all the company information
 * to a JSON file including the supplied JSON information.
 * @author Dave Lee
 */
public class ExportCompanyRequest {

    /**
     * The name of the company to export.
     */
    private String company;

    /**
     * The player name of the company to export.
     */
    private String playerName;

    /**
     * The route information that exist for this company.
     */
    private String routes;

    /**
     * The driver information that exist for this company.
     */
    private String drivers;

    /**
     * The vehicle information that exist for this company.
     */
    private String vehicles;

    /**
     * The message information that exist for this company.
     */
    private String messages;

    public ExportCompanyRequest() {
    }

    public ExportCompanyRequest(String company, String playerName, String routes, String drivers, String vehicles, String messages) {
        this.company = company;
        this.playerName = playerName;
        this.routes = routes;
        this.drivers = drivers;
        this.vehicles = vehicles;
        this.messages = messages;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getDrivers() {
        return drivers;
    }

    public void setDrivers(String drivers) {
        this.drivers = drivers;
    }

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ExportCompanyRequest{" +
                "company='" + company + '\'' +
                ", playerName='" + playerName + '\'' +
                ", routes='" + routes + '\'' +
                ", drivers='" + drivers + '\'' +
                ", vehicles='" + vehicles + '\'' +
                ", messages='" + messages + '\'' +
                '}';
    }
}
