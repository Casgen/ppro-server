package cz.filmdb.controller;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.User;
import cz.filmdb.service.JwtService;
import cz.filmdb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public Page<User> getUsers(Pageable pageable) {
        return userService.loadUsers(pageable);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.loadUserById(Long.parseLong(id)).orElse(null);
    }


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/search")
    public Page<User> searchUsers(@RequestParam String query, Pageable pageable) {
        return userService.searchByUsername(query, pageable);
    }

    @PutMapping
    public ResponseEntity<String> putUser(@RequestBody User user, HttpServletRequest request) {
        try {
            Long userId = Long.valueOf(jwtService.extractUserId(retrieveToken(request)));

            User foundUser = userService.loadUserById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);

            if (foundUser.getId() != user.getId())
                throw new AuthorizationServiceException("User id's do not match! edit not authorized!");

            userService.updateUser(user);

            return ResponseEntity.ok().body("User was updated successfully.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(String.format("Error occured while updating the user! %s", e.getMessage()));
        } catch (AuthorizationServiceException e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(String.format("Error occured while updating the user! %s", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        userService.removeUser(id);

        return ResponseEntity.ok().body("User was removed successfully.");
    }

    @GetMapping("/plans-to-watch/{id}")
    public Page<Filmwork> getPlansToWatchList(@PathVariable Long id, Pageable pageable) {
        return userService.loadPlansToWatchListByUserId(id, pageable);
    }

    @GetMapping("/is-watching/{id}")
    public Page<Filmwork> getIsWatchingList(@PathVariable Long id, Pageable pageable) {
        return userService.loadIsWatchingListByUserId(id, pageable);
    }

    @GetMapping("/has-watched/{id}")
    public Page<Filmwork> getHasWatchedList(@PathVariable Long id, Pageable pageable) {
        return userService.loadHasWatchedListByUserId(id, pageable);
    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
