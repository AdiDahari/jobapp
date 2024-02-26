package dev.adidahari.jobapp.jobservice.job.dto;

import dev.adidahari.jobapp.jobservice.job.external.Company;
import dev.adidahari.jobapp.jobservice.job.external.Review;

import java.math.BigDecimal;
import java.util.List;

public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private String location;
    private Company company;
    private List<Review> companyReviews;

    public JobDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Review> getCompanyReviews() {
        return companyReviews;
    }

    public void setCompanyReviews(List<Review> companyReviews) {
        this.companyReviews = companyReviews;
    }
}
