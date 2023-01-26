package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.User;
import cz.filmdb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> loadUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> loadUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public Page<User> searchByUsername(String query, Pageable pageable) {
        return userRepository.findAllByUsernameContainingIgnoreCase(query, pageable);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) {
        Optional<User> oldUser = userRepository.findById(updatedUser.getId());

        if (oldUser.isEmpty())
            return null;

        if (updatedUser.getUserRole() == null)
            updatedUser.setUserRole(oldUser.get().getUserRole());

        if (updatedUser.getPassword() != null) {

            String updatedPassword = passwordEncoder.encode(updatedUser.getPassword());

            if (!oldUser.get().getPassword().equals(updatedPassword)) {
                updatedUser.setPassword(updatedPassword);
            } else {
                updatedUser.setPassword(oldUser.get().getPassword());
            }

            return userRepository.save(updatedUser);
        }

        updatedUser.setPassword(oldUser.get().getPassword());

        return userRepository.save(updatedUser);
    }

    public void removeUser(Long id) {

        Optional<User> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty())
            throw new NullPointerException("User with the given id wasn't found!");

        userRepository.deleteById(id);
    }

    public Page<Filmwork> loadPlansToWatchListByUserId(Long id, Pageable pageable) {
        return userRepository.findUsersPlansToWatchListById(id, pageable);
    }

    public Page<Filmwork> loadIsWatchingListByUserId(Long id, Pageable pageable) {
        return userRepository.findUsersWatchingListById(id, pageable);
    }

    public Page<Filmwork> loadHasWatchedListByUserId(Long id, Pageable pageable) {
        return userRepository.findUsersWatchingListById(id, pageable);
    }
}
