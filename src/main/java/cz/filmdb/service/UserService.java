package cz.filmdb.service;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.User;
import cz.filmdb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, StorageService storageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.storageService = storageService;
    }

    public Page<User> loadUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> loadUserById(Long id) {
        return userRepository.findById(id);
    }

    public void addOrUpdateImgFile(Long usersId, MultipartFile file) {

        try {

            Optional<User> optUser = userRepository.findById(usersId);

            if (optUser.isEmpty()) throw new NullPointerException("User was not found!");

            User user = optUser.get();
            Path path = Paths.get("files","imgs", "usr");

            if (user.getProfileImg() != null)
                storageService.delete(Paths.get(user.getProfileImg()));

            storageService.store(file, path);

            Path src = path.resolve(file.getOriginalFilename());

            Path dst = path.resolve(String.format("%s-%d", LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), user.getId()));

            // This renames the given file to a unique identifier
            storageService.move(src, dst);

            user.setProfileImg(dst.toString());
            userRepository.save(user);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public User updateUser(User updatedUser) throws ChangeSetPersister.NotFoundException {
        User oldUser = userRepository.findById(updatedUser.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (updatedUser.getUserRole() == null)
            updatedUser.setUserRole(oldUser.getUserRole());

        if (updatedUser.getPassword() != null) {

            String updatedPassword = passwordEncoder.encode(updatedUser.getPassword());

            if (!oldUser.getPassword().equals(updatedPassword)) {
                updatedUser.setPassword(updatedPassword);
            } else {
                updatedUser.setPassword(oldUser.getPassword());
            }

            return userRepository.save(updatedUser);
        }

        updatedUser.setEmail(oldUser.getEmail());
        updatedUser.setPassword(oldUser.getPassword());

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
        return userRepository.findUsersHasWatchedListById(id, pageable);
    }
}
