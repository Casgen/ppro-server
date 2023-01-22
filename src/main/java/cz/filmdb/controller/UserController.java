package cz.filmdb.controller;

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

    // TODO: Add have watched, won't watch, is watching and plans to watch endpoints!
}
