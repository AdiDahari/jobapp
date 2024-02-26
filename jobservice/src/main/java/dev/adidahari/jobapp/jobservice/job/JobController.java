package dev.adidahari.jobapp.jobservice.job;

import dev.adidahari.jobapp.jobservice.job.dto.JobWithCompanyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createJob(@RequestBody Job job) {
        jobService.createJob(job);

    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id) {
        JobWithCompanyDTO jobWithCompanyDTO = jobService.getJobById(id);

        return jobWithCompanyDTO == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(jobWithCompanyDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable Long id){
        if (jobService.deleteJob(id)) {
            return ResponseEntity.ok(null);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job) {

        Job jobNullable = jobService.updateJob(id, job);

        if (jobNullable != null) {
            return ResponseEntity.ok(jobNullable);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
