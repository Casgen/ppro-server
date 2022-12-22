package cz.filmdb.repo;

import cz.filmdb.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


    @Query("SELECT p FROM Person p WHERE SOUNDEX(p.firstName) = SOUNDEX(:firstName) OR SOUNDEX(p.lastName) = SOUNDEX(:lastName)")
    List<Person> findAllByFirstNameOrLastName(String firstName, String lastName);

    //This one is used only if the query is split only into 1 string
    @Query("SELECT p FROM Person p WHERE SOUNDEX(p.firstName) = SOUNDEX(:name) OR SOUNDEX(p.lastName) = SOUNDEX(:name)")
    List<Person> findAllByName(String name);
}
