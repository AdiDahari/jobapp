package dev.adidahari.jobapp.jobservice.job.impl;

import dev.adidahari.jobapp.jobservice.job.Job;
import dev.adidahari.jobapp.jobservice.job.JobRepository;
import dev.adidahari.jobapp.jobservice.job.JobService;
import dev.adidahari.jobapp.jobservice.job.dto.JobWithCompanyDTO;
import dev.adidahari.jobapp.jobservice.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

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
    public void createJob(Job job) {
        jobRepository.save(job);
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
    public Job updateJob(Long id, Job job) {

        Optional<Job> jobNullable = jobRepository.findById(id);

        if (jobNullable.isPresent()) {
            Job jobEntity = jobNullable.get();

            jobEntity.setId(id);
            jobEntity.setTitle(job.getTitle());
            jobEntity.setDescription(job.getDescription());
            jobEntity.setMinSalary(job.getMinSalary());
            jobEntity.setMaxSalary(job.getMaxSalary());
            jobEntity.setLocation(job.getLocation());
            jobEntity.setCompanyId(job.getCompanyId());

            return jobRepository.save(jobEntity);
        }

        return null;
    }

    // Private Methods //

    private JobWithCompanyDTO jobToJobWithCompanyDTO(Job job) {
        RestTemplate restTemplate = new RestTemplate();

        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();

        Company company = restTemplate.getForObject("http://localhost:8081/companies/" + job.getCompanyId(), Company.class);

        jobWithCompanyDTO.setJob(job);
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
