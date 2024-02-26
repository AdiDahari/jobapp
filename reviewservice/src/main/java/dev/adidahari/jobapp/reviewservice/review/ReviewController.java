package dev.adidahari.jobapp.reviewservice.review;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam("companyId") Long companyId) {
        List<Review> reviews = reviewService.getAllReviewsByCompanyId(companyId);

        if (reviews != null) {
            return ResponseEntity.ok(reviews);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addReview(@RequestParam("companyId") Long companyId, @RequestBody Review review) {
        if (reviewService.createReview(companyId, review)) {
            return ResponseEntity.ok(null);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review reviewNullable = reviewService.getReviewById(reviewId);

        if (reviewNullable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(reviewNullable);

    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long reviewId, @RequestBody Review review) {
        Review reviewNullable = reviewService.updateReview(reviewId, review);

        if (reviewNullable == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(reviewNullable);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable Long reviewId) {
        if (reviewService.deleteReview(reviewId)) {
            return ResponseEntity.ok(null);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
