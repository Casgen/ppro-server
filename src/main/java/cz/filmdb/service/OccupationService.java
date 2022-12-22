package cz.filmdb.service;

import cz.filmdb.repo.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OccupationService {

    private OccupationRepository occupationRepository;

    @Autowired
    public OccupationService(OccupationRepository occupationRepository) {
        this.occupationRepository = occupationRepository;
    }
}
