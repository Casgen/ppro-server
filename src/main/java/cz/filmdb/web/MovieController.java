package cz.filmdb.web;

import cz.filmdb.model.Movie;
import cz.filmdb.model.Person;
import cz.filmdb.model.Review;
import cz.filmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies/")
@CrossOrigin("http://localhost:5173")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("latest")
    public List<Movie> getLatestMovies() {
        return movieService.getLatestMovies();
    }

    @GetMapping("{id}")
    public Movie getMovieById(@PathVariable String id) {
        return movieService.loadMovieById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping("genres")
    public List<Movie> getMoviesByGenres(@RequestParam(name = "genres") List<Long> genreIds) {
        if (genreIds.isEmpty()) return List.of();

        return movieService.getMoviesByGenres(genreIds);
    }

    @PostMapping
    public ResponseEntity.BodyBuilder createMovie(@RequestBody Movie movie) {
        Movie newMovie = movieService.saveMovie(movie);

        if (newMovie != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }

    @PutMapping
    public ResponseEntity.BodyBuilder putMovie(@RequestBody Movie movie) {
        Movie updatedMovie = movieService.updateMovie(movie);

        if (updatedMovie != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }
}
