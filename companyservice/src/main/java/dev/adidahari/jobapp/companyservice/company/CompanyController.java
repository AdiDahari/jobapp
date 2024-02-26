package dev.adidahari.jobapp.companyservice.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;


    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Company compaanyNullable = companyService.getCompanyById(id);

        if (compaanyNullable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(compaanyNullable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCompany(@RequestBody Company company) {
        companyService.createCompany(company);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable Long id) {
        if (companyService.deleteCompany(id))
            return ResponseEntity.ok(null);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company company) {
        Company companyNullable = companyService.updateCompany(id, company);
        if (companyNullable != null) {
            return ResponseEntity.ok(companyNullable);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
