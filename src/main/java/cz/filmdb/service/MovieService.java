package cz.filmdb.service;

import cz.filmdb.model.Genre;
import cz.filmdb.model.Movie;
import cz.filmdb.repo.GenreRepository;
import cz.filmdb.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    public Page<Movie> loadMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Optional<Movie> loadMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Page<Movie> loadMoviesByGenres(List<Long> genreIds, Pageable pageable) {
        return movieRepository.findAllByGenreIds(genreIds, pageable);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Movie updatedMovie) {
        Optional<Movie> oldReview = movieRepository.findById(updatedMovie.getId());

        if (oldReview.isEmpty())
            return null;

        return movieRepository.save(updatedMovie);
    }

    public void removeMovie(Long id) {

        Optional<Movie> foundMovie = movieRepository.findById(id);

        if (foundMovie.isEmpty())
            throw new NullPointerException("Genre with a given id wasn't found!");

        List<Genre> associatedGenres = genreRepository.findAllByFilmworksIn(Set.of(foundMovie.get()));

        for (Genre filmwork : associatedGenres) {
            filmwork.removeFilmwork(foundMovie.get());
        }

        movieRepository.deleteById(id);
    }
}
