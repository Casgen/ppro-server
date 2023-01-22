package cz.filmdb.controller;

import cz.filmdb.model.Genre;
import cz.filmdb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/genres")
@CrossOrigin("http://localhost:5173")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping
    public Page<Genre> getGenres(Pageable pageable) {
        return genreService.getAllGenres(pageable);
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable String id) {
        return genreService.loadGengreById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping("/search")
    public Page<Genre> searchGenresByQuery(@RequestParam String query, Pageable pageable) {
        return genreService.searchGenres(query, pageable);
    }


    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) {
        return genreService.saveGenre(genre);
    }

    @PutMapping
    public Genre putGenre(@RequestBody Genre genre) {
        return genreService.updateGenre(genre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable(name = "id") String id) {
        genreService.removeGenreById(Long.parseLong(id));

        return ResponseEntity.ok().body("Genre was removed succesfully");
    }
}
