package cz.filmdb.conf;

import cz.filmdb.model.Genre;
import cz.filmdb.model.Movie;
import cz.filmdb.model.Person;
import cz.filmdb.repo.FilmWorkRepository;
import cz.filmdb.repo.GenreRepository;
import cz.filmdb.repo.MovieRepository;
import cz.filmdb.repo.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class FlmDBConfig {

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository, GenreRepository genreRepository, PersonRepository personRepository) {
        return args -> {

            Person quentinTarantino = new Person("Quentin", "Tarantino");
            Person jamesCameron = new Person("James", "Cameron");
            Person ridleyScott = new Person("Ridley", "Scott");

            //personRepository.saveAll(List.of(quentinTarantino,jamesCameron,ridleyScott));

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

            Movie alien = new Movie("Alien", 1979, Set.of(scifi, horror), Set.of(ridleyScott));
            Movie killBill = new Movie("Kill Bill Vol. 1", 2003, Set.of(action,crime,drama), Set.of(quentinTarantino));
            Movie avatar = new Movie("Avatar", 2009, Set.of(action, scifi), Set.of(jamesCameron));

            movieRepository.saveAll(List.of(alien,killBill,avatar));

        };
    }
}
