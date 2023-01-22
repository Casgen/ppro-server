package cz.filmdb.conf;

import cz.filmdb.enums.RoleType;
import cz.filmdb.model.*;
import cz.filmdb.repo.*;
import cz.filmdb.service.StorageService;
import cz.filmdb.service.StorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@Configuration
@EnableSpringDataWebSupport
public class FlmDBConfig {

    private final UserRepository userRepository;

    @Autowired
    public FlmDBConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    CommandLineRunner commandLineRunner(PersonRepository personRepository, OccupationRepository occupationRepository,
                                        UserRepository userRepository, ReviewRepository reviewRepository,
                                        FilmWorkRepository filmworkRepository) {
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

            // -----------

            Person steveCarrel = new Person("Steve", "Carrel");
            Person jennaFischer = new Person("Jenna", "Fischer");
            Person johnKrasinski = new Person("John", "Krasinski");


            List<User> users = new ArrayList<>();

            User basicJoe = new User("basicjoe231", "basic.joe@gmail.com", "123456", UserRole.USER);
            User mckinsley = new User("mckinsley", "mckinsley87@gmail.com", "123456", UserRole.USER);
            User elenorRigby = new User("elenorRigby", "elenor.Rigby@gmail.com", "3890jdfhs", UserRole.USER);

            users.add(basicJoe);
            users.add(mckinsley);
            users.add(elenorRigby);
            userRepository.saveAll(users);

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

            people.add(steveCarrel);
            people.add(jennaFischer);
            people.add(johnKrasinski);

            personRepository.saveAll(people);

            //Genres
            Genre horror = new Genre("Horror");
            Genre action = new Genre("Action");
            Genre crime = new Genre("Crime");
            Genre drama = new Genre("Drama");
            Genre scifi = new Genre("Sci-fi");
            Genre mockumentary = new Genre("Mockumentary");
            Genre comedy = new Genre("Comedy");

            // ATTENTION: We don't have to persist the genres if they are referenced by a movie or a TV Show.
            // if we save for example the movies, the saving will also propagate to the genres implicitly.
            // And that is why we get the PersistentObjectException (the genres which are saved or persisted beforehand
            // are detached from the current session)

            //genreRepository.saveAll(List.of(horror,action,crime,drama,scifi));



            // Movies
            Filmwork alien = new Movie("Alien", LocalDate.of(1979,3,13), Set.of(scifi, horror));
            Filmwork killBill = new Movie("Kill Bill Vol. 1", LocalDate.of(2003, 9, 29), Set.of(action,crime,drama));
            Filmwork avatar = new Movie("Avatar", LocalDate.of(2009, 9, 23), Set.of(action, scifi));

            // TVShows
            Filmwork theOffice = new TVShow("The Office", LocalDate.of(2005, 3, 16),LocalDate.of(2013, 5, 16), Set.of(comedy, mockumentary), 9);

            filmworkRepository.saveAll(List.of(alien,killBill,avatar, theOffice));

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

            Map<Person, List<RoleType>> theOfficeOccupations = new HashMap<>();
            theOfficeOccupations.put(steveCarrel, List.of(RoleType.ACTOR));
            theOfficeOccupations.put(jennaFischer, List.of(RoleType.ACTOR));
            theOfficeOccupations.put(johnKrasinski, List.of(RoleType.ACTOR));

            alien.setOccupations(alienOccupations);
            avatar.setOccupations(avatarOccupations);
            killBill.setOccupations(killBillOccupations);
            theOffice.setOccupations(theOfficeOccupations);

            occupationRepository.saveAll(Occupation.of(alien,alienOccupations));
            occupationRepository.saveAll(Occupation.of(killBill,killBillOccupations));
            occupationRepository.saveAll(Occupation.of(avatar,avatarOccupations));
            occupationRepository.saveAll(Occupation.of(theOffice,theOfficeOccupations));


            List<Review> reviews = new ArrayList<>();

            reviews.add(new Review(basicJoe, alien, "What a banger film.", 10.0f));
            reviews.add(new Review(elenorRigby, killBill, "One of the goriest tarantino movies.", 9.7f));
            reviews.add(new Review(mckinsley, killBill, "Classic.", 9.0f));
            reviews.add(new Review(mckinsley, avatar, "ngl, kinda mid.", 5.0f));
            reviews.add(new Review(basicJoe, avatar, "cringey.", 2.3f));

            reviewRepository.saveAll(reviews);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        /*PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);*/

        return userRepository::findByUsername;

    }

    @Bean
    public StorageService storageService() {
        StorageService storageService = new StorageServiceImpl();
        storageService.init();

        return storageService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
