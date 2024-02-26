package dev.adidahari.jobapp.jobservice.job.impl;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.JobRepository;
import dev.adidahari.jobapp.jobservice.job.JobService;
import dev.adidahari.jobapp.jobservice.job.clients.CompanyClient;
import dev.adidahari.jobapp.jobservice.job.clients.ReviewClient;
import dev.adidahari.jobapp.jobservice.job.dto.JobDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;
import dev.adidahari.jobapp.jobservice.job.external.Review;
import dev.adidahari.jobapp.jobservice.job.mapper.JobMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    // Override Methods //

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::jobToJobDTO).toList();
    }

    @Override
    public JobDTO createJob(Job job) {

        Job savedJob = jobRepository.save(job);

        return jobToJobDTO(savedJob);
    }

    @Override
    public JobDTO getJobById(Long id)
    {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            return jobToJobDTO(job);
        }
        return null;
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public JobDTO updateJob(Long id, Job updatedJob) {

        Optional<Job> jobNullable = jobRepository.findById(id);


        if (jobNullable.isPresent()) {
            Job job = jobNullable.get();

            job.setId(id);
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompanyId(updatedJob.getCompanyId());

            JobDTO jobDTO = jobToJobDTO(job);

            jobRepository.save(job);

            return jobDTO;
        }

        return null;
    }

    // Private Methods //

    private JobDTO jobToJobDTO(Job job) {
        Company company = getCompany(job);
        List<Review> reviews = getCompanyReviews(company.getId());

        return JobMapper.convertToJobDto(job, company, reviews);
    }

    private Company getCompany(Job job) {
        return companyClient.getCompany(job.getCompanyId());
    }

    private List<Review> getCompanyReviews(Long companyId) {
        return reviewClient.getReviewsByCompany(companyId);
    }
}
