package cz.filmdb.service;

import cz.filmdb.model.Genre;
import cz.filmdb.model.TVShow;
import cz.filmdb.model.User;
import cz.filmdb.repo.TVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TVShowService {

    private final TVShowRepository tvShowRepository;

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

    public TVShow saveTvShow(TVShow tvShow) {
        return tvShowRepository.save(tvShow);
    }

    public TVShow updateTvShow(TVShow updatedTvShow) {

        Optional<TVShow> oldTvShow = tvShowRepository.findById(updatedTvShow.getFid());

        if (oldTvShow.isEmpty())
            return null;

        return tvShowRepository.save(updatedTvShow);
    }
}
