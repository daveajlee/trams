/**
 * This class defines a response for Routes in TraMS which consist of a count and a Route array.
 */
import {Route} from './route.model';

export class RoutesResponse {

    private routeResponses: Route[];

    /**
     * Get the route responses that were retrieved from the server.
     * @return the route responses as a Route array.
     */
    getRouteResponses(): Route[] {
        return this.routeResponses;
    }

}
