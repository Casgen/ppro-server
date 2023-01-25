package cz.filmdb.controller;

import cz.filmdb.model.Movie;
import cz.filmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/movies")
@CrossOrigin("http://localhost:5173")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public Page<Movie> getMovies(Pageable pageable) {
        return movieService.loadMovies(pageable);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable String id) {
        return movieService.loadMovieById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping("/by-genres")
    public Page<Movie> getMoviesByGenres(@RequestParam(name = "ids") List<String> genreIdsParam, Pageable pageable) {
        List<Long> genreIds = genreIdsParam.stream().map(Long::parseLong).toList();

        return movieService.loadMoviesByGenres(genreIds, pageable);
    }

    @GetMapping("/search")
    public Page<Movie> searchMovies(@RequestParam String query, Pageable pageable) {
        return movieService.searchMoviesByName(query, pageable);
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PutMapping
    public Movie putMovie(@RequestBody Movie movie) {
        return movieService.updateMovie(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable(name = "id") String id) {
        movieService.removeMovie(Long.parseLong(id));
        return ResponseEntity.ok().body("Movie was successfully deleted.");
    }
}
