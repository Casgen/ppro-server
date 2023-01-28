package cz.filmdb.controller;

import cz.filmdb.model.Review;
import cz.filmdb.model.User;
import cz.filmdb.service.JwtService;
import cz.filmdb.service.ReviewService;
import cz.filmdb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceAlreadyExistsException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/reviews")
@CrossOrigin("http://localhost:5173")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService, JwtService jwtService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.jwtService = jwtService;
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
    public Review createReview(@RequestBody Review review, HttpServletRequest request) {
        try {
            if (review.getFilmwork() == null || review.getFilmwork().getId() == null)
                throw new NullPointerException("Filmwork's id is not present!");

            Long userId = Long.parseLong(jwtService.extractUserId(retrieveToken(request)));

            if (userId == null)
                throw new RuntimeException("User id couldn't be extracted");

            if (reviewService.isExistingByUserIdAndFilmworkId(userId, review.getFilmwork().getId()))
                throw new InstanceAlreadyExistsException("User has already created a review for this filmwork!");

            review.setUser(new User(userId));

            return reviewService.saveReview(review);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (InstanceAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @PutMapping
    public Review putReview(@RequestBody Review review, HttpServletRequest request) {
        try {
            Long userId = Long.parseLong(jwtService.extractUserId(retrieveToken(request)));

            Review foundReview = reviewService.loadReviewById(review.getId())
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);

            if (userId == null) throw new RuntimeException("User id couldn't be extracted");

            review.setUser(new User(userId));

            if (userId != foundReview.getUser().getId())
                throw new AuthorizationServiceException("Can not modify other's user's review!");

            return reviewService.saveReview(review);

        } catch (AuthorizationServiceException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, HttpServletRequest request) {
        try {
            Long userId = Long.parseLong(jwtService.extractUserId(retrieveToken(request)));

            Review review = reviewService.loadReviewById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);

            if (userId == null)
                throw new RuntimeException("User id couldn't be extracted");

            if (userId != review.getUser().getId())
                throw new AuthorizationServiceException("Can not remove other's user's review!");

            reviewService.removeReview(id);
            return ResponseEntity.ok().body("Review was deleted successfully");

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
