import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {StopsResponse} from "../stops/stops-response.model";
import {RoutesResponse} from "../routes/routes-response.model";
import {VehiclesResponse} from "../vehicles/vehicles-response.model";
import {CompanyRequest} from "../scenariolist/company.request";
import {Game} from "../game/game.model";
import {lastValueFrom} from "rxjs";
import {MessageRequest} from "../messages/message.request";
import {TimeHelper} from "./time.helper";

@Injectable()
/**
 * This class provides access via http calls to the server to collect data about stops and routes as necessary.
 */
export class ServerService {

    private serverUrl: string;

    /**
     * Construct a new ServerService object which calls the http calls and returns the data provided.
     * @param httpClient an http client which can make http calls
     */
    constructor(private httpClient: HttpClient) {
    }

    /**
     * Set the server url for http calls.
     * @param serverUrl the new server url to be set
     */
    setServerUrl(serverUrl: string) {
        this.serverUrl = serverUrl;
    }

    /**
     * Retrieve the server url used for http calls.
     */
    getServerUrl() {
        return this.serverUrl;
    }

    /**
     * Add the game to the server as a company.
     * @param game the game object to add to the server.
     */
    async createCompany(game: Game): Promise<void> {
        // Create the company request object.
        let companyRequest = new CompanyRequest(game.getCompanyName(), game.getBalance(), game.getPlayerName(), TimeHelper.formatDateTimeAsString(game.getCurrentDateTime()), game.getScenario().getScenarioName(), game.getDifficultyLevel());
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/company/', companyRequest));
    }

    /**
     * Retrieve the list of stops that exist on the server for a specified company.
     * @param company the name of the company to retrieve the stops for.
     */
    getStops(company: string): any {
        this.httpClient.get<StopsResponse>(this.serverUrl + '/stops/?company=' + company).subscribe(stops => {
            return stops;
        });
    }

    /**
     * Retrieve the list of routes that exist on the server for a specified company.
     * @param company the name of the company to retrieve the routes for.
     */
    getRoutes(company: string): any {
        this.httpClient.get<RoutesResponse>(this.serverUrl + '/routes/?company=' + company).subscribe(routes => {
            return routes;
        });
    }

    /**
     * Retrieve the list of vehicles that exist on the server for a specified company.
     * @param company the name of the company to retrieve the vehicles for.
     */
    getVehicles(company: string): any {
        this.httpClient.get<VehiclesResponse>(this.serverUrl + '/vehicles/?company=' + company).subscribe(vehicles => {
            return vehicles;
        });
    }

    /**
     * Add the message to the server for the specified company.
     * @param messageRequest the message to add to the server.
     */
    async addMessage(messageRequest: MessageRequest) {
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/message/', messageRequest));
    }

}