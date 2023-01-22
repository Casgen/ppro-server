package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Genre;
import cz.filmdb.repo.FilmWorkRepository;
import cz.filmdb.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final FilmWorkRepository filmworkRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository, FilmWorkRepository filmworkRepository) {
        this.genreRepository = genreRepository;
        this.filmworkRepository = filmworkRepository;
    }

    public Optional<Genre> loadGengreById(Long id) {
        return genreRepository.findById(id);
    }

    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    public Page<Genre> searchGenres(String query, Pageable pageable) {
        return genreRepository.findAllByNameContainingIgnoreCase(query, pageable);
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Genre updatedGenre) {

        Optional<Genre> oldGenre = genreRepository.findById(updatedGenre.getId());

        if (oldGenre.isEmpty())
            return null;

        return genreRepository.save(updatedGenre);
    }

    public void removeGenre(Genre genre) {

        if (genre == null)
            throw new NullPointerException("Genre with a given id wasn't found!");

        List<Filmwork> associatedFilmworks = filmworkRepository.findAllByGenreIds(List.of(genre.getId()));

        for (Filmwork filmwork : associatedFilmworks) {
            filmwork.removeGenre(genre);
        }

        genreRepository.delete(genre);
    }

    public void removeGenreById(Long id) {

        Optional<Genre> foundGenre = genreRepository.findById(id);

        if (foundGenre.isEmpty())
            throw new NullPointerException("Genre with a given id wasn't found!");

        List<Filmwork> associatedFilmworks = filmworkRepository.findAllByGenreIds(List.of(id));

        for (Filmwork filmwork : associatedFilmworks) {
            filmwork.removeGenre(foundGenre.get());
        }

        genreRepository.deleteById(id);
    }
}
