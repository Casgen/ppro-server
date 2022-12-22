package cz.filmdb.service;

import cz.filmdb.model.Genre;
import cz.filmdb.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public List<Genre> searchGenres(String query) {
        return genreRepository.findByNameContainingIgnoreCase(query);
    }
}
