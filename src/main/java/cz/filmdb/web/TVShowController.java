package cz.filmdb.web;

import cz.filmdb.model.TVShow;
import cz.filmdb.service.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/tvShows/")
@CrossOrigin("http://localhost:5173")
public class TVShowController {

    private TVShowService tvShowService;

    @Autowired
    public TVShowController(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }


    @GetMapping("{id}")
    public TVShow getShow(@PathVariable String id) {

        Optional<TVShow> tvShow = tvShowService.loadTVShowById(Long.parseLong(id));

        return tvShow.orElse(null);

    }

    @GetMapping
    public List<TVShow> getShows() {
        return tvShowService.loadTVShows();
    }
}
