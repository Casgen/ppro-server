package cz.filmdb.service;

import cz.filmdb.model.Occupation;
import cz.filmdb.repo.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;

    @Autowired
    public OccupationService(OccupationRepository occupationRepository) {
        this.occupationRepository = occupationRepository;
    }

    public Page<Occupation> loadOccupationsByFilmWork(Long id, Pageable pageable) {
        return occupationRepository.findAllByFilmWork(id, pageable);
    }

    public Page<Occupation> loadOccupationsByPerson(Long id, Pageable pageable) {
        return occupationRepository.findAllByPerson(id, pageable);
    }
}
