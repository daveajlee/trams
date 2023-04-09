package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Company;
import de.davelee.trams.server.repository.CompanyRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Test cases for the CompanyService class - the CompanyRepository is mocked.
 * @author Dave Lee
 */
@SpringBootTest
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    /**
     * Test case: save a new company.
     * Expected Result: true.
     */
    @Test
    public void testSaveCompany() {
        //Test data
        Company company = generateValidCompany();
        //Mock important method in repository.
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        //do actual test.
        assertTrue(companyService.save(company));
    }

    /**
     * Ensure that data can be retrieved from the mock database and supplied as a response.
     */
    @Test
    public void testRetrieveByCompany() {
        //Test data.
        Mockito.when(companyRepository.findByName("Mustermann GmbH")).thenReturn(List.of(generateValidCompany()));
        //Now do actual test.
        List<Company> companies = companyService.retrieveCompanyByName("Mustermann GmbH");
        assertEquals(BigDecimal.valueOf(10000.0), companies.get(0).getBalance());
    }

    /**
     * Ensure that data can be retrieved from the mock database with a company and player name and supplied as a response.
     */
    @Test
    public void testRetrieveByCompanyNameAndPlayerName() {
        //Test data.
        Mockito.when(companyRepository.findByNameAndPlayerName("Mustermann GmbH", "Max Mustermann")).thenReturn(List.of(generateValidCompany()));
        //Now do actual test.
        List<Company> companies = companyService.retrieveCompanyByNameAndPlayerName("Mustermann GmbH", "Max Mustermann");
        assertEquals(BigDecimal.valueOf(10000.0), companies.get(0).getBalance());
    }

    /**
     * Verify that the balance of a company can be adjusted appropriately.
     */
    @Test
    public void testAdjustBalance ( ) {
        //Generate test data.
        Company company = generateValidCompany();
        //Mock important method in repository.
        Mockito.when(companyRepository.save(any())).thenReturn(company);
        //Do test.
        assertEquals(BigDecimal.valueOf(15000.0), companyService.adjustBalance(company, BigDecimal.valueOf(5000.0)));
        assertEquals(BigDecimal.valueOf(7000.0), companyService.adjustBalance(company, BigDecimal.valueOf(-8000.0)));
        assertEquals(BigDecimal.valueOf(-1000.0), companyService.adjustBalance(company, BigDecimal.valueOf(-8000.0)));
        //Do test if database does not work.
        Mockito.when(companyRepository.save(any())).thenReturn(null);
        assertEquals(BigDecimal.valueOf(Integer.MIN_VALUE), companyService.adjustBalance(company, BigDecimal.valueOf(5000.0)));
    }

    /**
     * Verify that the satisfaction rate of a company can be adjusted appropriately.
     */
    @Test
    public void testAdjustSatisfaction ( ) {
        //Generate test data.
        Company company = generateValidCompany();
        //Mock important method in repository.
        Mockito.when(companyRepository.save(any())).thenReturn(company);
        //Do test.
        assertEquals(BigDecimal.valueOf(100.0), companyService.adjustSatisfactionRate(company, BigDecimal.valueOf(10.0)));
        assertEquals(BigDecimal.valueOf(40.0), companyService.adjustSatisfactionRate(company, BigDecimal.valueOf(-60.0)));
        assertEquals(BigDecimal.valueOf(0), companyService.adjustSatisfactionRate(company, BigDecimal.valueOf(-60.0)));
        //Do test if database does not work.
        Mockito.when(companyRepository.save(any())).thenReturn(null);
        assertEquals(BigDecimal.valueOf(Integer.MIN_VALUE), companyService.adjustSatisfactionRate(company, BigDecimal.valueOf(10.0)));
    }

    /**
     * Verify that the time of a company can be added to appropriately.
     */
    @Test
    public void testAddTime ( ) {
        //Generate test data.
        Company company = generateValidCompany();
        //Mock important method in repository.
        Mockito.when(companyRepository.save(any())).thenReturn(company);
        //Do test.
        assertEquals(LocalDateTime.of(2020,12,28,14,37), companyService.addTime(company, 15));
        //Do test if database does not work.
        Mockito.when(companyRepository.save(any())).thenReturn(null);
        assertNull(companyService.addTime(company, 10));
    }

    /**
     * Verify that the difficulty level of a company can be adjusted appropriately.
     */
    @Test
    public void testAdjustDifficultyLevel ( ) {
        //Generate test data.
        Company company = generateValidCompany();
        //Mock important method in repository.
        Mockito.when(companyRepository.save(any())).thenReturn(company);
        //Do test.
        assertEquals("HARD", companyService.adjustDifficultyLevel(company, "HARD"));
        //Do test if database does not work.
        Mockito.when(companyRepository.save(any())).thenReturn(null);
        assertEquals("", companyService.adjustDifficultyLevel(company, "HARD"));
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
