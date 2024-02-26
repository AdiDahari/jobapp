package dev.adidahari.jobapp.jobservice.job;


import dev.adidahari.jobapp.jobservice.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> getAllJobs();
    JobDTO createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJob(Long id);

    JobDTO updateJob(Long id, Job job);
}
