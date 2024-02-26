package dev.adidahari.jobapp.jobservice.job.mapper;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.dto.JobDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;
import dev.adidahari.jobapp.jobservice.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO convertToJobDto(Job job, Company company, List<Review> companyReviews) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setCompanyReviews(companyReviews);

        return jobDTO;
    }
}
