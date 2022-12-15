package cz.filmdb.web;

import cz.filmdb.model.TVShow;
import cz.filmdb.service.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/tvshows/")
public class TVShowController {

    private TVShowService tvShowService;

    @Autowired
    public TVShowController(TVShowService tvShowService) {
        this.tvShowService = tvShowService;
    }


    @GetMapping("/getShow")
    public TVShow getShow() {
        return new TVShow();
    }
}
