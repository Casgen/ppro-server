package cz.filmdb.web;

import cz.filmdb.model.Person;
import cz.filmdb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/people/")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("getPeople")
    public List<Person> getPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("getPerson/{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.getPerson(Long.parseLong(id));
    }
}
