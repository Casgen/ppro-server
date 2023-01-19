package cz.filmdb.controller;

import cz.filmdb.model.User;
import cz.filmdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity.BodyBuilder createUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);

        if (newUser != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }

    @PutMapping
    public ResponseEntity.BodyBuilder putPerson(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);

        if (updatedUser != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }

}
