package cz.filmdb.service;

import cz.filmdb.model.TVShow;
import cz.filmdb.model.User;
import cz.filmdb.repo.TVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    public Page<TVShow> loadTVShows(Pageable pageable) {
        return tvShowRepository.findAll(pageable);
    }

    public Page<TVShow> loadTvShowsByGenre(List<Long> ids, Pageable pageable) {
        return tvShowRepository.findAllByGenres(ids, pageable);
    }

    public Optional<TVShow> loadTVShowById(Long id) {
        return tvShowRepository.findById(id);
    }

    public TVShow saveTvShow(TVShow tvShow) {
        return tvShowRepository.save(tvShow);
    }

    public TVShow updateTvShow(TVShow updatedTvShow) throws ChangeSetPersister.NotFoundException {

        TVShow oldTvShow = tvShowRepository.findById(updatedTvShow.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        updatedTvShow.setGenres(oldTvShow.getGenres());
        updatedTvShow.setReviews(oldTvShow.getReviews());
        updatedTvShow.setOccupations(oldTvShow.getOccupations());
        updatedTvShow.setImgPaths(oldTvShow.getImgPaths());

        return tvShowRepository.save(updatedTvShow);
    }

    public void removeTvShow(Long id) {
        tvShowRepository.deleteById(id);
    }

    public Page<TVShow> searchByName(String query, Pageable pageable) {
        return tvShowRepository.findAllByNameContainingIgnoreCase(query, pageable);
    }
}
