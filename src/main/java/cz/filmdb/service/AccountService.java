package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.User;
import cz.filmdb.repo.FilmWorkRepository;
import cz.filmdb.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final FilmWorkRepository filmWorkRepository;


    public User addToPlansToWatch(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.addToPlansToWatch(filmwork);
        return userRepository.save(user);
    }

    public User addToIsWatching(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.addToWatching(filmwork);
        return userRepository.save(user);
    }

    public User addToHasWatched(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.addToHasWatched(filmwork);
        return userRepository.save(user);
    }

    public User addToWontWatch(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.addToWontWatch(filmwork);
        return userRepository.save(user);
    }

    // ------------------------------------------------------------

    public User removeFromWontWatch(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.removeFromWontWatch(filmwork);
        return userRepository.save(user);
    }

    public User removeFromHasWatched(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.removeFromHasWatched(filmwork);
        return userRepository.save(user);
    }

    public User removeFromIsWatching(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.removeFromIsWatching(filmwork);
        return userRepository.save(user);
    }

    public User removeFromPlansToWatch(Long usersId, Long filmworkId) {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        user.removeFromPlansToWatch(filmwork);
        return userRepository.save(user);
    }

    private User getUserById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty()) throw new NullPointerException("User was not found!");

        return foundUser.get();
    }

    private Filmwork getFilmWorkById(Long id) {
        Optional<Filmwork> foundFilmwork = filmWorkRepository.findById(id);

        if (foundFilmwork.isEmpty()) throw new NullPointerException("Filmwork was not found!");

        return foundFilmwork.get();
    }
}
