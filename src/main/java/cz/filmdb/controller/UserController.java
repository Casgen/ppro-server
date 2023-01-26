package cz.filmdb.controller;

import cz.filmdb.model.Filmwork;
import cz.filmdb.model.User;
import cz.filmdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<String> putUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);

        if (updatedUser != null)
            return ResponseEntity.ok().body("User was updated successfully.");

        return ResponseEntity.status(503).body("Error occured while updating the user!");
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
}
