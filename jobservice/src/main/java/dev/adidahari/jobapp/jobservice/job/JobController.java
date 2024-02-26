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

        ResponseEntity<List<JobWithCompanyDTO>> response = null;
        try {
            response = ResponseEntity.ok(jobService.getAllJobs());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<JobWithCompanyDTO> createJob(@RequestBody Job job) {
        try {
            JobWithCompanyDTO jobWithCompanyDTO = jobService.createJob(job);
            return ResponseEntity.ok(jobWithCompanyDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id) {
        JobWithCompanyDTO jobWithCompanyDTO = jobService.getJobById(id);

        return jobWithCompanyDTO == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(jobWithCompanyDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable Long id){
        if (jobService.deleteJob(id)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        try {
            JobWithCompanyDTO jobWithCompanyDTO = jobService.updateJob(id, updatedJob);

            if (jobWithCompanyDTO != null) {
                return ResponseEntity.ok(jobWithCompanyDTO);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
