package de.davelee.trams.business.request;

import lombok.*;

/**
 * This class is part of the TraMS Business REST API. It represents a request to export all the company information
 * to a JSON file including the supplied JSON information.
 * @author Dave Lee
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

}
