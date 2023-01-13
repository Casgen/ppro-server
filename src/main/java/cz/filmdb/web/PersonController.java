package cz.filmdb.web;

import cz.filmdb.model.Person;
import cz.filmdb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/people/")
@CrossOrigin("http://localhost:5173")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.loadPeople();
    }

    @GetMapping("{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.loadPerson(Long.parseLong(id));
    }
}
