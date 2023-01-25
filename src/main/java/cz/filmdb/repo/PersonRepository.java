package cz.filmdb.repo;

import cz.filmdb.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


    @Query("SELECT p FROM Person p WHERE SOUNDEX(p.firstName) = SOUNDEX(:firstName) OR SOUNDEX(p.lastName) = SOUNDEX(:lastName)")
    Page<Person> findAllByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);

    //This one is used only if the query is split only into 1 string
    @Query("SELECT p FROM Person p WHERE SOUNDEX(p.firstName) = SOUNDEX(:name) OR SOUNDEX(p.lastName) = SOUNDEX(:name)")
    Page<Person> findAllByName(String name, Pageable pageable);
}
