package cz.filmdb.controller;

import cz.filmdb.service.AccountService;
import cz.filmdb.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/account")
@CrossOrigin("http://localhost:5173")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final JwtService jwtService;

    @PostMapping("/plans-to-watch/{id}")
    public ResponseEntity<String> addPlansToWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.addToPlansToWatch(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully added to your planned watchlist!");
        }

        return ResponseEntity.ok().body("Error occurred while adding a filmwork to your planned watchlist!");
    }

    @PostMapping("/is-watching/{id}")
    public ResponseEntity<String> addIsWatching(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.addToIsWatching(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully added to your 'is watching' list!");
        }

        return ResponseEntity.ok().body("Error occurred while adding a filmwork to your 'is watching' list!");
    }

    @PostMapping("/has-watched/{id}")
    public ResponseEntity<String> addHasWatched(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.addToHasWatched(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully added to your 'has watched' list!");
        }

        return ResponseEntity.ok().body("Error occurred while adding a filmwork to your 'has watched' list!");
    }

    @PostMapping("/wont-watch/{id}")
    public ResponseEntity<String> addWontWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.addToWontWatch(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully added to your 'won't watch' list!");
        }

        return ResponseEntity.ok().body("Error occurred while adding a filmwork to your 'won't watch' list!");
    }

    // ---------------------------------------------------------------------------------------------------------

    @DeleteMapping("/plans-to-watch/{id}")
    public ResponseEntity<String> removePlansToWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.removeFromPlansToWatch(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully removed from your planned watchlist!");
        }

        return ResponseEntity.ok().body("Error occurred while removing a filmwork from your planned watchlist!");
    }

    @DeleteMapping("/is-watching/{id}")
    public ResponseEntity<String> removeIsWatching(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.removeFromIsWatching(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully removed from your 'is watching' list!");
        }

        return ResponseEntity.ok().body("Error occurred while removing a filmwork from your 'is watching' list!");
    }

    @DeleteMapping("/has-watched/{id}")
    public ResponseEntity<String> removeHasWatched(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.removeFromHasWatched(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully removed from your 'has watched' list!");
        }

        return ResponseEntity.ok().body("Error occurred while removing a filmwork from your 'has watched' list!");
    }

    @DeleteMapping("/wont-watch/{id}")
    public ResponseEntity<String> removeWontWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        String usersId = jwtService.extractUserId(retrieveToken(req));

        if (accountService.removeFromWontWatch(Long.parseLong(usersId), id) != null) {
            return ResponseEntity.ok().body("Filmwork successfully removed from your 'won't watch' list!");
        }

        return ResponseEntity.ok().body("Error occurred while removing a filmwork from your 'won't watch' list!");
    }


    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}
