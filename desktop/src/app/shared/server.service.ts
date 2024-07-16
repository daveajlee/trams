import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {StopsResponse} from "../stops/stops-response.model";
import {RoutesResponse} from "../routes/routes.response";
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
import {GenerateStopTimesRequest} from "../timetablecreator/generatestoptimes.request";
import {AddStopRequest} from "../stops/addstop.request";
import {TimetablesResponse} from "./timetables.response";
import {CompaniesResponse} from "./companies.response";
import {StopTimesResponse} from "../stops/stop-detail/stoptimes.response";

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
    async deleteLoadedCompany(): Promise<void> {
        await this.deleteCompany(this.company, this.playerName);
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

    /**
     * Generate the stop times for a particular frequency pattern within a timetable.
     * @param generateStopTimesRequest the parameters for generating stop times.
     */
    async generateStopTimes(generateStopTimesRequest: GenerateStopTimesRequest): Promise<void> {
        console.log(generateStopTimesRequest);
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/stopTimes/generate', generateStopTimesRequest));
    }

    /**
     * Add the stop to the server for the specified company.
     * @param stop the stop to add to the server.
     */
    async addStop(stop: AddStopRequest) {
        await lastValueFrom(this.httpClient.post(this.serverUrl + '/stop/', stop));
    }

    /**
     * Retrieve the list of timetables that exist on the server for the configured company.
     * @param routeNumber the route number to retrieve the timetables for.
     */
    async getTimetables(routeNumber: string): Promise<TimetablesResponse> {
        return await lastValueFrom(this.httpClient.get<TimetablesResponse>(this.serverUrl + '/timetables/?company=' + this.company + '&routeNumber=' + routeNumber));
    }

    /**
     * Delete the timetable matching the supplied route number and timetable name for the configured company.
     * @param routeNumber the route number which must match.
     * @param name the name of the timetable which must match.
     */
    async deleteTimetable(routeNumber: string, name: string): Promise<void> {
        // Delete the timetable
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/timetable/?company=' + this.company + '&name=' + name + '&routeNumber=' + routeNumber));
    }

    /**
     * Retrieve the list of companies that exist on the server for the configured player name.
     * @param playerName the player name to search for.
     */
    async getCompanies(playerName: string): Promise<CompaniesResponse> {
        return await lastValueFrom(this.httpClient.get<CompaniesResponse>(this.serverUrl + '/companies/?playerName=' + playerName));
    }

    /**
     * Delete the supplied company and player name from the server.
     * @param company the name of the company to delete.
     * @param playerName the name of the player name that should be deleted.
     */
    async deleteCompany(company: string, playerName: string ) {
        // Delete the messages
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/messages/?company=' + company));
        // Delete the drivers
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/drivers/?company=' + company));
        // Delete the stop times
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/stopTimes/?company=' + company));
        // Delete the stops
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/stops/?company=' + company));
        // Delete the timetables
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/timetables/?company=' + company));
        // Delete the routes
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/routes/?company=' + company));
        // Delete the vehicles
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/vehicles/?company=' + company));
        // Delete the company
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/company/?name=' + company + '&playerName=' + playerName))
    }

    /**
     * Set the company and player name to the supplied values so that all other methods will return
     * the correct company.
     * @param name the name of the company to retrieve
     * @param playerName the player name of the company to retrieve
     */
    async loadCompany ( name: string, playerName: string ) {
        this.company = name;
        this.playerName = playerName;
    }

    /**
     * Delete the route matching the route number for the configured company.
     * @param routeNumber the route number to be deleted as a string.
     */
    async deleteRoute (routeNumber: string): Promise<void> {
        // Delete the route
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/route/?company=' + this.company + '&routeNumber=' + routeNumber))
        // Delete the timetables matching this route.
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/timetable/?company=' + this.company + '&routeNumber=' + routeNumber))
        // Delete the stop times matching this route.
        await lastValueFrom(this.httpClient.delete<void>(this.serverUrl + '/stopTimes/?company=' + this.company + '&routeNumber=' + routeNumber))
    }

    /**
     * Get the stop times matching the route number for the configured company.
     * @param routeNumber the route number to get the stop times for.
     */
    async getStopTimes ( routeNumber: string): Promise<StopTimesResponse> {
        // Get the stop times
        return await lastValueFrom(this.httpClient.get<StopTimesResponse>(this.serverUrl + '/stopTimes/?stopName=Airport&company=' + this.company + '&routeNumber=' + routeNumber + '&date=15-07-2024&endDate=15-07-2024&departures=true&arrivals=true'));
    }

}