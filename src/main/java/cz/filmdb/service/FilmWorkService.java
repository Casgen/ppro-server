package cz.filmdb.service;

import cz.filmdb.repo.FilmWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmWorkService {

    private final FilmWorkRepository filmworkRepository;

    @Autowired
    public FilmWorkService(FilmWorkRepository filmworkRepository) {
        this.filmworkRepository = filmworkRepository;
    }


}
