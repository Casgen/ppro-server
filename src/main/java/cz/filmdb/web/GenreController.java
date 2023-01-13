package cz.filmdb.web;

import cz.filmdb.model.Genre;
import cz.filmdb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("api/v1/genres/")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("{id}")
    public Genre getGenre(@PathVariable String id) {
        return genreService.loadGengreById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping
    public List<Genre> getGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("search")
    public List<Genre> searchGenresByQuery(@RequestParam String query) {
        return genreService.searchGenres(query);
    }
}
