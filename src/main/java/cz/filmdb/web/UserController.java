package cz.filmdb.web;

import cz.filmdb.model.User;
import cz.filmdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/v1/users/")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.loadUsers();
    }

    @GetMapping("{id}")
    public User getUser(@PathVariable String id) {
        return userService.loadUserById(Long.parseLong(id)).orElse(null);
    }

}
