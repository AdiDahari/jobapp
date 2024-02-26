package dev.adidahari.jobapp.companyservice.company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company getCompanyById(Long id);

    void createCompany(Company company);

    boolean deleteCompany(Long id);

    Company updateCompany(Long id, Company company);
}
