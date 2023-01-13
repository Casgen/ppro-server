package cz.filmdb.web;

import cz.filmdb.model.Movie;
import cz.filmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

@RestController("api/v1/movies/")
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

    @GetMapping("getMoviesByGenres")
    public List<Movie> getMoviesByGenres(@RequestParam(name = "genres") List<Long> genreIds) {
        if (genreIds.isEmpty()) return List.of();

        return movieService.getMoviesByGenres(genreIds);
    }
}
