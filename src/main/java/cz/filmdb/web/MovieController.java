package cz.filmdb.web;

import cz.filmdb.model.Movie;
import cz.filmdb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/movie/")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("getMovies")
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @GetMapping("getMovieById")
    public Movie getMovieById(@RequestParam(name = "id") Optional<Long> id) {
        if (id.isPresent())
            return movieService.getMovieById(id.get());

        throw new InvalidParameterException("Id is either null or invalid!");
    }

    @GetMapping("getMoviesByGenre")
    public List<Movie> getMoviesByGenre(@RequestParam(name = "genres") List<Long> genreIds) {
        if (genreIds.isEmpty()) return List.of();
        return movieService.getMoviesByGenres(genreIds);
    }
}
