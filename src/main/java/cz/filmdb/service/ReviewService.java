package cz.filmdb.service;

import cz.filmdb.model.Review;
import cz.filmdb.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> loadReviewsByUser(Long id) {
        return reviewRepository.findReviewsByUser(id);
    }

    public List<Review> loadReviewsByFilmwork(Long id) {
        return reviewRepository.findReviewsByFilmwork(id);
    }

    public List<Review> loadReviews() { return reviewRepository.findAll();}

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Review updatedReview) {
        Optional<Review> oldReview = reviewRepository.findById(updatedReview.getId());

        if (oldReview.isEmpty())
            return null;

        return reviewRepository.save(updatedReview);
    }
}
