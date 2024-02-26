package dev.adidahari.jobapp.jobservice.job.mapper;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.dto.JobWithCompanyDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;

public class JobMapper {

    public static JobWithCompanyDTO convertToJobWithCompanyDto(Job job, Company company) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
