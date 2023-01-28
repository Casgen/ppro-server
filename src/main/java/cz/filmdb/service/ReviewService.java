package cz.filmdb.service;

import cz.filmdb.model.Review;
import cz.filmdb.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Optional<Review> loadReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public Page<Review> loadReviewsByUser(Long id, Pageable pageable) {
        return reviewRepository.findAllByUser(id, pageable);
    }

    public Optional<Review> loadReviewByUserAndFilmwork(Long usersId, Long filmworkId) {
        return reviewRepository.findByUserIdAndFilmworkId(usersId, filmworkId);
    }

    public boolean isExistingByUserIdAndFilmworkId(Long usersId, Long filmworkId) {
        return loadReviewByUserAndFilmwork(usersId, filmworkId).isPresent();
    }

    public Page<Review> loadReviewsByFilmwork(Long id, Pageable pageable) {
        return reviewRepository.findAllByFilmwork(id, pageable);
    }

    public Page<Review> loadReviews(Pageable pageable) { return reviewRepository.findAll(pageable);}

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Review updatedReview) {
        Optional<Review> oldReview = reviewRepository.findById(updatedReview.getId());

        if (oldReview.isEmpty())
            return null;

        if (updatedReview.getDate() == null)
            updatedReview.setDate(oldReview.get().getDate());

        return reviewRepository.save(updatedReview);
    }

    public void removeReview(Long id) {

        Optional<Review> foundReview = reviewRepository.findById(id);

        if (foundReview.isEmpty())
            throw new NullPointerException("Review for deletion wasn't found!");

        reviewRepository.deleteById(id);

    }
}
