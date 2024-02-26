package dev.adidahari.jobapp.jobservice.job.clients;

import dev.adidahari.jobapp.jobservice.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-service")
public interface ReviewClient {
    @GetMapping("/reviews")
    List<Review> getReviewsByCompany(@RequestParam("companyId") Long companyId);
}
