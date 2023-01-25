package cz.filmdb.controller;

import cz.filmdb.model.Review;
import cz.filmdb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/reviews")
@CrossOrigin("http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public Page<Review> getReviews(Pageable pageable) {
        return reviewService.loadReviews(pageable);
    }

    @GetMapping("{id}")
    public Review getReview(@PathVariable(name = "id") Long id) {
        return reviewService.loadReviewById(id).orElse(null);
    }

    @GetMapping("/by-user/{id}")
    public Page<Review> getReviewsByUser(@PathVariable("id") String id, Pageable pageable) {
        return reviewService.loadReviewsByUser(Long.parseLong(id), pageable);
    }

    @GetMapping("/by-filmwork/{id}")
    public Page<Review> getReviewsByFilmwork(@PathVariable("id") String id, Pageable pageable) {
        return reviewService.loadReviewsByFilmwork(Long.parseLong(id), pageable);
    }

    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.saveReview(review);
    }

    @PutMapping
    public Review putReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.removeReview(id);
        return ResponseEntity.ok().body("Review was deleted successfully");
    }
}
