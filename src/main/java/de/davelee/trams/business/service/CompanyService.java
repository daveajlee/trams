package de.davelee.trams.business.service;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class to provide service operations for companies in TraMS Business.
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
     * Retrieve all companies with this name from the databas (this should usually just be 1).
     * @param name a <code>String</code> with the name of the company to search for.
     * @return a <code>List</code> of <code>Company</code> objects.
     */
    public List<Company> retrieveCompanyByName (final String name) {
        //Return the companies found.
        return companyRepository.findByName(name);
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

}
