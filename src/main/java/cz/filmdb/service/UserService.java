package cz.filmdb.service;

import cz.filmdb.model.User;
import cz.filmdb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> loadUsers() {
        return userRepository.findAll();
    }

    public Optional<User> loadUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) {
        Optional<User> oldUser = userRepository.findById(updatedUser.getId());

        if (oldUser.isEmpty())
            return null;

        return userRepository.save(updatedUser);
    }
}
