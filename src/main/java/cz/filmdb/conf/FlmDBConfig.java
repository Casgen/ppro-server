package cz.filmdb.conf;

import cz.filmdb.enums.RoleType;
import cz.filmdb.model.*;
import cz.filmdb.repo.*;
import cz.filmdb.service.AuthenticationService;
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
                                        FilmWorkRepository filmworkRepository, AuthenticationService authenticationService) {
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

            Person francisCoppola = new Person("Francis", "Coppola");
            Person marioPuzo = new Person("Mario", "Puzo");
            Person marlonBrando = new Person("Marlon", "Brando");
            Person alPacino = new Person("Al", "Pacino");
            Person jamesCaan = new Person("James", "Caan");

            Person tomHanks = new Person("Tom", "Hanks");
            Person robertZemeckis = new Person("Robert", "Zemeckis");
            Person winstonGroom = new Person("Winston", "Groom");
            Person ericRoth = new Person("Eric", "Roth");
            Person robinWright = new Person("Robin", "Wright");
            Person garySinise = new Person("Gary", "Sinise");

            Person johnTravolta = new Person("John", "Travolta");
            Person samuelJackson = new Person("Samuel", "Jackson");

            // -----------

            Person steveCarrel = new Person("Steve", "Carrel");
            Person jennaFischer = new Person("Jenna", "Fischer");
            Person johnKrasinski = new Person("John", "Krasinski");
            Person gregDaniels = new Person("Greg", "Daniels");


            Person amyPoehler = new Person("Amy", "Poehler");
            Person jimoHeir = new Person("Jim", "O'Heir");
            Person nickOfferman = new Person("Nick", "Offerman");

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

            people.add(francisCoppola);
            people.add(marioPuzo);
            people.add(marlonBrando);
            people.add(alPacino);
            people.add(jamesCaan);

            people.add(tomHanks);
            people.add(robertZemeckis);
            people.add(winstonGroom);
            people.add(ericRoth);
            people.add(robinWright);
            people.add(garySinise);

            people.add(johnTravolta);
            people.add(samuelJackson);

            people.add(steveCarrel);
            people.add(jennaFischer);
            people.add(johnKrasinski);

            people.add(gregDaniels);
            people.add(amyPoehler);
            people.add(jimoHeir);
            people.add(nickOfferman);

            personRepository.saveAll(people);

            //Genres
            Genre horror = new Genre("Horror");
            Genre action = new Genre("Action");
            Genre crime = new Genre("Crime");
            Genre drama = new Genre("Drama");
            Genre scifi = new Genre("Sci-fi");
            Genre mockumentary = new Genre("Mockumentary");
            Genre comedy = new Genre("Comedy");
            Genre romance = new Genre("Romance");

            // ATTENTION: We don't have to persist the genres if they are referenced by a movie or a TV Show.
            // if we save for example the movies, the saving will also propagate to the genres implicitly.
            // And that is why we get the PersistentObjectException (the genres which are saved or persisted beforehand
            // are detached from the current session)

            //genreRepository.saveAll(List.of(horror,action,crime,drama,scifi));


            // Movies
            Filmwork alien = new Movie("Alien", LocalDate.of(1979,3,13), Set.of(scifi, horror));
            Filmwork killBill = new Movie("Kill Bill Vol. 1", LocalDate.of(2003, 9, 29), Set.of(action,crime,drama));
            Filmwork avatar = new Movie("Avatar", LocalDate.of(2009, 9, 23), Set.of(action, scifi));
            Filmwork theGodfather = new Movie("The Godfather", LocalDate.of(1972, 8,30), Set.of(crime, drama));
            Filmwork forrestGump = new Movie("Forrest Gump", LocalDate.of(1994, 2,15), Set.of(romance, drama));
            Filmwork pulpFiction = new Movie("Pulp Fiction", LocalDate.of(1994, 4,2), Set.of(drama, crime));

            // TVShows
            Filmwork theOffice = new TVShow("The Office", LocalDate.of(2005, 3, 16),LocalDate.of(2013, 5, 16), Set.of(comedy, mockumentary), 9);
            Filmwork parksAndRecreation = new TVShow("Parks and Recreation", LocalDate.of(2009, 3, 16),LocalDate.of(2015, 5, 16), Set.of(comedy, mockumentary), 7);

            // FILMWORKS ARE PERSISTED THROUGH THE USERS!

            filmworkRepository.saveAll(Set.of(alien,killBill,avatar,theGodfather,theOffice, parksAndRecreation, pulpFiction, forrestGump));

            authenticationService.register(new RegisterRequest("basicjoe231", "basic.joe@gmail.com", "123456"));
            authenticationService.register(new RegisterRequest("mckinsley", "mckinsley87@gmail.com", "123456"));
            authenticationService.register(new RegisterRequest("elenorRigby", "elenor.Rigby@gmail.com", "123456789"));
            authenticationService.register(new RegisterRequest("theUltimate", "the.ultimate@gmail.com", "123456789"));
            authenticationService.register(new RegisterRequest("THXsound", "that.thx.sound@gmail.com", "thxsound"));

            User basicJoe = userRepository.findByUsername("basicjoe231");

            basicJoe.addToPlansToWatch(killBill);
            basicJoe.addToHasWatched(alien);
            basicJoe.addToHasWatched(avatar);
            basicJoe.addToHasWatched(forrestGump);

            userRepository.save(basicJoe);

            User mckinsley = userRepository.findByUsername("mckinsley");

            mckinsley.addToPlansToWatch(theOffice);
            mckinsley.addToPlansToWatch(alien);
            mckinsley.addToWatching(theOffice);

            userRepository.save(mckinsley);

            User elenorRigby = userRepository.findByUsername("elenorRigby");

            elenorRigby.addToWatching(avatar);
            elenorRigby.addToHasWatched(killBill);
            elenorRigby.addToHasWatched(pulpFiction);

            userRepository.save(elenorRigby);

            User theUltimate = userRepository.findByUsername("theUltimate");

            theUltimate.addToHasWatched(theGodfather);
            theUltimate.addToPlansToWatch(parksAndRecreation);
            theUltimate.addToWatching(forrestGump);

            userRepository.save(theUltimate);

            User thx = userRepository.findByUsername("THXsound");

            thx.addToPlansToWatch(killBill);
            thx.addToHasWatched(alien);
            thx.addToHasWatched(avatar);
            thx.addToHasWatched(forrestGump);
            thx.addToWatching(pulpFiction);

            userRepository.save(thx);

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

            Map<Person, List<RoleType>> theGodfatherOccupations = new HashMap<>();
            theGodfatherOccupations.put(francisCoppola, List.of(RoleType.DIRECTOR));
            theGodfatherOccupations.put(marioPuzo, List.of(RoleType.WRITER));
            theGodfatherOccupations.put(marlonBrando, List.of(RoleType.ACTOR));
            theGodfatherOccupations.put(alPacino, List.of(RoleType.ACTOR));
            theGodfatherOccupations.put(jamesCaan, List.of(RoleType.ACTOR));

            Map<Person, List<RoleType>> forrestGumpOccupations = new HashMap<>();
            forrestGumpOccupations.put(robertZemeckis, List.of(RoleType.DIRECTOR));
            forrestGumpOccupations.put(winstonGroom, List.of(RoleType.WRITER));
            forrestGumpOccupations.put(ericRoth, List.of(RoleType.WRITER));
            forrestGumpOccupations.put(tomHanks, List.of(RoleType.ACTOR));
            forrestGumpOccupations.put(robinWright, List.of(RoleType.ACTOR));
            forrestGumpOccupations.put(garySinise, List.of(RoleType.ACTOR));

            Map<Person, List<RoleType>> pulpFictionOccupations = new HashMap<>();
            pulpFictionOccupations.put(quentinTarantino, List.of(RoleType.DIRECTOR, RoleType.ACTOR, RoleType.WRITER));
            pulpFictionOccupations.put(johnTravolta, List.of(RoleType.ACTOR));
            pulpFictionOccupations.put(samuelJackson, List.of(RoleType.ACTOR));
            pulpFictionOccupations.put(umaThurman, List.of(RoleType.ACTOR));

            Map<Person, List<RoleType>> parksAndRecreationOccupations = new HashMap<>();
            parksAndRecreationOccupations.put(gregDaniels, List.of(RoleType.CREATOR));
            parksAndRecreationOccupations.put(amyPoehler, List.of(RoleType.ACTOR));
            parksAndRecreationOccupations.put(jimoHeir, List.of(RoleType.ACTOR));
            parksAndRecreationOccupations.put(nickOfferman, List.of(RoleType.ACTOR));

            alien.setOccupations(alienOccupations);
            avatar.setOccupations(avatarOccupations);
            killBill.setOccupations(killBillOccupations);
            theOffice.setOccupations(theOfficeOccupations);
            theGodfather.setOccupations(theGodfatherOccupations);
            forrestGump.setOccupations(forrestGumpOccupations);
            pulpFiction.setOccupations(pulpFictionOccupations);
            parksAndRecreation.setOccupations(parksAndRecreationOccupations);

            occupationRepository.saveAll(Occupation.of(alien,alienOccupations));
            occupationRepository.saveAll(Occupation.of(killBill,killBillOccupations));
            occupationRepository.saveAll(Occupation.of(avatar,avatarOccupations));
            occupationRepository.saveAll(Occupation.of(theOffice,theOfficeOccupations));
            occupationRepository.saveAll(Occupation.of(theGodfather,theGodfatherOccupations));
            occupationRepository.saveAll(Occupation.of(theGodfather,forrestGumpOccupations));
            occupationRepository.saveAll(Occupation.of(pulpFiction,pulpFictionOccupations));
            occupationRepository.saveAll(Occupation.of(parksAndRecreation,parksAndRecreationOccupations));

            List<Review> reviews = new ArrayList<>();

            reviews.add(new Review(basicJoe, alien, "What a banger film. For it's time it was really something", 10.0f));
            reviews.add(new Review(elenorRigby, killBill, "One of the goriest tarantino movies. And the best ones", 9.7f));
            reviews.add(new Review(mckinsley, killBill, "Classic. That's all that is needed to be said", 9.0f));
            reviews.add(new Review(mckinsley, avatar, "I really liked the movie.", 8.0f));
            reviews.add(new Review(basicJoe, avatar, "The film is kinda mid, but visually beautiful for it's time.", 6.0f));
            reviews.add(new Review(basicJoe, forrestGump, "Very breathtaking. And the storytelling...oh my gosh", 9.0f));
            reviews.add(new Review(theUltimate, theGodfather, "I mean what can I say. Godfather is one of my favorite movies. Very well written and the actors are magnificent!", 9.5f));

            reviewRepository.saveAll(reviews);

            authenticationService.register(new RegisterRequest("admin","admin@gmail.com","password"), UserRole.ADMIN);
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
