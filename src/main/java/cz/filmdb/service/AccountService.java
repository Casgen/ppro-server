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


    public User addToPlansToWatch(Long usersId, Long filmworkId) throws IllegalStateException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (user.getPlansToWatch().contains(filmwork))
            throw new IllegalStateException("Filmwork already has been added!");

        user.addToPlansToWatch(filmwork);
        return userRepository.save(user);
    }

    public User addToIsWatching(Long usersId, Long filmworkId) throws IllegalStateException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (user.getIsWatching().contains(filmwork))
            throw new IllegalStateException("Filmwork already has been added!");

        user.addToWatching(filmwork);
        return userRepository.save(user);
    }

    public User addToHasWatched(Long usersId, Long filmworkId) throws IllegalStateException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (user.getHasWatched().contains(filmwork))
            throw new IllegalStateException("Filmwork already has been added!");

        user.addToHasWatched(filmwork);
        return userRepository.save(user);
    }

    public User addToWontWatch(Long usersId, Long filmworkId) throws IllegalStateException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (user.getWontWatch().contains(filmwork))
            throw new IllegalStateException("Filmwork already has been added!");

        user.addToWontWatch(filmwork);
        return userRepository.save(user);
    }

    // ------------------------------------------------------------

    public User removeFromWontWatch(Long usersId, Long filmworkId) throws NullPointerException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (!user.getWontWatch().contains(filmwork))
            throw new NullPointerException("Filmwork in the user's 'won't watch' list was not found!");

        user.removeFromWontWatch(filmwork);
        return userRepository.save(user);
    }

    public User removeFromHasWatched(Long usersId, Long filmworkId) throws NullPointerException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (!user.getHasWatched().contains(filmwork))
            throw new NullPointerException("Filmwork in user's 'has watched' list was not found!");

        user.removeFromHasWatched(filmwork);
        return userRepository.save(user);
    }

    public User removeFromIsWatching(Long usersId, Long filmworkId) throws NullPointerException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (!user.getIsWatching().contains(filmwork))
            throw new NullPointerException("Filmwork in user's 'is watching' list was not found!");

        user.removeFromIsWatching(filmwork);
        return userRepository.save(user);
    }

    public User removeFromPlansToWatch(Long usersId, Long filmworkId) throws NullPointerException {

        User user = getUserById(usersId);
        Filmwork filmwork = getFilmWorkById(filmworkId);

        if (!user.getPlansToWatch().contains(filmwork))
            throw new NullPointerException("Filmwork was not found in the user's planned watchlist!");

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

