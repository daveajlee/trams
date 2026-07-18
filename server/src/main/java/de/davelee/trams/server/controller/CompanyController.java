package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Company;
import de.davelee.trams.server.request.*;
import de.davelee.trams.server.response.*;
import de.davelee.trams.server.service.CompanyService;
import de.davelee.trams.server.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * This class defines the endpoints for the REST API which manipulate a company and delegates the actions to the CompanyService class.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/company")
@RequestMapping(value="/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Add a company to the system.
     * @param companyRequest a <code>CompanyRequest</code> object representing the company to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @Operation(summary = "Add a company", description="Add a company to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="201",description="Successfully created company")})
    public ResponseEntity<Void> addCompany (@RequestBody final CompanyRequest companyRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (companyRequest.getName().isBlank() || companyRequest.getPlayerName().isBlank()
                || companyRequest.getStartingTime().isBlank() || companyRequest.getStartingBalance() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        //Now convert to company object.
        Company company = new Company();
        company.setName(companyRequest.getName());
        company.setPlayerName(companyRequest.getPlayerName());
        company.setBalance(BigDecimal.valueOf(companyRequest.getStartingBalance()));
        company.setSatisfactionRate(BigDecimal.valueOf(100.0));
        company.setTime(DateUtils.convertDateToLocalDateTime(companyRequest.getStartingTime()));
        company.setScenarioName(companyRequest.getScenarioName());
        company.setDifficultyLevel(companyRequest.getDifficultyLevel());
        company.setSimulationInterval(100);
        //Return 201 if saved successfully.
        return companyService.save(company) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Retrieve a company based on the name of the company and the player name.
     * @param name a <code>String</code> containing the name of the company.
     * @param playerName a <code>String</code> containing the name of the player.
     * @return a <code>ResponseEntity</code> containing the result.
     */
    @Operation(summary = "Get a company", description="Get a company based on name and player name")
    @GetMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned company")})
    public ResponseEntity<CompanyResponse> retrieveCompany (final String name, final String playerName ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (name.isBlank() || playerName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        //Retrieve the company. Return no content if 0 or more than 1 companies are found.
        List<Company> companies = companyService.retrieveCompanyByNameAndPlayerName(name, playerName);
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now convert to company response object.
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setName(companies.getFirst().getName());
        companyResponse.setPlayerName(companies.getFirst().getPlayerName());
        companyResponse.setBalance(companies.getFirst().getBalance().doubleValue());
        companyResponse.setSatisfactionRate(companies.getFirst().getSatisfactionRate().doubleValue());
        companyResponse.setTime(DateUtils.convertLocalDateTimeToDate(companies.getFirst().getTime()));
        companyResponse.setScenarioName(companies.getFirst().getScenarioName());
        companyResponse.setDifficultyLevel(companies.getFirst().getDifficultyLevel());
        companyResponse.setSimulationInterval(companies.getFirst().getSimulationInterval());
        return ResponseEntity.ok(companyResponse);
    }

    /**
     * Adjust the balance of the company matching the supplied company. The current balance after adjustment will be returned.
     * @param adjustBalanceRequest a <code>AdjustBalanceRequest</code> object containing the information about the company and the value of the balance which should be adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Adjust balance of a company", description="Adjust balance of the company")
    @PatchMapping(value="/balance")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully adjusted balance of company"), @ApiResponse(responseCode="204",description="No company found")})
    public ResponseEntity<BalanceResponse> adjustBalance (@RequestBody AdjustBalanceRequest adjustBalanceRequest) {
        //Check that the request is valid.
        if ( adjustBalanceRequest.getCompany().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the balance cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(adjustBalanceRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the balance and return the current balance after adjustment.
        return ResponseEntity.ok(new BalanceResponse(companies.getFirst().getName(), companyService.adjustBalance(companies.getFirst(), BigDecimal.valueOf(adjustBalanceRequest.getValue())).doubleValue()));
    }

    /**
     * Adjust the satisfaction rate of the company matching the supplied company. The current satisfaction rate after adjustment will be returned.
     * @param adjustSatisfactionRequest a <code>AdjustSatisfactionRequest</code> object containing the information about the company and the value of the satisfaction rate which should be adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Adjust satisfaction rate", description="Adjust satisfaction rate of the company")
    @PatchMapping(value="/satisfaction")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully adjusted satisfaction rate of company"), @ApiResponse(responseCode="204",description="No company found")})
    public ResponseEntity<SatisfactionRateResponse> adjustSatisfaction (@RequestBody AdjustSatisfactionRequest adjustSatisfactionRequest) {
        //Check that the request is valid.
        if ( adjustSatisfactionRequest.getCompany().isBlank() || adjustSatisfactionRequest.getSatisfactionRate() < -100 && adjustSatisfactionRequest.getSatisfactionRate() > 100) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the balance cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(adjustSatisfactionRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the satisfaction rate and return the current satisfaction rate after adjustment.
        return ResponseEntity.ok(new SatisfactionRateResponse(companies.getFirst().getName(), companyService.adjustSatisfactionRate(companies.getFirst(), BigDecimal.valueOf(adjustSatisfactionRequest.getSatisfactionRate())).doubleValue()));
    }

    /**
     * Adjust the simulation interval of the company matching the supplied company. The current simulation interval after adjustment will be returned.
     * @param adjustSimulationIntervalRequest a <code>AdjustSimulationIntervalRequest</code> object containing the information about the company and the value of the simulation rate which should be adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Adjust simulation interval", description="Adjust simulation interval of the company")
    @PatchMapping(value="/simulationInterval")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully adjusted satisfaction rate of company"), @ApiResponse(responseCode="204",description="No company found")})
    public ResponseEntity<SimulationIntervalResponse> adjustSimulationInterval (@RequestBody AdjustSimulationIntervalRequest adjustSimulationIntervalRequest) {
        //Check that the request is valid.
        if ( adjustSimulationIntervalRequest.getCompany().isBlank() || adjustSimulationIntervalRequest.getSimulationInterval() < 1) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the simulation interval cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(adjustSimulationIntervalRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the simulation interval and return the current simulation interval after adjustment.
        return ResponseEntity.ok(new SimulationIntervalResponse(companies.getFirst().getName(), companyService.adjustSimulationInterval(companies.getFirst(), adjustSimulationIntervalRequest.getSimulationInterval())));
    }

    /**
     * Add the time in minutes to the company matching the supplied company name. The current time after adjustment will be returned.
     * @param addTimeRequest a <code>AddTimeRequest</code> object containing the information about the company and the time in minutes which should be added.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Add time in minutes", description="Add time in minutes to the company")
    @PatchMapping(value="/time")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully added the time in minutes to the company"), @ApiResponse(responseCode="204",description="No company found")})
    public ResponseEntity<TimeResponse> addTime (@RequestBody AddTimeRequest addTimeRequest) {
        //Check that the request is valid.
        if ( addTimeRequest.getCompany().isBlank() || (addTimeRequest.getMinutes() <= 0) ) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the time cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(addTimeRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now add the time and return the current time after adjustment.
        return ResponseEntity.ok(new TimeResponse(companies.getFirst().getName(), DateUtils.convertLocalDateTimeToDate(companyService.addTime(companies.getFirst(), addTimeRequest.getMinutes()))));
    }

    /**
     * Set the difficulty level for the company matching the supplied company name. The difficulty level after adjustment will be returned.
     * @param difficultyLevelRequest a <code>DifficultyLevelRequest</code> object containing the information about the company and the difficulty level which should be used.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @Operation(summary = "Set the difficulty level", description="Set the difficulty level for the company")
    @PatchMapping(value="/difficultyLevel")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully adjust the difficulty level for the company"), @ApiResponse(responseCode="204",description="No company found")})
    public ResponseEntity<DifficultyLevelResponse> adjustDifficultyLevel (@RequestBody AdjustDifficultyLevelRequest difficultyLevelRequest) {
        //Check that the request is valid.
        if (difficultyLevelRequest.getCompany().isBlank() || difficultyLevelRequest.getDifficultyLevel().isBlank() ) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the time cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(difficultyLevelRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now add the time and return the current time after adjustment.
        return ResponseEntity.ok(new DifficultyLevelResponse(companies.getFirst().getName(), companyService.adjustDifficultyLevel(companies.getFirst(), difficultyLevelRequest.getDifficultyLevel())));
    }

    /**
     * Export the company information in JSON format containing the information supplied.
     * @param exportCompanyRequest a <code>ExportCompanyRequest</code> object containing the information about
     *                            routes, messages, drivers and vehicles which should be used.
     * @return a <code>ResponseEntity</code> containing all company information as a <code>ExportCompanyResponse</code> in JSON format.
     */
    @Operation(summary = "Export company information", description="Export all company information")
    @PostMapping(value="/export")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully exported the company information"), @ApiResponse(responseCode="500",description="Export could not be generated")})
    public ResponseEntity<ExportCompanyResponse> exportCompany (@RequestBody ExportCompanyRequest exportCompanyRequest) {
        //Check that the request is valid.
        if ( exportCompanyRequest.getCompany().isBlank() || exportCompanyRequest.getPlayerName().isBlank() ) {
            return ResponseEntity.badRequest().build();
        }
        //Retrieve the company. Return no content if 0 or more than 1 companies are found.
        List<Company> companies = companyService.retrieveCompanyByNameAndPlayerName(exportCompanyRequest.getCompany(), exportCompanyRequest.getPlayerName());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        // Return the export.
        ExportCompanyResponse exportCompanyResponse = new ExportCompanyResponse();
        exportCompanyResponse.setName(companies.getFirst().getName());
        exportCompanyResponse.setBalance(companies.getFirst().getBalance().doubleValue());
        exportCompanyResponse.setPlayerName(companies.getFirst().getPlayerName());
        exportCompanyResponse.setSatisfactionRate(companies.getFirst().getSatisfactionRate().doubleValue());
        exportCompanyResponse.setTime(DateUtils.convertLocalDateTimeToDate(companies.getFirst().getTime()));
        exportCompanyResponse.setScenarioName(companies.getFirst().getScenarioName());
        exportCompanyResponse.setDifficultyLevel(companies.getFirst().getDifficultyLevel());
        exportCompanyResponse.setRoutes(exportCompanyRequest.getRoutes());
        exportCompanyResponse.setDrivers(exportCompanyRequest.getDrivers());
        exportCompanyResponse.setVehicles(exportCompanyRequest.getVehicles());
        exportCompanyResponse.setMessages(exportCompanyRequest.getMessages());
        return ResponseEntity.ok(exportCompanyResponse);
    }

    /**
     * Delete a particular company.
     * @param name a <code>String</code> containing the name of the company.
     * @param playerName a <code>String</code> containing the name of the player.
     * @return a <code>ResponseEntity</code> object containing the results of the action.
     */
    @DeleteMapping("/")
    @CrossOrigin
    @Operation(summary = "Delete company", description="Delete the particular company")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully deleted company")})
    public ResponseEntity<Void> deleteCompany (final String name, final String playerName ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (name.isBlank() || playerName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        //Now delete the companies found.
        companyService.deleteCompanies(name, playerName);
        //Return ok.
        return ResponseEntity.ok().build();
    }

}
