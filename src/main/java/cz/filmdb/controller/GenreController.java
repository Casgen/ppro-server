package cz.filmdb.controller;

import cz.filmdb.model.Genre;
import cz.filmdb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres")
@CrossOrigin("http://localhost:5173")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable String id) {
        return genreService.loadGengreById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/search")
    public List<Genre> searchGenresByQuery(@RequestParam String query) {
        return genreService.searchGenres(query);
    }


    @PostMapping
    public ResponseEntity.BodyBuilder createGenre(@RequestBody Genre genre) {
        Genre newGenre = genreService.saveGenre(genre);

        if (newGenre != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);

    }

    @PutMapping
    public ResponseEntity.BodyBuilder putGenre(@RequestBody Genre genre) {
        Genre updatedGenre = genreService.updateGenre(genre);

        if (updatedGenre != null)
            return ResponseEntity.ok();

        return ResponseEntity.status(503);
    }
}
