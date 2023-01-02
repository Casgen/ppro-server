package cz.filmdb.web;

import cz.filmdb.model.Review;
import cz.filmdb.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("api/v1/reviews/")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("getReviewsByUser/{id}")
    public List<Review> getReviewsByUser(@PathVariable("id") String id) {
        return reviewService.getReviewsByUser(Long.parseLong(id));
    }

    @GetMapping("getReviewsByFilmwork/{id}")
    public List<Review> getReviewsByFilmwork(@PathVariable("id") String id) {
        return reviewService.getReviewsByFilmwork(Long.parseLong(id));
    }
}
