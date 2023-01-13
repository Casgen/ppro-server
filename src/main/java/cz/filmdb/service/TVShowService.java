package cz.filmdb.service;

import cz.filmdb.model.TVShow;
import cz.filmdb.repo.TVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TVShowService {

    private TVShowRepository tvShowRepository;

    @Autowired
    public TVShowService(TVShowRepository tvShowRepository) {
        this.tvShowRepository = tvShowRepository;
    }


    public List<TVShow> loadTVShows() {
        return tvShowRepository.findAll();
    }

    public Optional<TVShow> loadTVShowById(Long id) {
        return tvShowRepository.findById(id);
    }
}
