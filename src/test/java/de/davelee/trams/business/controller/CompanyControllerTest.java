package de.davelee.trams.business.controller;

import de.davelee.trams.business.request.CompanyRequest;
import de.davelee.trams.business.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

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

}
