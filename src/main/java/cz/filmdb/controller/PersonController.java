package cz.filmdb.controller;

import cz.filmdb.model.Person;
import cz.filmdb.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/people")
@CrossOrigin("http://localhost:5173")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personService.loadPeople();
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.loadPerson(Long.parseLong(id));
    }

    @PostMapping
    public ResponseEntity.BodyBuilder createPerson(@RequestBody Person person) {
        Person newPerson = personService.savePerson(person);

        if (newPerson != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }

    @PutMapping
    public ResponseEntity.BodyBuilder putPerson(@RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(person);

        if (updatedPerson != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }
}
