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
import {VehicleRequest} from "../vehicles/vehicle.request";
import {DriverRequest} from "../drivers/driver.request";
import {CompanyResponse} from "../management/company.response";
import {Message} from "../messages/message.model";
import {MessagesResponse} from "../messages/messages.response";
import {Route} from "../routes/route.model";
import {TimetableRequest} from "./timetable.request";
import {RouteResponse} from "../routes/route.response";

@Injectable()
/**
 * This class provides access via http calls to the server to collect data about stops and routes as necessary.
 */
export class ServerService {

    private serverUrl: string;
    private company: string;
    private playerName: string;

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
        // Set the company and player name attributes.
        this.company = game.getCompanyName();
        this.playerName = game.getPlayerName();
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/company/', companyRequest));
    }

    /**
     * Retrieve the list of stops that exist on the server for the configured company.
     */
    getStops(): any {
        this.httpClient.get<StopsResponse>(this.serverUrl + '/stops/?company=' + this.company).subscribe(stops => {
            return stops;
        });
    }

    /**
     * Retrieve the list of routes that exist on the server for the configured company.
     */
    async getRoutes(): Promise<RoutesResponse> {
        return await lastValueFrom(this.httpClient.get<RoutesResponse>(this.serverUrl + '/routes/?company=' + this.company));
    }

    /**
     * Retrieve the list of vehicles that exist on the server for the configured company.
     */
    async getVehicles(): Promise<VehiclesResponse> {
        return await lastValueFrom(this.httpClient.get<VehiclesResponse>(this.serverUrl + '/vehicles/?company=' + this.company));
    }

    /**
     * Add the message to the server for the specified company.
     * @param messageRequest the message to add to the server.
     */
    async addMessage(messageRequest: MessageRequest) {
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/message/', messageRequest));
    }

    /**
     * Add the vehicle to the server for the specified company.
     * @param vehicleRequest the vehicle to add to the server.
     */
    async addVehicle(vehicleRequest: VehicleRequest) {
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/vehicle/', vehicleRequest));
    }

    /**
     * Add the driver to the server for the specified company.
     * @param driverRequest the driver to add to the server.
     */
    async addDriver(driverRequest: DriverRequest) {
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/driver/', driverRequest));
    }

    /**
     * Get the current date time for the configured company and player name.
     * @return the current date time as a string in the format dd-MM-yyyy HH:mm
     */
    async getCurrentDateTime(): Promise<string> {
        let company = await lastValueFrom(this.httpClient.get<CompanyResponse>(this.serverUrl + '/company/?name=' + this.company + '&playerName=' + this.playerName))
        return company.time;
    }

    /**
     * Get the current balance for the configured company and player name.
     * @return the current balance as a number
     */
    async getBalance(): Promise<number> {
        let company = await lastValueFrom(this.httpClient.get<CompanyResponse>(this.serverUrl + '/company/?name=' + this.company + '&playerName=' + this.playerName))
        return company.balance;
    }

    /**
     * Get the current passenger satisfaction for the configured company and player name.
     * @return the current passenger satisfaction as a number
     */
    async getPassengerSatisfaction(): Promise<number> {
        let company = await lastValueFrom(this.httpClient.get<CompanyResponse>(this.serverUrl + '/company/?name=' + this.company + '&playerName=' + this.playerName))
        return company.satisfactionRate;
    }

    /**
     * Retrieve all of the messages in a particular folder.
     * @return the array of messages that exist for the supplied folder.
     */
    async getMessagesByFolder(folder: string): Promise<Message[]> {
        let messages = [];
        let messagesResponse = await lastValueFrom(this.httpClient.get<MessagesResponse>(this.serverUrl + '/messages/?company=' + this.company + '&folder=' + folder));
        if ( messagesResponse ) {
            for ( let messageResponse of messagesResponse.messageResponses ) {
                messages.push(new Message(messageResponse.subject, messageResponse.text, messageResponse.folder, TimeHelper.formatStringAsDateObject(messageResponse.dateTime)));
            }
        }
        return messages;
    }

    /**
     * Retrieve the company name.
     * @return the company name as a string.
     */
    getCompanyName(): string {
        return this.company;
    }

    /**
     * Retrieve the player name.
     * @return the player name as a string.
     */
    getPlayerName(): string {
        return this.playerName;
    }

    /**
     * Get the current scenario name for the configured company and player name.
     * @return the current scenario name as a string
     */
    async getScenarioName(): Promise<string> {
        let company = await lastValueFrom(this.httpClient.get<CompanyResponse>(this.serverUrl + '/company/?name=' + this.company + '&playerName=' + this.playerName))
        return company.scenarioName;
    }

    /**
     * Delete the company - including the drivers, messages and vehicles.
     */
    async deleteCompany(): Promise<void> {
        // Delete the messages
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/messages/?company=' + this.company));
        // Delete the drivers
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/drivers/?company=' + this.company));
        // Delete the routes
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/routes/?company=' + this.company));
        // Delete the vehicles
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/vehicles/?company=' + this.company));
        // Delete the company
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/company/?name=' + this.company + '&playerName=' + this.playerName))
    }

    /**
     * Add the route to the server for the specified company.
     * @param route the route to add to the server.
     */
    async addRoute(route: Route) {
        // Post it to the server.
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/route/', route));
    }

    /**
     * Add the timetable to the server for the specified company.
     * @param timetable the timetable to add to the server.
     */
    async addTimetable(timetable: TimetableRequest) {
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/timetable/', timetable));
    }

    /**
     * Get the route matching the supplied route number for the specified company.
     * @param routeNumber the route number to retrieve.
     */
    async getRoute(routeNumber: string): Promise<RouteResponse> {
        return await lastValueFrom(this.httpClient.get<RouteResponse>(this.serverUrl + '/route/?company=' + this.company + '&routeNumber=' + routeNumber))
    }


}