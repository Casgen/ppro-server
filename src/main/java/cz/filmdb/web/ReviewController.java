package cz.filmdb.web;

import cz.filmdb.model.Review;
import cz.filmdb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/reviews/")
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

    @GetMapping("user/{id}")
    public List<Review> getReviewsByUser(@PathVariable("id") String id) {
        return reviewService.loadReviewsByUser(Long.parseLong(id));
    }

    @GetMapping("filmwork/{id}")
    public List<Review> getReviewsByFilmwork(@PathVariable("id") String id) {
        return reviewService.loadReviewsByFilmwork(Long.parseLong(id));
    }
}
