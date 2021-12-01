package de.davelee.trams.business.controller;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.request.AdjustBalanceRequest;
import de.davelee.trams.business.request.CompanyRequest;
import de.davelee.trams.business.service.CompanyService;
import org.assertj.core.util.Lists;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Test cases for the Company endpoints in the TraMS Business REST API.
 * @author Dave Lee
 */
@SpringBootTest
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyService companyService;

    /**
     * Test case: add a company to the system based on a valid company request.
     * Expected Result: company added successfully.
     */
    @Test
    public void testValidAdd() {
        //Mock important methods in compan< service.
        Mockito.when(companyService.save(any())).thenReturn(true);
        //Add company so that test is successfully.
        CompanyRequest companyRequest = CompanyRequest.builder()
                .name("Mustermann GmbH")
                .playerName("Max Mustermann")
                .startingBalance(10000.0)
                .startingTime("28-11-2020 15:16")
                .build();
        assertEquals("CompanyRequest(name=Mustermann GmbH, startingBalance=10000.0, playerName=Max Mustermann, startingTime=28-11-2020 15:16)", companyRequest.toString());
        ResponseEntity<Void> responseEntity = companyController.addCompany(companyRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.CREATED.value());
    }

    /**
     * Test case: add a company to the system based on an invalid company request.
     * Expected Result: bad request.
     */
    @Test
    public void testInvalidAdd() {
        //Add company so that test is successfully.
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setName("Mustermann GmbH");
        companyRequest.setPlayerName("Max Mustermann");
        companyRequest.setStartingBalance(-10000.0);
        companyRequest.setStartingTime("28-11-2020 15:16");
        ResponseEntity<Void> responseEntity = companyController.addCompany(companyRequest);
        assertTrue(responseEntity.getStatusCodeValue() == HttpStatus.BAD_REQUEST.value());
        companyRequest.setStartingBalance(10000.0);
        companyRequest.setStartingTime("50-11-2020 15:90");
        ResponseEntity<Void> responseEntity2 = companyController.addCompany(companyRequest);
        assertTrue(responseEntity2.getStatusCodeValue() == HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * Test case: retrieve a company based on company and player name.
     * Expected result: company information is returned as appropriate.
     */
    @Test
    public void testGetCompany() {
        //Mock the important methods in company service.
        Mockito.when(companyService.retrieveCompanyByNameAndPlayerName("Mustermann GmbH", "Max Mustermann")).thenReturn(List.of(generateValidCompany()));
        //Valid request returns company information.
        assertEquals(HttpStatus.OK, companyController.retrieveCompany("Mustermann GmbH", "Max Mustermann").getStatusCode());
        //If no name supplied then bad request.
        assertEquals(HttpStatus.BAD_REQUEST, companyController.retrieveCompany("", "Max").getStatusCode());
        //If names supplied but no results then no content,
        assertEquals(HttpStatus.NO_CONTENT, companyController.retrieveCompany("Mustermann GmbH", "Max").getStatusCode());
        //Test a null date.
        Company company = generateValidCompany();
        company.setTime(null);
        Mockito.when(companyService.retrieveCompanyByNameAndPlayerName("Mustermann GmbH", "Erica")).thenReturn(List.of(company));
        assertEquals(HttpStatus.OK, companyController.retrieveCompany("Mustermann GmbH", "Erica").getStatusCode());
    }

    /**
     * Test case: adjust balance of a company.
     * Expected result: the balance of the company is adjusted as appropriate.
     */
    @Test
    public void testAdjustBalance() {
        //Mock the important methods in company service.
        Mockito.when(companyService.retrieveCompanyByName("Mustermann GmbH")).thenReturn(List.of(generateValidCompany()));
        Mockito.when(companyService.adjustBalance(any(), eq(BigDecimal.valueOf(10000.0)))).thenReturn(BigDecimal.valueOf(50000.0));
        //Attempt to adjust delay.
        assertEquals(HttpStatus.OK, companyController.adjustBalance(AdjustBalanceRequest.builder()
                .company("Mustermann GmbH").value(10000.0).build()).getStatusCode());
        AdjustBalanceRequest adjustBalanceRequest = new AdjustBalanceRequest();
        adjustBalanceRequest.setCompany("Mustermann GmbH und Co");
        adjustBalanceRequest.setValue(1000.0);
        assertEquals("AdjustBalanceRequest(company=Mustermann GmbH und Co, value=1000.0)", adjustBalanceRequest.toString());
        assertEquals(HttpStatus.NO_CONTENT, companyController.adjustBalance(adjustBalanceRequest).getStatusCode());
        adjustBalanceRequest.setCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, companyController.adjustBalance(adjustBalanceRequest).getStatusCode());
    }

    /**
     * Private helper method to generate a valid company.
     * @return a <code>Company</code> object containing valid test data.
     */
    private Company generateValidCompany( ) {
        Company company = new Company();
        company.setName("Mustermann GmbH");
        company.setBalance(BigDecimal.valueOf(10000.0));
        company.setPlayerName("Max Mustermann");
        company.setSatisfactionRate(BigDecimal.valueOf(100.0));
        company.setTime(LocalDateTime.of(2020,12,28,14,22));
        company.setId(ObjectId.get());
        return company;
    }

}
