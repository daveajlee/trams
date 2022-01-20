package de.davelee.trams.business.controller;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.request.*;
import de.davelee.trams.business.service.CompanyService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
                .scenarioName("Intermediate's Scenario")
                .difficultyLevel("MEDIUM")
                .build();
        assertEquals("CompanyRequest(name=Mustermann GmbH, startingBalance=10000.0, playerName=Max Mustermann, startingTime=28-11-2020 15:16, scenarioName=Intermediate's Scenario, difficultyLevel=MEDIUM)", companyRequest.toString());
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
        //Attempt to adjust balance.
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
     * Test case: adjust satisfaction rate of a company.
     * Expected result: the satisfaction rate of the company is adjusted as appropriate.
     */
    @Test
    public void testAdjustSatisfactionRate() {
        //Mock the important methods in company service.
        Mockito.when(companyService.retrieveCompanyByName("Mustermann GmbH")).thenReturn(List.of(generateValidCompany()));
        Mockito.when(companyService.adjustSatisfactionRate(any(), eq(BigDecimal.valueOf(-20.0)))).thenReturn(BigDecimal.valueOf(80.0));
        //Attempt to adjust satisfaction rate.
        assertEquals(HttpStatus.OK, companyController.adjustSatisfaction(AdjustSatisfactionRequest.builder()
                .company("Mustermann GmbH").satisfactionRate(-20.0).build()).getStatusCode());
        AdjustSatisfactionRequest adjustSatisfactionRequest = new AdjustSatisfactionRequest();
        adjustSatisfactionRequest.setCompany("Mustermann GmbH und Co");
        adjustSatisfactionRequest.setSatisfactionRate(10.0);
        assertEquals("AdjustSatisfactionRequest(company=Mustermann GmbH und Co, satisfactionRate=10.0)", adjustSatisfactionRequest.toString());
        assertEquals(HttpStatus.NO_CONTENT, companyController.adjustSatisfaction(adjustSatisfactionRequest).getStatusCode());
        adjustSatisfactionRequest.setCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, companyController.adjustSatisfaction(adjustSatisfactionRequest).getStatusCode());
    }

    /**
     * Test case: add time in minutes to a company.
     * Expected result: the time of the company is adjusted as appropriate.
     */
    @Test
    public void testAddTime() {
        //Mock the important methods in company service.
        Mockito.when(companyService.retrieveCompanyByName("Mustermann GmbH")).thenReturn(List.of(generateValidCompany()));
        Mockito.when(companyService.addTime(any(), eq(20))).thenReturn(LocalDateTime.of(2020,12,3,8,20));
        //Attempt to adjust satisfaction rate.
        assertEquals(HttpStatus.OK, companyController.addTime(AddTimeRequest.builder()
                .company("Mustermann GmbH").minutes(20).build()).getStatusCode());
        AddTimeRequest addTimeRequest = new AddTimeRequest();
        addTimeRequest.setCompany("Mustermann GmbH und Co");
        addTimeRequest.setMinutes(10);
        assertEquals("AddTimeRequest(company=Mustermann GmbH und Co, minutes=10)", addTimeRequest.toString());
        assertEquals(HttpStatus.NO_CONTENT, companyController.addTime(addTimeRequest).getStatusCode());
        addTimeRequest.setCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, companyController.addTime(addTimeRequest).getStatusCode());
    }

    /**
     * Test case: adjust difficulty level of a company.
     * Expected result: the difficulty level of the company is adjusted as appropriate.
     */
    @Test
    public void testAdjustDifficultyLevel() {
        //Mock the important methods in company service.
        Mockito.when(companyService.retrieveCompanyByName("Mustermann GmbH")).thenReturn(List.of(generateValidCompany()));
        Mockito.when(companyService.adjustDifficultyLevel(any(), eq("EASY"))).thenReturn("EASY");
        //Attempt to adjust balance.
        assertEquals(HttpStatus.OK, companyController.adjustDifficultyLevel(AdjustDifficultyLevelRequest.builder()
                .company("Mustermann GmbH").difficultyLevel("EASY").build()).getStatusCode());
        AdjustDifficultyLevelRequest adjustDifficultyLevelRequest = new AdjustDifficultyLevelRequest();
        adjustDifficultyLevelRequest.setCompany("Mustermann GmbH und Co");
        adjustDifficultyLevelRequest.setDifficultyLevel("HARD");
        assertEquals("AdjustDifficultyLevelRequest(company=Mustermann GmbH und Co, difficultyLevel=HARD)", adjustDifficultyLevelRequest.toString());
        assertEquals(HttpStatus.NO_CONTENT, companyController.adjustDifficultyLevel(adjustDifficultyLevelRequest).getStatusCode());
        adjustDifficultyLevelRequest.setCompany("");
        assertEquals(HttpStatus.BAD_REQUEST, companyController.adjustDifficultyLevel(adjustDifficultyLevelRequest).getStatusCode());
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
