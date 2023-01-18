package cz.filmdb.controller;

import cz.filmdb.model.Review;
import cz.filmdb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Review> getReviews() {
        return reviewService.loadReviews();
    }

    @GetMapping("/user/{id}")
    public List<Review> getReviewsByUser(@PathVariable("id") String id) {
        return reviewService.loadReviewsByUser(Long.parseLong(id));
    }

    @GetMapping("/filmwork/{id}")
    public List<Review> getReviewsByFilmwork(@PathVariable("id") String id) {
        return reviewService.loadReviewsByFilmwork(Long.parseLong(id));
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
