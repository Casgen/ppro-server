package cz.filmdb.service;

import cz.filmdb.model.Genre;
import cz.filmdb.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
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
}
