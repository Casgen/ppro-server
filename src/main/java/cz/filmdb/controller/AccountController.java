package cz.filmdb.controller;

import cz.filmdb.model.ErrorResponse;
import cz.filmdb.service.AccountService;
import cz.filmdb.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> addPlansToWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.addToPlansToWatch(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully added to the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while adding a filmwork!",e);
        }
    }

    @PostMapping("/is-watching/{id}")
    public ResponseEntity<?> addIsWatching(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.addToIsWatching(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully added to the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while adding a filmwork!",e);
        }
    }

    @PostMapping("/has-watched/{id}")
    public ResponseEntity<?> addHasWatched(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.addToHasWatched(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully added to the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while adding a filmwork!",e);
        }
    }

    @PostMapping("/wont-watch/{id}")
    public ResponseEntity<?> addWontWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.addToWontWatch(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully added to the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while adding a filmwork!",e);
        }
    }

    // ---------------------------------------------------------------------------------------------------------

    @DeleteMapping("/plans-to-watch/{id}")
    public ResponseEntity<?> removePlansToWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.removeFromPlansToWatch(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully removed from the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while removing a filmwork!",e);
        }
    }

    @DeleteMapping("/is-watching/{id}")
    public ResponseEntity<?> removeIsWatching(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.removeFromIsWatching(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully removed from the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while removing a filmwork!",e);
        }
    }

    @DeleteMapping("/has-watched/{id}")
    public ResponseEntity<?> removeHasWatched(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.removeFromHasWatched(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully removed from the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while removing a filmwork!",e);
        }
    }

    @DeleteMapping("/wont-watch/{id}")
    public ResponseEntity<?> removeWontWatch(@PathVariable(name = "id") Long id, HttpServletRequest req) {
        try {
            String usersId = jwtService.extractUserId(retrieveToken(req));

            accountService.removeFromWontWatch(Long.parseLong(usersId), id);
            return ResponseEntity.ok().body("Filmwork successfully removed from the list.");

        } catch (Exception e) {
            return createErrorResponse("Error occurred while removing a filmwork!",e);
        }
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(String message, Exception e) {
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResponse(message,e.getMessage()));

    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader("Authorization").substring(7);
    }
}

