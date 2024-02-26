package dev.adidahari.jobapp.jobservice.job.impl;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.JobRepository;
import dev.adidahari.jobapp.jobservice.job.JobService;
import dev.adidahari.jobapp.jobservice.job.dto.JobDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;
import dev.adidahari.jobapp.jobservice.job.external.Review;
import dev.adidahari.jobapp.jobservice.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private RestTemplate restTemplate;

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    // Override Methods //

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::jobToJobWithCompanyDTO).toList();
    }

    @Override
    public JobDTO createJob(Job job) {

        JobDTO jobDTO = jobToJobWithCompanyDTO(job);
        Job savedJob = jobRepository.save(job);

        return jobDTO;
    }

    @Override
    public JobDTO getJobById(Long id)
    {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            return jobToJobWithCompanyDTO(job);
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

            JobDTO jobDTO = jobToJobWithCompanyDTO(job);

            jobRepository.save(job);

            return jobDTO;
        }

        return null;
    }

    // Private Methods //

    private JobDTO jobToJobWithCompanyDTO(Job job) {
        Company company = getCompany(job);
        List<Review> reviews = getCompanyReviews(company);

        return JobMapper.convertToJobDto(job, company, reviews);
    }

    private Company getCompany(Job job) {
        return restTemplate.getForObject("http://company-service:8081/companies/" + job.getCompanyId(),
                Company.class);
    }

    private List<Review> getCompanyReviews(Company company) {
        ResponseEntity<List<Review>> reviewsResponse = restTemplate.exchange(
                "http://review-service:8083/reviews?companyId=" + company.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {});

        return reviewsResponse.getBody();


    }
}
