package cz.filmdb.service;

import cz.filmdb.model.Review;
import cz.filmdb.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

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
}
