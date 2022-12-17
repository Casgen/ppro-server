package cz.filmdb.service;

import cz.filmdb.model.Genre;
import cz.filmdb.model.Movie;
import cz.filmdb.repo.GenreRepository;
import cz.filmdb.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).get();
    }

    public List<Movie> getMoviesByGenres(List<Long> genreIds) {
        return movieRepository.findMoviesByGenres(genreIds);
    }

}
