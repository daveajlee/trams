package de.davelee.trams.business.service;

import de.davelee.trams.business.model.Company;
import de.davelee.trams.business.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
