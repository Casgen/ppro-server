package cz.filmdb.service;

import cz.filmdb.repo.TVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TVShowService {

    private TVShowRepository tvShowRepository;

    @Autowired
    public TVShowService(TVShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }
}
