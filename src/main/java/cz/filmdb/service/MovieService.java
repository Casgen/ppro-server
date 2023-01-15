package cz.filmdb.service;

import cz.filmdb.model.Movie;
import cz.filmdb.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> loadMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> loadMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> loadMoviesByGenres(List<Long> genreIds) {
        return movieRepository.findMoviesByGenres(genreIds);
    }

    public List<Movie> loadLatestMovies() {
        return movieRepository.findLatestMovies();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie updatedMovie) {
        Optional<Movie> oldReview = movieRepository.findById(updatedMovie.getFid());

        if (oldReview.isEmpty())
            return null;

        return movieRepository.save(updatedMovie);
    }
}
