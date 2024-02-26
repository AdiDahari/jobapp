package dev.adidahari.jobapp.jobservice.job;


import dev.adidahari.jobapp.jobservice.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> getAllJobs();
    void createJob(Job job);

    JobWithCompanyDTO getJobById(Long id);

    boolean deleteJob(Long id);

    Job updateJob(Long id, Job job);
}
