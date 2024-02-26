package dev.adidahari.jobapp.companyservice.company.impl;


import dev.adidahari.jobapp.companyservice.company.Company;
import dev.adidahari.jobapp.companyservice.company.CompanyRepository;
import dev.adidahari.jobapp.companyservice.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Optional<Company> companyNullable = companyRepository.findById(id);

        if (companyNullable.isEmpty()) {
            return null;
        }
        Company companyEntity = companyNullable.get();
        companyEntity.setName(company.getName());
        companyEntity.setDescription(company.getDescription());

        return companyRepository.save(companyEntity);

    }
}
