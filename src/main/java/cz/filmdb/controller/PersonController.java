package cz.filmdb.controller;

import cz.filmdb.model.Person;
import cz.filmdb.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/people")
@CrossOrigin("http://localhost:5173")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Page<Person> getPeople(Pageable pageable) {
        return personService.loadPeople(pageable);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable String id) {
        return personService.loadPerson(Long.parseLong(id));
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping
    public Person putPerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable(name = "id") String id) {


        personService.removePerson(Long.parseLong(id));

        return ResponseEntity.ok().body("Person was deleted successfully.");
    }
}
