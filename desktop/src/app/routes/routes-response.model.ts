/**
 * This class defines a response for Routes in TraMS which consist of a count and a Route array.
 */
import {Route} from './route.model';

export class RoutesResponse {

    public count: number;
    public routeResponses: Route[];

}
