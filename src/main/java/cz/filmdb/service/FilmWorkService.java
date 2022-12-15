package cz.filmdb.service;

import cz.filmdb.repo.FilmWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmWorkService {

    private FilmWorkRepository filmWorkRepository;

    @Autowired
    public FilmWorkService(FilmWorkRepository filmWorkRepository) {
        this.filmWorkRepository = filmWorkRepository;
    }
}
