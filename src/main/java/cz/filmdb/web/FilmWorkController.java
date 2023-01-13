package cz.filmdb.web;

import cz.filmdb.service.FilmWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/filmworks/")
@CrossOrigin("http://localhost:5173")
public class FilmWorkController {

    private FilmWorkService filmWorkService;

    @Autowired
    public FilmWorkController(FilmWorkService filmWorkService) {
        this.filmWorkService = filmWorkService;
    }
}
