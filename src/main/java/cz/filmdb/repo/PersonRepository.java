package cz.filmdb.repo;

import cz.filmdb.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


    List<Person> findAllByFirstNameOrLastName(String firstName, String lastName);
}
