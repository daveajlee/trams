package de.davelee.trams.server.service;

import de.davelee.trams.server.model.Company;
import de.davelee.trams.server.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class to provide service operations for companies in TraMS Server.
 * @author Dave Lee
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Save the specified company object in the database.
     * @param company a <code>Company</code> object to save in the database.
     * @return a <code>boolean</code> which is true iff the company has been saved successfully.
     */
    public boolean save(final Company company) {
        return companyRepository.save(company) != null;
    }

    /**
     * Retrieve all companies with this name from the database (this should usually just be 1).
     * @param name a <code>String</code> with the name of the company to search for.
     * @return a <code>List</code> of <code>Company</code> objects.
     */
    public List<Company> retrieveCompanyByName (final String name) {
        //Return the companies found.
        return companyRepository.findByName(name);
    }

    /**
     * Retrieve a company with the name and player name from the database (this should usually just be 1 but Spring cannot guarantee it).
     * @param name a <code>String</code> with the name of the company to search for.
     * @param playerName a <code>String</code> with the player name to search for.
     * @return a <code>List</code> of <code>Company</code> objects (this should usually just be 1 but Spring cannot guarantee it).
     */
    public List<Company> retrieveCompanyByNameAndPlayerName (final String name, final String playerName) {
        //Return the companies found.
        return companyRepository.findByNameAndPlayerName(name, playerName);
    }

    /**
     * Retrieve all companies with the player name from the database.
     * @param playerName a <code>String</code> with the player name to search for.
     * @return a <code>List</code> of <code>Company</code> objects.
     */
    public List<Company> retrieveCompaniesByPlayerName (final String playerName) {
        //Return the companies found.
        return companyRepository.findByPlayerName(playerName);
    }

    /**
     * Adjust the balance of the company by the supplied amount.
     * @param company a <code>Company</code> object which should have its balance adjusted.
     * @param value a <code>BigDecimal</code> containing the amount that should be added or subtracted.
     * @return a <code>BigDecimal</code> containing the current balance of the company.
     */
    public BigDecimal adjustBalance ( final Company company, final BigDecimal value ) {
        company.setBalance(company.getBalance().add(value));
        if ( companyRepository.save(company) != null ) {
            return company.getBalance();
        }
        return BigDecimal.valueOf(Integer.MIN_VALUE);
    }

    /**
     * Adjust the satisfaction rate of the company by the supplied amount.
     * @param company a <code>Company</code> object which should have its satisfaction rate adjusted.
     * @param satisfactionRate a <code>BigDecimal</code> containing the amount that should be added or subtracted.
     * @return a <code>BigDecimal</code> containing the current satisfaction rate of the company.
     */
    public BigDecimal adjustSatisfactionRate ( final Company company, final BigDecimal satisfactionRate ) {
        company.setSatisfactionRate(company.getSatisfactionRate().add(satisfactionRate));
        //If satisfaction rate is below 0 then set it to 0.
        if ( company.getSatisfactionRate().compareTo(BigDecimal.ZERO) == -1 ) {
            company.setSatisfactionRate(BigDecimal.ZERO);
        }
        //If satisfaction rate is above 100 then set it 100.
        if ( company.getSatisfactionRate().compareTo(BigDecimal.valueOf(100.0)) == 1 ) {
            company.setSatisfactionRate(BigDecimal.valueOf(100.0));
        }
        //Return satisfaction rate if it could be saved successfully.
        if ( companyRepository.save(company) != null ) {
            return company.getSatisfactionRate();
        }
        return BigDecimal.valueOf(Integer.MIN_VALUE);
    }

    /**
     * Add the number of minutes to the supplied company's time and return the time after adjustment.
     * @param company a <code>Company</code> object which should have its time adjusted.
     * @param minutes a <code>int</code> containing the number of minutes to add.
     * @return a <code>LocalDateTime</code> containing the current time of the company.
     */
    public LocalDateTime addTime (final Company company, final int minutes ) {
        //Add time in minutes to the current time
        company.setTime(company.getTime().plusMinutes(minutes));
        //Return time if it could be saved successfully.
        if ( companyRepository.save(company) != null ) {
            return company.getTime();
        }
        return null;
    }

    /**
     * Adjust the difficulty level of the company to the supplied difficulty level.
     * @param company a <code>Company</code> object which should have its difficulty level adjusted.
     * @param difficultyLevel a <code>String</code> containing the difficulty level that should now be used for the company.
     * @return a <code>String</code> containing the current difficulty level of the company.
     */
    public String adjustDifficultyLevel ( final Company company, final String difficultyLevel ) {
        company.setDifficultyLevel(difficultyLevel);
        if ( companyRepository.save(company) != null ) {
            return company.getDifficultyLevel();
        }
        return "";
    }

    /**
     * Delete all of the companies matching the company name and player name.
     * @param name a <code>String</code> with the name of the company to search for.
     * @param playerName a <code>String</code> with the player name to search for.
     */
    public void deleteCompanies(final String name, final String playerName) {
        List<Company> companies = retrieveCompanyByNameAndPlayerName(name, playerName);
        companies.forEach(companyRepository::delete);
    }

}
