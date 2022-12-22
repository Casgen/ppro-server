package cz.filmdb.conf;

import cz.filmdb.enums.RoleType;
import cz.filmdb.model.Genre;
import cz.filmdb.model.Movie;
import cz.filmdb.model.Occupation;
import cz.filmdb.model.Person;
import cz.filmdb.repo.GenreRepository;
import cz.filmdb.repo.MovieRepository;
import cz.filmdb.repo.OccupationRepository;
import cz.filmdb.repo.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class FlmDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository, GenreRepository genreRepository,
                                        PersonRepository personRepository, OccupationRepository occupationRepository) {
        return args -> {

            List<Person> people = new ArrayList<>();
            //People
            Person ridleyScott = new Person("Ridley", "Scott");
            Person sigourneyWeaver = new Person("Sigourney", "Weaver");
            Person danOBannon = new Person("Dan", "O'Bannon");
            Person ronaldShusett = new Person("Ronald", "Shusett");
            Person tomSkerrit = new Person("Tom", "Skerrit");
            Person johnHurt = new Person("John", "Hurt");

            Person quentinTarantino = new Person("Quentin", "Tarantino");
            Person umaThurman = new Person("Uma", "Thurman");
            Person davidCarradine = new Person("David", "Carradine");
            Person darylHannah = new Person("Daryl", "Hannah");
            Person lucyLiu = new Person("Lucy", "Liu");
            Person michaelMadsen = new Person("Michael","Madsen");

            Person jamesCameron = new Person("James", "Cameron");
            Person samWorthington = new Person("Sam", "Worthington");
            Person zoeSaldana = new Person("Zoe", "Saldana");

            people.add(ridleyScott);
            people.add(sigourneyWeaver);
            people.add(danOBannon);
            people.add(ronaldShusett);
            people.add(tomSkerrit);
            people.add(johnHurt);

            people.add(quentinTarantino);
            people.add(umaThurman);
            people.add(davidCarradine);
            people.add(darylHannah);
            people.add(lucyLiu);
            people.add(michaelMadsen);

            people.add(jamesCameron);
            people.add(samWorthington);
            people.add(zoeSaldana);

            personRepository.saveAll(people);

            //Genres
            Genre horror = new Genre("Horror");
            Genre action = new Genre("Action");
            Genre crime = new Genre("Crime");
            Genre drama = new Genre("Drama");
            Genre scifi = new Genre("Sci-fi");

            // ATTENTION: We don't have to persist the genres if they are referenced by a movie or a TV Show.
            // If an object is found with referenced objects, those referenced objects will be saved automatically. that means
            // if we save for example the movies, the saving will also propagate to the genres implicitly.
            // And that is why we get the PersistentObjectException (the genres which are saved or persisted beforehand
            // are detached from the current session)

            //genreRepository.saveAll(List.of(horror,action,crime,drama,scifi));



            //Movies
            Movie alien = new Movie("Alien", LocalDate.of(1979,3,13), Set.of(scifi, horror));
            Movie killBill = new Movie("Kill Bill Vol. 1", LocalDate.of(2003, 9, 29), Set.of(action,crime,drama));
            Movie avatar = new Movie("Avatar", LocalDate.of(2009, 9, 23), Set.of(action, scifi));

            movieRepository.saveAll(List.of(alien,killBill,avatar));

            //Filmwork occupations
            Map<Person, List<RoleType>> alienOccupations = new HashMap<>();

            alienOccupations.put(ridleyScott,List.of(RoleType.DIRECTOR));
            alienOccupations.put(sigourneyWeaver, List.of(RoleType.ACTOR));
            alienOccupations.put(tomSkerrit, List.of(RoleType.ACTOR));
            alienOccupations.put(johnHurt, List.of(RoleType.ACTOR));
            alienOccupations.put(ronaldShusett, List.of(RoleType.WRITER));
            alienOccupations.put(danOBannon, List.of(RoleType.WRITER));

            Map<Person, List<RoleType>> killBillOccupations = new HashMap<>();

            killBillOccupations.put(quentinTarantino, List.of(RoleType.WRITER, RoleType.DIRECTOR));
            killBillOccupations.put(umaThurman, List.of(RoleType.WRITER, RoleType.ACTOR));
            killBillOccupations.put(michaelMadsen, List.of(RoleType.ACTOR));
            killBillOccupations.put(davidCarradine, List.of(RoleType.ACTOR));
            killBillOccupations.put(lucyLiu, List.of(RoleType.ACTOR));
            killBillOccupations.put(darylHannah, List.of(RoleType.ACTOR));

            Map<Person, List<RoleType>> avatarOccupations = new HashMap<>();
            avatarOccupations.put(jamesCameron, List.of(RoleType.WRITER, RoleType.DIRECTOR));
            avatarOccupations.put(sigourneyWeaver, List.of(RoleType.ACTOR));
            avatarOccupations.put(samWorthington, List.of(RoleType.ACTOR));
            avatarOccupations.put(zoeSaldana, List.of(RoleType.ACTOR));

            occupationRepository.saveAll(Occupation.of(alien,alienOccupations));
            occupationRepository.saveAll(Occupation.of(killBill,killBillOccupations));
            occupationRepository.saveAll(Occupation.of(avatar,avatarOccupations));

            alien.setOccupation(alienOccupations);
            avatar.setOccupation(avatarOccupations);
            killBill.setOccupation(killBillOccupations);
        };
    }
}
