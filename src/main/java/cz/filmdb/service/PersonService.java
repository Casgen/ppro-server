package cz.filmdb.service;

import cz.filmdb.model.Person;
import cz.filmdb.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public Page<Person> loadPeople(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public List<Person> findPeopleByName(String query) {
        String[] splitQuery = query.split(query);

        return switch (splitQuery.length) {
            case 1 -> personRepository.findAllByName(splitQuery[0].trim());
            case 2 -> personRepository.findAllByFirstNameOrLastName(splitQuery[0].trim(), splitQuery[1].trim());
            default -> List.of();
        };
    }

    public Person loadPerson(Long id) {
        Optional<Person> person; personRepository.findById(id);

        if ((person = personRepository.findById(id)).isEmpty())
            return null;


        return person.get();
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Person updatedPerson) {
        Optional<Person> oldPerson = personRepository.findById(updatedPerson.getId());

        if (oldPerson.isEmpty())
            return null;

        return personRepository.save(updatedPerson);
    }

    public void removePerson(Long id) {
        personRepository.deleteById(id);
    }
}
