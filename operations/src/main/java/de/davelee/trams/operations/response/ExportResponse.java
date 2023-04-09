package de.davelee.trams.operations.response;

import lombok.*;

/**
 * This class is part of the TraMS Operations REST API. It represents a response to a request to export all routes
 * and vehicles.
 * @author Dave Lee
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExportResponse {

    private RouteResponse[] routeResponses;

    private VehicleResponse[] vehicleResponses;

}
