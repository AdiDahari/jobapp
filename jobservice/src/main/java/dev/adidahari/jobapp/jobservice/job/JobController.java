package dev.adidahari.jobapp.jobservice.job;

import dev.adidahari.jobapp.jobservice.job.dto.JobDTO;
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
    public ResponseEntity<List<JobDTO>> getAllJobs() {

        ResponseEntity<List<JobDTO>> response = null;
        try {
            response = ResponseEntity.ok(jobService.getAllJobs());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return response;
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody Job job) {
        try {
            JobDTO jobDTO = jobService.createJob(job);
            return ResponseEntity.ok(jobDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        JobDTO jobDTO = jobService.getJobById(id);

        return jobDTO == null ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(jobDTO);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable Long id){
        if (jobService.deleteJob(id)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {

        try {
            JobDTO jobDTO = jobService.updateJob(id, updatedJob);

            if (jobDTO != null) {
                return ResponseEntity.ok(jobDTO);
            }

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
