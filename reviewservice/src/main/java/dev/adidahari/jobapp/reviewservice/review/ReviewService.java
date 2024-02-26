package dev.adidahari.jobapp.reviewservice.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByCompanyId(Long companyId);

    boolean createReview(Long companyId, Review review);

    Review getReviewById(Long reviewId);

    Review updateReview(Long reviewId, Review review);

    boolean deleteReview(Long reviewId);
}
