package de.davelee.trams.server.controller;

import de.davelee.trams.server.model.Company;
import de.davelee.trams.server.response.CompaniesResponse;
import de.davelee.trams.server.response.CompanyResponse;
import de.davelee.trams.server.service.CompanyService;
import de.davelee.trams.server.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This class provides REST endpoints which provide operations associated with companies in the TraMS Server API.
 * @author Dave Lee
 */
@RestController
@Tag(name="/api/companies")
@RequestMapping(value="/api/companies")
public class CompaniesController {

    @Autowired
    private CompanyService companyService;

    /**
     * Retrieve all companies being run by the player name.
     * @param playerName a <code>String</code> containing the name of the player.
     * @return a <code>ResponseEntity</code> containing the result.
     */
    @Operation(summary = "Get companies", description="Get companies based on player name")
    @GetMapping(value="/")
    @ApiResponses(value = {@ApiResponse(responseCode="200",description="Successfully returned companies")})
    public ResponseEntity<CompaniesResponse> retrieveCompanies (final String playerName ) {
        //First of all, check if any of the fields are empty or null, then return bad request.
        if (playerName.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        //Retrieve the companies. Return no content if 0 companies are found.
        List<Company> companies = companyService.retrieveCompaniesByPlayerName(playerName);
        if ( companies == null || companies.size() != 1 ) {
            return ResponseEntity.noContent().build();
        }
        CompanyResponse[] companyResponseList = new CompanyResponse[companies.size()];
        // Go through the company list.
        for ( int i = 0; i < companyResponseList.length; i++ ) {
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setName(companies.get(i).getName());
            companyResponse.setPlayerName(companies.get(i).getPlayerName());
            companyResponse.setBalance(companies.get(i).getBalance().doubleValue());
            companyResponse.setSatisfactionRate(companies.get(i).getSatisfactionRate().doubleValue());
            companyResponse.setTime(DateUtils.convertLocalDateTimeToDate(companies.get(i).getTime()));
            companyResponse.setScenarioName(companies.get(i).getScenarioName());
            companyResponse.setDifficultyLevel(companies.get(i).getDifficultyLevel());
            companyResponseList[i] = companyResponse;
        }
        //Now convert to companies response object.
        return ResponseEntity.ok(new CompaniesResponse((long) companyResponseList.length, companyResponseList));
    }

}
