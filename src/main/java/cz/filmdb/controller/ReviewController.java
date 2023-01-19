package cz.filmdb.controller;

import cz.filmdb.model.Review;
import cz.filmdb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews")
@CrossOrigin("http://localhost:5173")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public Page<Review> getReviews(Pageable pageable) {
        return reviewService.loadReviews(pageable);
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
    public ResponseEntity.BodyBuilder createReview(@RequestBody Review review) {
        Review newReview = reviewService.saveReview(review);

        if (newReview != null) return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }

    @PutMapping
    public ResponseEntity.BodyBuilder putReview(@RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(review);

        if (updatedReview != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }
}