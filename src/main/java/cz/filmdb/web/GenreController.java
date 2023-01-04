package cz.filmdb.web;

import cz.filmdb.model.Genre;
import cz.filmdb.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/genres/")
public class GenreController {

    private GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/getGenre/{id}")
    public Genre getGenre(@PathVariable String id) {
        return genreService.findGenreById(Long.parseLong(id)).orElse(null);
    }

    @GetMapping("/getGenres")
    public List<Genre> getGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping("/search")
    public List<Genre> searchGenresByQuery(@RequestParam String query) {
        return genreService.searchGenres(query);
    }
}
