/**
 * This class defines a response for Routes in TraMS which consist of a count and a Route array.
 */
import {RouteResponse} from "./route.response";

export class RoutesResponse {

    public count: number;

    public routeResponses: RouteResponse[];

}
