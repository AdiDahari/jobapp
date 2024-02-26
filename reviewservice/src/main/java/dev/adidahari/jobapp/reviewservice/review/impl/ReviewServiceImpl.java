package dev.adidahari.jobapp.reviewservice.review.impl;


import dev.adidahari.jobapp.reviewservice.review.Review;
import dev.adidahari.jobapp.reviewservice.review.ReviewRepository;
import dev.adidahari.jobapp.reviewservice.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private RestTemplate restTemplate;

    private final ReviewRepository reviewRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<Review> getAllReviewsByCompanyId(Long companyId) {

        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public boolean createReview(Long companyId, Review review) {
        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }

    @Override
    public Review getReviewById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);

        return reviewOptional.orElse(null);
    }

    @Override
    public Review updateReview(Long reviewId, Review updatedReview) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty() || (updatedReview == null)) return null;

        Review review = reviewOptional.get();
        review.setTitle(updatedReview.getTitle());
        review.setDescription(updatedReview.getDescription());
        review.setRating(updatedReview.getRating());


        return reviewRepository.save(review);
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) return false;

        reviewRepository.deleteById(reviewId);
        return true;
    }
}
