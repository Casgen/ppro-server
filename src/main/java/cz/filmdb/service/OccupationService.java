package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;
import cz.filmdb.repo.FilmWorkRepository;
import cz.filmdb.repo.OccupationRepository;
import cz.filmdb.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService {

    private final OccupationRepository occupationRepository;
    private final PersonRepository personRepository;
    private final FilmWorkRepository filmworkRepository;

    @Autowired
    public OccupationService(OccupationRepository occupationRepository, PersonRepository personRepository,
                             FilmWorkRepository filmworkRepository) {
        this.occupationRepository = occupationRepository;
        this.personRepository = personRepository;
        this.filmworkRepository = filmworkRepository;
    }

    public Page<Occupation> loadOccupationsByFilmWork(Long id, Pageable pageable) {
        return occupationRepository.findAllByFilmWork(id, pageable);
    }

    public Page<Occupation> loadOccupationsByPerson(Long id, Pageable pageable) {
        return occupationRepository.findAllByPerson(id, pageable);
    }

    public void removeOccupation(Long id) {
        occupationRepository.deleteById(id);
    }

    public List<Occupation> saveOccupations(List<Occupation> occupations) {

        List<Person> foundPeople = personRepository.findAllById(occupations.stream().map( item -> item.getPerson().getId()).toList());

        List<Filmwork> foundFilmworks = filmworkRepository.findAllById(occupations.stream().map(item -> item.getFilmwork().getId()).toList());

        if (foundPeople.size() != occupations.size() || foundFilmworks.size() != occupations.size())
            throw new IllegalStateException("Not all entities were found!");

        return occupationRepository.saveAll(occupations);
    }

}
