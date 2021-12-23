package de.davelee.trams.business.controller;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.request.AddTimeRequest;
import de.davelee.trams.business.request.AdjustBalanceRequest;
import de.davelee.trams.business.request.AdjustSatisfactionRequest;
import de.davelee.trams.business.request.CompanyRequest;
import de.davelee.trams.business.response.BalanceResponse;
import de.davelee.trams.business.response.CompanyResponse;
import de.davelee.trams.business.response.SatisfactionRateResponse;
import de.davelee.trams.business.response.TimeResponse;
import de.davelee.trams.business.service.CompanyService;
import de.davelee.trams.business.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
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
@Api(value="/api/company")
@RequestMapping(value="/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * Add a company to the system.
     * @param companyRequest a <code>CompanyRequest</code> object representing the company to add.
     * @return a <code>ResponseEntity</code> containing the result of the action.
     */
    @ApiOperation(value = "Add a company", notes="Add a company to the system.")
    @PostMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=201,message="Successfully created company")})
    public ResponseEntity<Void> addCompany (@RequestBody final CompanyRequest companyRequest ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(companyRequest.getName()) || StringUtils.isBlank(companyRequest.getPlayerName())
                || StringUtils.isBlank(companyRequest.getStartingTime()) || companyRequest.getStartingBalance() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        //Now convert to company object.
        Company company = Company.builder()
                .name(companyRequest.getName())
                .playerName(companyRequest.getPlayerName())
                .balance(BigDecimal.valueOf(companyRequest.getStartingBalance()))
                .satisfactionRate(BigDecimal.valueOf(100.0))
                .time(DateUtils.convertDateToLocalDateTime(companyRequest.getStartingTime()))
                .scenarioName(companyRequest.getScenarioName())
                .difficultyLevel(companyRequest.getDifficultyLevel())
                .build();
        //Return 201 if saved successfully.
        return companyService.save(company) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }

    /**
     * Retrieve a company based on the name of the company and the player name.
     * @param name a <code>String</code> containing the name of the company.
     * @param playerName a <code>String</code> containing the name of the player.
     * @return a <code>ResponseEntity</code> containing the result.
     */
    @ApiOperation(value = "Get a company", notes="Get a company based on name and player name")
    @GetMapping(value="/")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully returned company")})
    public ResponseEntity<CompanyResponse> retrieveCompany (final String name, final String playerName ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (StringUtils.isBlank(name) || StringUtils.isBlank(playerName)) {
            return ResponseEntity.badRequest().build();
        }
        //Retrieve the company. Return no content if 0 or more than 1 companies are found.
        List<Company> companies = companyService.retrieveCompanyByNameAndPlayerName(name, playerName);
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now convert to company response object.
        return ResponseEntity.ok(CompanyResponse.builder()
                .name(companies.get(0).getName())
                .playerName(companies.get(0).getPlayerName())
                .balance(companies.get(0).getBalance().doubleValue())
                .satisfactionRate(companies.get(0).getSatisfactionRate().doubleValue())
                .time(DateUtils.convertLocalDateTimeToDate(companies.get(0).getTime()))
                .scenarioName(companies.get(0).getScenarioName())
                .difficultyLevel(companies.get(0).getDifficultyLevel())
                .build());
    }

    /**
     * Adjust the balance of the company matching the supplied company. The current balance after adjustment will be returned.
     * @param adjustBalanceRequest a <code>AdjustBalanceRequest</code> object containing the information about the company and the value of the balance which should be adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Adjust balance of a company", notes="Adjust balance of the company")
    @PatchMapping(value="/balance")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully adjusted balance of company"), @ApiResponse(code=204,message="No company found")})
    public ResponseEntity<BalanceResponse> adjustBalance (@RequestBody AdjustBalanceRequest adjustBalanceRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(adjustBalanceRequest.getCompany())) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the balance cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(adjustBalanceRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the balance and return the current balance after adjustment.
        return ResponseEntity.ok(BalanceResponse.builder()
                .company(companies.get(0).getName())
                .balance(companyService.adjustBalance(companies.get(0), BigDecimal.valueOf(adjustBalanceRequest.getValue())).doubleValue())
                .build());
    }

    /**
     * Adjust the satisfaction rate of the company matching the supplied company. The current satisfaction rate after adjustment will be returned.
     * @param adjustSatisfactionRequest a <code>AdjustSatisfactionRequest</code> object containing the information about the company and the value of the satisfaction rate which should be adjusted.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Adjust satisfaction rate", notes="Adjust satisfaction rate of the company")
    @PatchMapping(value="/satisfaction")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully adjusted satisfaction rate of company"), @ApiResponse(code=204,message="No company found")})
    public ResponseEntity<SatisfactionRateResponse> adjustSatisfaction (@RequestBody AdjustSatisfactionRequest adjustSatisfactionRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(adjustSatisfactionRequest.getCompany()) || adjustSatisfactionRequest.getSatisfactionRate() < -100 && adjustSatisfactionRequest.getSatisfactionRate() > 100) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the balance cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(adjustSatisfactionRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now adjust the satisfaction rate and return the current satisfaction rate after adjustment.
        return ResponseEntity.ok(SatisfactionRateResponse.builder()
                .company(companies.get(0).getName())
                .satisfactionRate(companyService.adjustSatisfactionRate(companies.get(0), BigDecimal.valueOf(adjustSatisfactionRequest.getSatisfactionRate())).doubleValue())
                .build());
    }

    /**
     * Add the time in minutes to the company matching the supplied company name. The current time after adjustment will be returned.
     * @param addTimeRequest a <code>AddTimeRequest</code> object containing the information about the company and the time in minutes which should be added.
     * @return a <code>ResponseEntity</code> containing the results of the action.
     */
    @ApiOperation(value = "Add time in minutes", notes="Add time in minutes to the company")
    @PatchMapping(value="/time")
    @ApiResponses(value = {@ApiResponse(code=200,message="Successfully added the time in minutes to the company"), @ApiResponse(code=204,message="No company found")})
    public ResponseEntity<TimeResponse> addTime (@RequestBody AddTimeRequest addTimeRequest) {
        //Check that the request is valid.
        if ( StringUtils.isBlank(addTimeRequest.getCompany()) || (addTimeRequest.getMinutes() <= 0) ) {
            return ResponseEntity.badRequest().build();
        }
        //Check that this company exists otherwise the time cannot be adjusted.
        List<Company> companies = companyService.retrieveCompanyByName(addTimeRequest.getCompany());
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        //Now add the time and return the current time after adjustment.
        return ResponseEntity.ok(TimeResponse.builder()
                .company(companies.get(0).getName())
                .time(DateUtils.convertLocalDateTimeToDate(companyService.addTime(companies.get(0), addTimeRequest.getMinutes())))
                .build());
    }

}
