package dev.adidahari.jobapp.jobservice.job.impl;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.JobRepository;
import dev.adidahari.jobapp.jobservice.job.JobService;
import dev.adidahari.jobapp.jobservice.job.dto.JobWithCompanyDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;
import dev.adidahari.jobapp.jobservice.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<JobWithCompanyDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::jobToJobWithCompanyDTO).toList();
    }

    @Override
    public JobWithCompanyDTO createJob(Job job) {

        JobWithCompanyDTO jobWithCompanyDTO = jobToJobWithCompanyDTO(job);
        Job savedJob = jobRepository.save(job);

        return jobWithCompanyDTO;
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id)
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
    public JobWithCompanyDTO updateJob(Long id, Job updatedJob) {

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

            JobWithCompanyDTO jobWithCompanyDTO = jobToJobWithCompanyDTO(job);

            jobRepository.save(job);

            return jobWithCompanyDTO;
        }

        return null;
    }

    // Private Methods //

    private JobWithCompanyDTO jobToJobWithCompanyDTO(Job job) {
        Company company = getCompany(job);

        return JobMapper.convertToJobWithCompanyDto(job, company);
    }

    private Company getCompany(Job job) {
        return restTemplate.getForObject("http://company-service:8081/companies/" + job.getCompanyId(),
                Company.class);
    }
}
