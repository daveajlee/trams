package de.davelee.trams.business.controller;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.request.CompanyRequest;
import de.davelee.trams.business.service.CompanyService;
import de.davelee.trams.business.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * This class defines the endpoints for the REST API which manipulate a company and delegates the actions to the CompanyService class.
 * @author Dave Lee
 */
@RestController
@Api(value="/trams-business/company")
@RequestMapping(value="/trams-business/company")
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
                .build();
        //Return 201 if saved successfully.
        return companyService.save(company) ? ResponseEntity.status(201).build() : ResponseEntity.status(500).build();
    }


}
