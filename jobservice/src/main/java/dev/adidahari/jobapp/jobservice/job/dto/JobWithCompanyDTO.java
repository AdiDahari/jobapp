package dev.adidahari.jobapp.jobservice.job.dto;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.external.Company;

public class JobWithCompanyDTO {
    private Job job;
    private Company company;

    public JobWithCompanyDTO() {
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
